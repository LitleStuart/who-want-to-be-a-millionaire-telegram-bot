import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Bot {
    Bot(SceneFactory sceneFactory) {
        this.sceneFactory = sceneFactory;
        users = getUsersFromDatabase();
    }

    private SceneFactory sceneFactory;
    private Database database = new Database("users");

    private Map<Long, User> users;

    public void handleMessage(Update update) throws IOException {
        User user = getUser(update);
        user.scene.handleMessage(user, update.botMessage);
        System.out.println(user.id + "–" + user.highScore);
        database.updateUserInfo(user.id, user.username, user.currentQuestionIndex,
                user.currentQuestion != null ? user.currentQuestion.getTextQuestion() : "", user.highScore);
    }

    public long getChatId(String username) {
        for (Long id : users.keySet()) {
            if (users.get(id).username.equals(username)) {
                return id;
            }
        }
        return -1;
    }

    public void transferQuestion(long senderId, long receiverId) {
        Question senderCurQuestion = users.get(senderId).currentQuestion;
        Question senderStoredQuestion = users.get(senderId).storedQuestion;
        if (senderCurQuestion == senderStoredQuestion) {
            users.get(receiverId).currentQuestion = senderCurQuestion;
        }
    }

    public void setLastRespMessageId(long messageId, long userId) {
        users.get(userId).lastResponseMessageId.add(messageId);
    }

    public long getLastRespMessageId(long userId) {
        return users.get(userId).lastResponseMessageId.lastElement();
    }

    private Map<Long, User> getUsersFromDatabase() {
        Map<Long, User> users = new HashMap<Long, User>();

        ResultSet rs = database.getUsers();
        try {
            while (rs.next()) {
                Long userId = rs.getLong("id");
                String username = rs.getString("username");
                int currentQuestionIndex = rs.getInt("curQuestionNum");
                // String currentQuestionText = rs.getString("curQuestionText");
                int highscore = rs.getInt("highscore");
                IScene scene = currentQuestionIndex > 0 ? sceneFactory.createGameScene()
                        : sceneFactory.createMainMenuScene();
                users.put(userId, new User(userId, username, currentQuestionIndex, highscore, scene));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /*
     * FOR DEBUG ONLY
     * public void printUsers() {
     * for (User user:users.values()){
     * System.out.println(user.name);
     * user.printResp();
     * }
     * }
     */

    private User getUser(Update update) {
        if (users.isEmpty() || !users.containsKey(update.chatId)) {
            users.put(update.chatId, new User(update.chatId, update.username, sceneFactory.createMainMenuScene()));
        }
        return users.get(update.chatId);
    }

}