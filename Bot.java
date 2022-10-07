import java.util.HashMap;
import java.util.Map;

public class Bot {
    Bot(IBotApi botApi) {
        this.botApi = botApi;
    }

    private IBotApi botApi;

    private int listId = 0;
    private Map<Long, User> users = new HashMap<Long, User>();

    public void handleUpdate(Update update) {
        User user = getUser(update);
        String answer = generateAnswer(user, update.message);
        this.botApi.sendAnswer(update.chatId, answer);
    }

    private User getUser(Update update) {
        if (users.isEmpty() || !users.containsKey(update.chatId)) {
            int newId = listId++;
            users.put(update.chatId, new User(newId));
        }
        return users.get(update.chatId);
    }

    private String generateAnswer(User user, Message message) {
        Commands coms = new Commands();
        return coms.messageReact(user);
    }
}