import java.io.IOException;

public class AssistScene implements IScene {
    private IBotApi botApi;
    private SceneFactory sceneFactory;
    private IQuestionProvider questionProvider;

    AssistScene(IBotApi botApi, SceneFactory sceneFactory, IQuestionProvider questionProvider) {
        this.botApi = botApi;
        this.sceneFactory = sceneFactory;
        this.questionProvider = questionProvider;
    }

    private boolean isAnswer(String message) {
        return message.contentEquals("A") || message.contentEquals("B") ||
                message.contentEquals("C") || message.contentEquals("D");
    }

    @Override
    public void handleMessage(User user, BotMessage botMessage) throws IOException {
        if (isAnswer(botMessage.text)) {
            if (user.currentQuestion != null) {
                botApi.sendBotToUserMessage(user.receiver, user.username + " считает что ответ - " + botMessage.text);
            }
            user.remLastCallBack();
            botApi.deleteMessage(user.id, botMessage.messageId);
            //user.scene = sceneFactory.createGameScene();
            user.sceneState = "Game";
        }
    }
}