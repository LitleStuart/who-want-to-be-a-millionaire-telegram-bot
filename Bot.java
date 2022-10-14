import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Bot {
    Bot(IBotApi botApi) {
        this.sceneFactory = new SceneFactory(botApi);
    }

    private SceneFactory sceneFactory;

    private Map<Long, User> users = new HashMap<Long, User>();

    public void handleMessage(Update update) throws IOException {
        User user = getUser(update);
        user.scene.handleMessage(user, update.message);
    }

    private User getUser(Update update) {
        if (users.isEmpty() || !users.containsKey(update.chatId)) {
            users.put(update.chatId, new User(update.chatId, update.username, sceneFactory.createMainMenuScene()));
        }
        return users.get(update.chatId);
    }
}