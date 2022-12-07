import java.io.IOException;

public class MainMenuScene implements IScene {
    private IBotApi botApi;
    private SceneFactory sceneFactory;
    private QuestionProvider questionProvider;

    MainMenuScene(IBotApi botApi, SceneFactory sceneFactory, QuestionProvider questionProvider) {
        this.botApi = botApi;
        this.sceneFactory = sceneFactory;
        this.questionProvider = questionProvider;
    }

    @Override
    public void handleMessage(User user, BotMessage botMessage) throws IOException {
        if (botMessage.text.startsWith("/HelpAccepted")){
            executeAssistCommand(user, botMessage);
        }
        switch (botMessage.text) {
            case ("/help"): {
                executeHelpCommand(user);
                return;
            }
            case ("/start"): {
                executeStartGameCommand(user);
                return;
            }
            case ("/info"): {
                executeInfoCommand(user);
                return;
            }

            default: {
                botApi.sendMessage(user.id, "Неправильный формат ввода, используйте /help для получения информации");
            }
        }
    }

    private void executeAssistCommand(User user, BotMessage botMessage){
        botApi.deleteMessage(user.id, botMessage.messageId );
        user.receiver=botMessage.text.substring(14);
        botApi.sendMessage(user.receiver,user.name);
        if (user.currentQuestion==null) {
            botApi.sendMessage(user.id, "Помощь больше не требуется");
            return;
        }
        botApi.sendMessage( user.receiver, user.name+" поможет вам" );
        Buttons answerButtons = new Buttons();
        String fullQuestionText = user.currentQuestion.getTextQuestion();
        answerButtons.createAnswerButtons(user.currentQuestion);
        botApi.sendMessage(user.id, fullQuestionText, answerButtons);
        user.scene = sceneFactory.createAssistScene();
        return;
    }

    private void executeHelpCommand(User user) {
        String responseMessage = "/start – Новая игра\n" +
                "/info – Статистика\n" +
                "/exit – Выход из игры\n" +
                "/help – Показать справку";
        botApi.sendMessage(user.id, responseMessage);
    }

    private void executeStartGameCommand(User user) throws IOException {
        user.currentQuestionIndex = 1;
        user.createHints();
        questionProvider.nextQuestionForUser(user);
        String questionText = user.currentQuestion.getTextQuestion();
        Buttons answerButtons = new Buttons();
        answerButtons.createAnswerButtons(user.currentQuestion );
        botApi.sendMessage(user.id, questionText, answerButtons );
        user.scene = sceneFactory.createGameScene();
    }

    private void executeInfoCommand(User user) {
        String responseMessage = "Ваша статистика:\n\n"
                + "Имя – " + user.name + '\n'
                + "Рекорд – " + user.highScore + "\n"
                + "Текущий вопрос – " + user.currentQuestionIndex;
        botApi.sendMessage(user.id, responseMessage);
    }
}
