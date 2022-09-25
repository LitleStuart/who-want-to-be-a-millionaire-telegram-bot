import java.util.HashMap;
import java.util.Map;

public class ChatBot {
    private static Map<Integer, User> users = new HashMap<Integer, User>();
    private static int listId = 0;

    public static void main(String[] args) {
        while (true) {
            Update update = getUpdate();
            updateUserMessageHistory(update);
            User user = getUser(update.chatId);
            String answer = generateAnswer(user);
            sendAnswer(answer);
        }
    }

    private static Update getUpdate() {
        return new Update(0);
    }

    private static void updateUserMessageHistory(Update update) {
        if (users.isEmpty() || !users.containsKey(update.chatId)) {
            int newId = listId++;
            users.put(update.chatId, new User(newId));
        }
        User user = users.get(update.chatId);
        user.messages.add(user.messages.size(), update.message);
    }

    private static User getUser(int chatId) {
        return users.get(chatId);
    }

    private static String generateAnswer(User user) {
        String answer = "";
        for (int i = 0; i < user.messages.size(); i++) {
            answer += user.messages.get(i).text + '\n';
        }
        return "Your ID: " + user.id + '\n' +
                "You have written " + user.messages.size() + " message(s):" + '\n' + answer;
    }

    private static void sendAnswer(String answer) {
        System.out.println(answer);
    }
}
