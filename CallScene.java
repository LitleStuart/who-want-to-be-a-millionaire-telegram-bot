import java.io.IOException;

public class CallScene implements IScene{
    private IBotApi botApi;
    private SceneFactory sceneFactory;
    private IQuestionProvider questionProvider;


    CallScene(IBotApi botApi, SceneFactory sceneFactory, IQuestionProvider questionProvider) {
        this.botApi = botApi;
        this.sceneFactory = sceneFactory;
        this.questionProvider = questionProvider;
    }

    public void handleMessage(User user, BotMessage botMessage) throws IOException {
        if (user.name.equals(botMessage.text)){
            botApi.sendBotToUserMessage( user.id, "Спросить себя можно было и без подсказки");
            user.scene = sceneFactory.createGameScene();
            return;
        }
        botApi.sendUserToUserMessage( user.name, botMessage.text );
        Buttons buttons = new Buttons();
        buttons.createHelpButton(user.name);
        String message = "Игрок "+user.name+" просит помощи";
        botApi.sendBotToUserMessage(botMessage.text, message, buttons);
        user.scene = sceneFactory.createGameScene();
    }

}
