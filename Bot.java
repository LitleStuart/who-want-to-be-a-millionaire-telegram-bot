import java.util.HashMap;
import java.util.Map;

public class Bot {
    Bot(IBotApi botApi) {
        this.botApi = botApi;
    }

    private IBotApi botApi;

    private Map<Long, User> users = new HashMap<Long, User>();
    private int listId = 0;

    public void handleMessage(Update update, boolean... withAnswer) {
        updateUserMessageHistory(update);
        User user = getUser(update.chatId);
        String answer = generateAnswer(user);
        this.botApi.sendAnswer(update.chatId, answer);
    }

    public void sendMessage(int chatId, String message)
    {
        this.botApi.sendAnswer ( chatId,message );
    }

    private void updateUserMessageHistory(Update update) {
        if (users.isEmpty() || !users.containsKey(update.chatId)) {
            int newId = listId++;
            users.put(update.chatId, new User(newId));
        }
        User user = users.get(update.chatId);
        user.lastMessage=update.message;
    }

    private User getUser(long chatId) {
        return users.get(chatId);
    }

    private String generateAnswer(User user) {
        if (user.isInGame)
        {
            GameCommands gameCom=new GameCommands ();
            return gameCom.respond ( user );
        }
        else
        {
            ChatCommands chatCom=new ChatCommands ();
            return chatCom.respond ( user );
        }
    }
}