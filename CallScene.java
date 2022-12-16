import java.io.IOException;

public class CallScene implements IScene {
    private IBotApi botApi;
    private SceneFactory sceneFactory;
    private IQuestionProvider questionProvider;

    CallScene(IBotApi botApi, SceneFactory sceneFactory, IQuestionProvider questionProvider) {
        this.botApi = botApi;
        this.sceneFactory = sceneFactory;
        this.questionProvider = questionProvider;
    }

    public void handleMessage(User user, BotMessage botMessage) throws IOException {
        if (user.username.equals(botMessage.text)) {
            botApi.sendBotToUserMessage(user.id, "Спросить себя можно было и без подсказки");
            user.scene = sceneFactory.createGameScene();
            return;
        }
        botApi.sendUserToUserMessage(user.username, botMessage.text);
        Buttons buttons = new Buttons();
        buttons.createHelpButton(user.username);
        String message = "Игрок " + user.username + " просит помощи";
        botApi.sendBotToUserMessage(botMessage.text, message, buttons);
        user.scene = sceneFactory.createGameScene();
    }

}
