import java.io.IOException;

public class MainMenuScene implements IScene {
    private IBotApi botApi;
    private SceneFactory sceneFactory;
    private IQuestionProvider questionProvider;

    MainMenuScene(IBotApi botApi, SceneFactory sceneFactory, IQuestionProvider questionProvider) {
        this.botApi = botApi;
        this.sceneFactory = sceneFactory;
        this.questionProvider = questionProvider;
    }

    @Override
    public void handleMessage(User user, BotMessage botMessage) throws IOException {
        if (botMessage.text.startsWith("/HelpAccepted")) {
            executeAssistCommand(user, botMessage);
            return;
        }
        switch (botMessage.text) {
            case ("/help"): {
                executeHelpCommand(user);
                return;
            }
            case ("/leaderboard"): {
                executeLeaderboardCommand(user);
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
                botApi.sendBotToUserMessage(user.id,
                        "Неправильный формат ввода, используйте /help для получения информации");
            }
        }
    }

    private void executeAssistCommand(User user, BotMessage botMessage) {
        user.remLastCallBack();
        botApi.deleteMessage(user.id, botMessage.messageId);
        user.receiver = botMessage.text.substring(14);
        if (user.currentQuestion == null) {
            botApi.sendBotToUserMessage(user.id, "Помощь больше не требуется");
            return;
        }
        botApi.sendBotToUserMessage(user.receiver, user.username + " поможет вам");
        Buttons answerButtons = new Buttons();
        String fullQuestionText = user.currentQuestion.getTextQuestion();
        answerButtons.createAnswerButtons(user.currentQuestion.getAllAnswers());
        botApi.sendBotToUserMessage(user.id, fullQuestionText, answerButtons);
        user.scene = sceneFactory.createAssistScene();
        return;
    }

    private void executeLeaderboardCommand(User user) {
        String responseMessage = botApi.getLeaderboard();
        botApi.sendBotToUserMessage(user.id, responseMessage);
    }

    private void executeHelpCommand(User user) {
        String responseMessage = "/start – Новая игра\n" +
                "/info – Статистика\n" +
                "/leaderboard – Рейтинг\n" +
                "/exit – Выход из игры\n" +
                "/help – Показать справку";
        botApi.sendBotToUserMessage(user.id, responseMessage);
    }

    private void executeStartGameCommand(User user) throws IOException {
        user.currentQuestionIndex = 1;
        user.createHints();
        questionProvider.nextQuestionForUser(user);
        String questionText = user.currentQuestion.getTextQuestion();
        Buttons answerButtons = new Buttons();
        answerButtons.createAnswerButtons(user.currentQuestion.getAllAnswers());
        answerButtons.addHintButton();
        botApi.sendBotToUserMessage(user.id, questionText, answerButtons);
        user.scene = sceneFactory.createGameScene();
    }

    private void executeInfoCommand(User user) {
        String responseMessage = "Ваша статистика:\n\n"
                + "Имя – " + user.username + '\n'
                + "Рекорд – " + user.highScore + "\n"
                + "Текущий вопрос – " + user.currentQuestionIndex;
        botApi.sendBotToUserMessage(user.id, responseMessage);
    }
}
