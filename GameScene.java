import java.io.IOException;

public class GameScene implements IScene {
    private IBotApi botApi;
    private SceneFactory sceneFactory;
    private QuestionProvider questionProvider;

    GameScene(IBotApi botApi, SceneFactory sceneFactory, QuestionProvider questionProvider) {
        this.botApi = botApi;
        this.sceneFactory = sceneFactory;
        this.questionProvider = questionProvider;
    }

    private boolean isAnswer(String message) {
        return message.contentEquals("A") || message.contentEquals("B") ||
                message.contentEquals("C") || message.contentEquals("D");
    }

    @Override
    public void handleMessage(User user, Message message) throws IOException {
        if (message.text.contentEquals("/hint")) {
            executeHintCommand(user);
            return;
        }
        if (message.text.contentEquals("/exit")) {
            executeGameExitCommand(user);
            return;
        }
        if (isAnswer(message.text)) {
            handleAnswerCommand(user, message);
            return;
        }
        sceneFactory.createMainMenuScene().handleMessage(user, message);
        return;
    }

    private void handleAnswerCommand(User user, Message message) throws IOException {
        if (user.currentQuestion.isRightAnswer(message.text)) {
            executeRightAnswerCommand(user, message);
        } else {
            executeWrongAnswerCommand(user, message);
        }
    }

    private void executeHintCommand(User user) {
        if (user.hints == 0) {
            botApi.sendAnswer(user.id, "У вас не осталось подсказок");
        } else {
            botApi.sendAnswer(user.id, "Выберите подсказку:\n\n1: 50/50\n2: Call\n3: x2", "withHints");
            user.scene = sceneFactory.createHintScene();
        }
    }

    private void executeGameExitCommand(User user) {
        botApi.sendAnswer(user.id, "Игра окончена.\n\nВаш счет: " + (user.currentQuestionIndex - 1)
                + "\n\nЧтобы начать новую игру, введите /start");
        if (user.currentQuestionIndex - 1 > user.highScore) {
            user.highScore = user.currentQuestionIndex - 1;
        }
        user.currentQuestionIndex = 0;
        user.scene = sceneFactory.createMainMenuScene();

    }

    private void executeRightAnswerCommand(User user, Message message) throws IOException {
        user.currentQuestionIndex++;
        if (user.currentQuestionIndex == 15) {
            botApi.sendAnswer(user.id, "Поздравляю! Вы прошли игру.\nА в награду вы получаете яблочко ");
            executeGameExitCommand(user);
            return;
        }
        botApi.sendAnswer(user.id, "Верно! Следующий вопрос:\n\n" + questionProvider.nextQuestionForUser(user), "withAnswers");
    }

    private void executeWrongAnswerCommand(User user, Message message) {
        botApi.sendAnswer(user.id, "Жаль, но это не так");
        executeGameExitCommand(user);
    }
}
