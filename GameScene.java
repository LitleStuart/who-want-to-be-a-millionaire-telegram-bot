import java.io.IOException;

public class GameScene implements IScene {
    private IBotApi botApi;
    private SceneFactory sceneFactory;
    private IQuestionProvider questionProvider;

    GameScene(IBotApi botApi, SceneFactory sceneFactory, IQuestionProvider questionProvider) {
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
        if (botMessage.text.contentEquals("/hint")) {
            executeHintCommand(user);
            return;
        }
        if (botMessage.text.contentEquals("/exit")) {
            executeGameExitCommand(user);
            return;
        }
        if (isAnswer(botMessage.text)) {
            if (user.currentQuestion == null) {
                String headerText = "Извините, у нас произошел сбой, поэтому вам придется ответить на другой вопрос.\n";
                questionProvider.nextQuestionForUser(user);
                String questionText = user.currentQuestion.getTextQuestion();
                Buttons answerButtons = new Buttons();
                answerButtons.createAnswerButtons(user.currentQuestion.getAllAnswers());
                answerButtons.addHintButton();
                botApi.sendBotToUserMessage(user.id, headerText + questionText, answerButtons);
                return;
            }
            user.remLastCallBack();
            handleAnswerCommand(user, botMessage);
            return;
        }
        // if (user.currentQuestion == null) {
        // user.scene = sceneFactory.createMainMenuScene();
        // user.scene.handleMessage(user, botMessage);
        // return;
        // }
        sceneFactory.createMainMenuScene().handleMessage(user, botMessage);
        return;
    }

    private void handleAnswerCommand(User user, BotMessage botMessage) throws IOException {
        user.storedQuestion = null;
        if (user.currentQuestion.isRightAnswer(botMessage.text)) {
            executeRightAnswerCommand(user, botMessage);
        } else {
            executeWrongAnswerCommand(user, botMessage);
        }
    }

    private void executeHintCommand(User user) {
        if (user.hints.isEmpty()) {
            botApi.sendBotToUserMessage(user.id, "У вас не осталось подсказок");
        } else {
            String hintText = "Выберите подсказку:\n";
            Buttons hintButtons = new Buttons();
            hintButtons.createHintButtons(user.getHints());
            botApi.sendBotToUserMessage(user.id, hintText, hintButtons);
            //user.scene = sceneFactory.createHintScene();
            user.sceneState = "Hint";
        }
    }

    private void executeGameExitCommand(User user) {
        Buttons gameOverButtons = new Buttons();
        gameOverButtons.createGameOverButtons();
        String text = "Игра окончена.\n\nВаш счет: " + (user.currentQuestionIndex - 1) + '\n';
        botApi.sendBotToUserMessage(user.id, text, gameOverButtons);
        if (user.currentQuestionIndex - 1 > user.highScore) {
            user.highScore = user.currentQuestionIndex - 1;
        }
        user.currentQuestion = null;
        user.currentQuestionIndex = 0;
        //user.scene = sceneFactory.createMainMenuScene();
        user.sceneState = "MainMenu";
    }

    private void executeRightAnswerCommand(User user, BotMessage botMessage) throws IOException {
        user.currentQuestionIndex++;
        if (user.currentQuestionIndex == 15) {
            botApi.sendBotToUserMessage(user.id, "Поздравляю! Вы прошли игру.\nА в награду вы получаете яблочко ");
            executeGameExitCommand(user);
            return;
        }
        if (user.secondChance) {
            user.secondChance = false;
        }
        String headerText = "Верно, правильный ответ " +
                user.currentQuestion.getAnswerText(botMessage.text) + "!\n Следующий вопрос:\n\n";
        questionProvider.nextQuestionForUser(user);
        String questionText = user.currentQuestion.getTextQuestion();
        Buttons answerButtons = new Buttons();
        answerButtons.createAnswerButtons(user.currentQuestion.getAllAnswers());
        answerButtons.addHintButton();
        botApi.sendBotToUserMessage(user.id, headerText + questionText, answerButtons);
    }

    private void executeWrongAnswerCommand(User user, BotMessage botMessage) {
        if (user.secondChance) {
            user.secondChance = false;
            Question question = user.currentQuestion;
            question.deleteAnswer(botMessage.text);
            String fullQuestionText = question.getTextQuestion();
            Buttons answerButtons = new Buttons();
            answerButtons.createAnswerButtons(question.getAllAnswers());
            answerButtons.addHintButton();
            botApi.deleteMessage(user.id, botMessage.messageId);
            botApi.sendBotToUserMessage(user.id, "Попробуйте еще раз\n" + fullQuestionText, answerButtons);
            return;
        }
        botApi.sendBotToUserMessage(user.id, "Жаль, но ответ " + botMessage.text + " неправильный");
        executeGameExitCommand(user);
    }
}
