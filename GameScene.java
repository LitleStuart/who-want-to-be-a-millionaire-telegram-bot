public class GameScene implements IScene {
    private IBotApi botApi;
    private SceneFactory sceneFactory;

    GameScene(IBotApi botApi, SceneFactory sceneFactory) {
        this.botApi = botApi;
        this.sceneFactory = sceneFactory;
    }

    @Override
    public void handleMessage(User user, Message message) {
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
        botApi.sendAnswer(user.id, "Игра окончена.\nВаш счет: " + user.curQuestion);
        user.curQuestion = 0;
        user.scene = sceneFactory.createFallbackScene();
        botApi.sendAnswer(user.id, "Чтобы начать новую игру, введите /start");
    }

    private void executeRightAnswerCommand(User user, Message message) {
        user.curQuestion++;
        if (user.curQuestion == 15) {
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

    private String buildQuestionWithAnswers(User user) {
        String result = "";
        result += user.game.questions.get(user.curQuestion).getQuestion() + '\n';
        for (int i = 0; i < 4; i++) {
            result += (char) ('A' + i) + ": "
                    + user.game.questions.get(user.curQuestion).getAnswers().get(i).answer + '\n';
        }
        return result;
    }
}
