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
        if (!botApi.isPresent(botMessage.text)){
            botApi.sendAnswer( user.id, "Игрок недоступен");
            user.scene = sceneFactory.createGameScene();
            return;
        }
        Buttons buttons = new Buttons();
        buttons.createHelpButtons(user.name);
        String message = "Игрок "+user.name+" просит помощи";
        botApi.sendAnswer(botMessage.text, message, buttons);
        user.scene = sceneFactory.createGameScene();
    }

}
