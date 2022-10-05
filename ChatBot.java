import java.util.HashMap;
import java.util.Map;

public class ChatBot {
    ChatBot(IBotApi botApi) {
        this.botApi = botApi;
    }

    private IBotApi botApi;

    private Map<Long, User> users = new HashMap<Long, User>();
    private int listId = 0;

    public void run() {
        this.botApi.registerOnUpdate((update) -> {
            updateUserMessageHistory(update);
            User user = getUser(update.chatId);
            String answer = generateAnswer(user);
            this.botApi.sendAnswer(update.chatId, answer);
        });
    }

    private void updateUserMessageHistory(Update update) {
        if (users.isEmpty() || !users.containsKey(update.chatId)) {
            int newId = listId++;
            users.put(update.chatId, new User(newId));
        }
        User user = users.get(update.chatId);
        user.messages.add(user.messages.size(), update.message);
    }

    private User getUser(long chatId) {
        return users.get(chatId);
    }

    private String generateAnswer(User user) {
        String answer = "";
        for (int i = 0; i < user.messages.size(); i++) {
            answer += user.messages.get(i).text + '\n';
        }
        return "Your ID: " + user.id + '\n' +
                "You have written " + user.messages.size() + " message(s):" + '\n' + answer;
    }
}
