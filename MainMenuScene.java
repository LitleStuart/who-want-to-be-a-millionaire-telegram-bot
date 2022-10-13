public class MainMenuScene implements IScene {
    private IBotApi botApi;
    private SceneFactory sceneFactory;

    MainMenuScene(IBotApi botApi, SceneFactory sceneFactory) {
        this.botApi = botApi;
        this.sceneFactory = sceneFactory;
    }

    @Override
    public void handleMessage(User user, Message message) {
        switch (message.text) {
            case ("/help"): {
                botApi.sendAnswer(user.id, new HelpCommand().execute());
                return;
            }
            case ("/start"): {
                botApi.sendAnswer(user.id, new StartCommand().execute(user));
                user.scene = sceneFactory.createGameScene();
                return;
            }
            case ("/info"): {
                botApi.sendAnswer(user.id, new InfoCommand().execute(user));
                return;
            }

            default: {
                botApi.sendAnswer(user.id, "Неправильный формат ввода, используйте /help для получения информации");
            }
        }

    }
}
