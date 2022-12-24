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

    public IScene createCallScene() {
        return new CallScene(botApi, this, questionProvider);
    };

    public IScene createAssistScene() {
        return new AssistScene(botApi, this, questionProvider);
    };

    // Хз насколько это нормально, выглядит как костыль если честно, но теперь нет
    // циклической зависимости
    // Еще юзер теперь не зависит от api, scenefactory, questionprovider(ну он и не
    // должен лол)
    public IScene getScene(String sceneName) {
        switch (sceneName) {
            case ("Game"): {
                return createGameScene();
            }
            case ("Hint"): {
                return createHintScene();
            }
            case ("Call"): {
                return createCallScene();
            }
            case ("Assist"): {
                return createAssistScene();
            }
            default: {
                return createMainMenuScene();
            }
        }
    }
}
