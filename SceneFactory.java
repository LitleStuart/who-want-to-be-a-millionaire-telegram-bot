public class SceneFactory {
    IBotApi botApi;
    IQuestionProvider questionProvider;

    public SceneFactory(IBotApi botApi, IQuestionProvider questionProvider) {
        this.botApi = botApi;
        this.questionProvider = questionProvider;
    }

    public IScene createMainMenuScene() {
        return new MainMenuScene(botApi, this, questionProvider);
    };

    public IScene createGameScene() {
        return new GameScene(botApi, this, questionProvider);
    };

    public IScene createHintScene() {
        return new HintScene(botApi, this, questionProvider);
    };

    public IScene createCallScene() { return new CallScene(botApi, this, questionProvider); };

    public IScene createAssistScene() {return new AssistScene( botApi, this,questionProvider );};

}
