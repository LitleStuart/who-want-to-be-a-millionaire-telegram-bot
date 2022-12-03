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
    public void handleMessage(User user, BotMessage botMessage) throws IOException {
        if (botMessage.text.contentEquals("/hint")) {
            executeHintCommand(user);
            return;
        }
        if (botMessage.text.contentEquals("/exit")) {
            executeGameExitCommand(user);
            return;
        }
        if (isAnswer( botMessage.text)) {
            handleAnswerCommand(user, botMessage );
            return;
        }
        sceneFactory.createMainMenuScene().handleMessage(user, botMessage );
        return;
    }

    private void handleAnswerCommand(User user, BotMessage botMessage) throws IOException {
        user.storedQuestion = null;
        if (user.currentQuestion.isRightAnswer( botMessage.text)) {
            executeRightAnswerCommand(user, botMessage);
        } else {
            executeWrongAnswerCommand(user, botMessage);
        }
    }

    private void executeHintCommand(User user) {
        if (user.hints.isEmpty()) {
            botApi.sendAnswer(user.id, "У вас не осталось подсказок");
        } else {
            String hintText = "Выберите подсказку:\n"+user.getHints();
            Buttons hintButtons = new Buttons();
            hintButtons.createHintButtons( hintText, hintText.split("\n").length-2 );
            botApi.sendAnswer(user.id, hintText, hintButtons);
            user.scene = sceneFactory.createHintScene();
        }
    }

    private void executeGameExitCommand(User user) {
        botApi.sendAnswer(user.id, "Игра окончена.\n\nВаш счет: " + (user.currentQuestionIndex - 1)
                + "\n\nЧтобы начать новую игру, введите /start");
        if (user.currentQuestionIndex - 1 > user.highScore) {
            user.highScore = user.currentQuestionIndex - 1;
        }
        user.currentQuestion = null;
        user.currentQuestionIndex = 0;
        user.scene = sceneFactory.createMainMenuScene();

    }

    private void executeRightAnswerCommand(User user, BotMessage botMessage) throws IOException {
        user.currentQuestionIndex++;
        if (user.currentQuestionIndex == 15) {
            botApi.sendAnswer( user.id, "Поздравляю! Вы прошли игру.\nА в награду вы получаете яблочко " );
            executeGameExitCommand( user );
            return;
        }
        if (user.secondChance) {user.secondChance=false;}
        String headerText = "Верно, правильный ответ "+
                user.currentQuestion.getAnswerText( botMessage.text )+"!\n Следующий вопрос:\n\n";
        String questionText = questionProvider.nextQuestionForUser(user);
        Buttons answerButtons = new Buttons();
        answerButtons.createAnswerButtons( questionText, 4 );

        botApi.sendAnswer(user.id,  headerText+questionText, answerButtons);
    }

    private void executeWrongAnswerCommand(User user, BotMessage botMessage) {
        botApi.sendAnswer(user.id, "Жаль, но ответ "+botMessage.text+" неправильный");
        if (user.secondChance) {
            botApi.sendAnswer(user.id, "Попробуйте еще рез");
            user.secondChance=false;
            Question question = user.currentQuestion;
            question.deleteAnswer( botMessage.text );
            String fullQuestionText = question.getTextQuestion()+"\n"+question.getAllAnswerText();
            Buttons answerButtons = new Buttons();
            answerButtons.createAnswerButtons(fullQuestionText , 3 );
            botApi.sendAnswer( user.id, fullQuestionText, answerButtons);
            return;
        }
        executeGameExitCommand(user);
    }
}
