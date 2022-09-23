import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatBot {
    private static Map<Integer, User> users = new HashMap<Integer, User>();
    private static ArrayList<Integer> listId = new ArrayList<>();

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
            int newId;
            if (listId.size() != 0) {
                newId = max(listId) + 1;
            } else {
                newId = 0;
            }
            listId.add(newId);
            users.put(update.chatId, new User(newId));
        }
        User user = users.get(update.chatId);
        user.messages.add(user.messages.size(), update.message);
    }

    private static User getUser(int chatId) {
        return users.get(chatId);
    }

    private static String generateAnswer(User user) {
        return "Your ID: " + user.id + '\n' +
                "You have written " + user.messages.size() + " message(s)";
    }

    private static void sendAnswer(String answer) {
        System.out.println(answer);
    }

    private static int max(ArrayList<Integer> arr) {
        int max = arr.get(0);
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) > max)
                max = arr.get(i);
        }
        return max;
    }
}