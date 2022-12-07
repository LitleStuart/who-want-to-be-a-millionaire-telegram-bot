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
        if (user.name.equals(botMessage.text)){
            botApi.sendMessage( user.id, "Спросить себя можно было и без подсказки");
            user.scene = sceneFactory.createGameScene();
            return;
        }
        user.storedQuestion=user.currentQuestion;
        botApi.sendMessage( user.name, botMessage.text );
        Buttons buttons = new Buttons();
        buttons.createHelpButtons(user.name);
        String message = "Игрок "+user.name+" просит помощи";
        botApi.sendMessage(botMessage.text, message, buttons);
        user.scene = sceneFactory.createGameScene();
    }

}
