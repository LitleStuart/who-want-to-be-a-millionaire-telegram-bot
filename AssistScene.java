import java.io.IOException;

public class AssistScene implements IScene {
    private IBotApi botApi;
    private SceneFactory sceneFactory;
    private QuestionProvider questionProvider;

    AssistScene(IBotApi botApi, SceneFactory sceneFactory, QuestionProvider questionProvider) {
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
        if (isAnswer( botMessage.text )){
            botApi.transferQuestion( user.receiver, user.name );
            if (user.currentQuestion!=null)
            {
                botApi.sendAnswer( user.receiver, user.name+" считает что ответ - "+botMessage.text);
            }
            botApi.deleteMessage( user.id, botMessage.messageId);
            user.scene = sceneFactory.createMainMenuScene();
        }
        else {
            botApi.sendAnswer( user.id, "Ответом должна одна из букв A-D" );
        }
    }
}