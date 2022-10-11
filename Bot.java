import java.util.HashMap;
import java.util.Map;

public class Bot {
    Bot(IBotApi botApi) {
        this.sceneFactory = new SceneFactory(botApi);
    }

    private SceneFactory sceneFactory;

    private Map<Long, User> users = new HashMap<Long, User>();

    public void handleMessage(Update update) {
        User user = getUser(update.chatId);
        user.scene.handleMessage(user, update.message);
    }

    private User getUser(long chatId) {
        if (users.isEmpty() || !users.containsKey(chatId)) {
            users.put(chatId, new User(chatId, sceneFactory.createFallbackScene()));
        }
        return users.get(chatId);
    }
}