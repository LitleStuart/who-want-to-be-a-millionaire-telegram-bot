import java.io.IOException;

public class CallScene implements IScene{
    private IBotApi botApi;
    private SceneFactory sceneFactory;
    private QuestionProvider questionProvider;


    CallScene(IBotApi botApi, SceneFactory sceneFactory, QuestionProvider questionProvider) {
        this.botApi = botApi;
        this.sceneFactory = sceneFactory;
        this.questionProvider = questionProvider;
    }

    public void handleMessage(User user, BotMessage botMessage) throws IOException {
        user.storedQuestion=user.currentQuestion;
        Buttons buttons = new Buttons();
        buttons.createHelpButtons(user.name);
        botApi.sendAnswer(botMessage.text, "Игрок "+user.name+" просит помощи", buttons);
        user.scene = sceneFactory.createGameScene();
    }

}
