public class HintScene implements IScene {
    private IBotApi botApi;
    private SceneFactory sceneFactory;
    private QuestionProvider questionProvider;

    HintScene(IBotApi botApi, SceneFactory sceneFactory, QuestionProvider questionProvider) {
        this.botApi = botApi;
        this.sceneFactory = sceneFactory;
        this.questionProvider = questionProvider;
    }

    @Override
    public void handleMessage(User user, Message message) {
        switch (message.text) {
            default:
                botApi.sendAnswer(user.id, "Раздел в разработке. Играйте пока без подсказок :)");
                user.scene = sceneFactory.createGameScene();
                break;
        }
    }

}
