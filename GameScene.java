import java.io.IOException;

public class GameScene implements IScene {
    private IBotApi botApi;
    private SceneFactory sceneFactory;

    GameScene(IBotApi botApi, SceneFactory sceneFactory) {
        this.botApi = botApi;
        this.sceneFactory = sceneFactory;
    }

    @Override
    public void handleMessage(User user, Message message) throws IOException {
        if (message.text.contentEquals("A") || message.text.contentEquals("B")
                || message.text.contentEquals("C") || message.text.contentEquals("D")) {
            if (new AnswerChecker().isAnswerCorrect(user, message.text)) {
                message.text = "Right";
            } else {
                message.text = "Wrong";
            }
        }
        switch (message.text) {
            case "/hint":
                executeHintCommand(user);
                return;
            case "/exit":
                executeGameExitCommand(user);
                return;
            case "Right":
                executeRightAnswerCommand(user, message);
                return;
            case "Wrong":
                executeWrongAnswerCommand(user, message);
                return;
            default:
                sceneFactory.createFallbackScene().handleMessage(user, message);
                return;
        }
    }

    private void executeHintCommand(User user) {
        if (user.hints == 0) {
            botApi.sendAnswer(user.id, "У вас не осталось подсказок");
        } else {
            botApi.sendAnswer(user.id, "Выберите подсказку:");
            user.scene = sceneFactory.createHintScene();
        }
    }

    private void executeGameExitCommand(User user) {
        botApi.sendAnswer(user.id, "Игра окончена.\nВаш счет: " + user.curQuestionIndex);
        user.curQuestionIndex = 0;
        user.scene = sceneFactory.createFallbackScene();
        botApi.sendAnswer(user.id, "Чтобы начать новую игру, введите /start");
    }

    private void executeRightAnswerCommand(User user, Message message) throws IOException {
        user.curQuestionIndex++;
        if (user.curQuestionIndex == 15) {
            botApi.sendAnswer(user.id, "Поздравляю! Вы прошли игру.\nА в награду вы получаете яблочко ");
            executeGameExitCommand(user);
            return;
        }
        botApi.sendAnswer(user.id, "Верно! Следующий вопрос:");
        String textQuestionWithAnswers = buildQuestionWithAnswers(user);
        botApi.sendAnswer(user.id, textQuestionWithAnswers);
    }

    private void executeWrongAnswerCommand(User user, Message message) {
        botApi.sendAnswer(user.id, "Жаль, но это не так");
        executeGameExitCommand(user);
    }

    private String buildQuestionWithAnswers(User user) throws IOException {
        Question q = new BuildJSONObject().toQuestion(user.curQuestionIndex);
        user.curQuestion = q;
        String result = q.getQuestion() + '\n';
        for (int i = 0; i < 4; i++) {
            result += (char) ('A' + i) + ": "
                    + q.getAnswers().get(i).answer + '\n';
        }
        return result;
    }
}
