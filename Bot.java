import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Bot {
    Bot(IBotApi botApi, IQuestionProvider questionProvider) {
        this.sceneFactory = new SceneFactory(botApi,  questionProvider);
    }

    private SceneFactory sceneFactory;

    private Map<Long, User> users = new HashMap<Long, User>();

    public void handleMessage(Update update) throws IOException {
        User user = getUser(update);
        user.scene.handleMessage(user, update.botMessage);
    }

    public long getChatId(String username){
        for (Long id: users.keySet()){
            if (users.get(id).name.equals(username)) {return id;}
        }
        return -1;
    }

    public void transferQuestion(long senderId, long receiverId){
        if (users.get(senderId).currentQuestion==users.get(senderId).storedQuestion) {
            users.get(receiverId).currentQuestion=users.get(senderId).currentQuestion;
        }
        else {
            users.get(receiverId).currentQuestion=null;
        }
    }

    public void setLastRespMessageId(long messageId, long userId){
        users.get(userId).lastResponseMessageId.add(messageId);
    }

    public long getLastRespMessageId(long userId){
        return users.get(userId).lastResponseMessageId.lastElement();
    }


    public void printUsers() {
        for (User user:users.values()){
            System.out.println(user.name);
        }
    }

    private User getUser(Update update) {
        if (users.isEmpty() || !users.containsKey(update.chatId)) {
            users.put(update.chatId, new User(update.chatId, update.username, sceneFactory.createMainMenuScene()));
        }
        return users.get(update.chatId);
    }


}