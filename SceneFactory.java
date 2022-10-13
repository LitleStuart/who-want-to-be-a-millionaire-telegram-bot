public class SceneFactory {
    IBotApi botApi;

    public SceneFactory(IBotApi botApi) {
        this.botApi = botApi;
    }

    public IScene createFallbackScene() {
        return new MainMenuScene(botApi, this);
    };

    public IScene createGameScene() {
        return new GameScene(botApi, this);
    };

    public IScene createHintScene() {
        return new HintScene(botApi, this);
    };
}
