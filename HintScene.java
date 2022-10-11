public class HintScene implements IScene {
    private IBotApi botApi;
    private SceneFactory sceneFactory;

    HintScene(IBotApi botApi, SceneFactory sceneFactory) {
        this.botApi = botApi;
        this.sceneFactory = sceneFactory;
    }

    @Override
    public void handleMessage(User user, Message message) {
    }

}
