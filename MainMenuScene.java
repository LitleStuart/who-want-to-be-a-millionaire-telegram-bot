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
    public void handleMessage(User user, Message message) throws IOException {
        switch (message.text) {
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
                botApi.sendAnswer(user.id, "Неправильный формат ввода, используйте /help для получения информации");
            }
        }
    }

    private void executeHelpCommand(User user) {
        String responseMessage = "/start – Новая игра\n" +
                "/info – Статистика\n" +
                // "/hint – Использовать подсказку\n" +
                "/exit – Выход из игры\n" +
                "/help – Показать справку";
        botApi.sendAnswer(user.id, responseMessage);
    }

    private void executeStartGameCommand(User user) throws IOException {
        user.currentQuestionIndex = 1;
        user.hints = 1;
        String questionText = questionProvider.nextQuestionForUser(user);
        Buttons answerButtons = new Buttons();
        answerButtons.createAnswerButtons( questionText );
        botApi.sendAnswer(user.id, questionText, answerButtons );
        user.scene = sceneFactory.createGameScene();
    }

    private void executeInfoCommand(User user) {
        String responseMessage = "Ваша статистика:\n\n"
                + "Имя – " + user.name + '\n'
                + "Рекорд – " + user.highScore + "\n"
                + "Текущий вопрос – " + user.currentQuestionIndex;
        botApi.sendAnswer(user.id, responseMessage);
    }
}
