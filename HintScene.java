public class HintScene implements IScene {
    private IBotApi botApi;
    private SceneFactory sceneFactory;
    private QuestionProvider questionProvider;

    HintScene(IBotApi botApi, SceneFactory sceneFactory, QuestionProvider questionProvider) {
        this.botApi = botApi;
        this.sceneFactory = sceneFactory;
        this.questionProvider = questionProvider;
    }

    @Override
    public void handleMessage(User user, BotMessage botMessage) {
        user.remLastCallBack();
        switch (botMessage.text) {
            case ("50/50"):{
                if (user.hints.get("50/50")==0) {
                    botApi.sendMessage(user.id, "Подсказки 50/50 нет");
                    break;
                }
                user.hints.remove("50/50");
                handleFifty(user);
                botApi.deleteMessage(user.id, botMessage.messageId );
                user.scene = sceneFactory.createGameScene();
                break;
            }
            case ("Call"):{
                if (user.hints.get("Call")==0) {
                    botApi.sendMessage(user.id, "Подсказки Call нет");
                    break;
                }
                user.hints.remove("Call");
                handleCall(user, botMessage);
                botApi.deleteMessage(user.id, botMessage.messageId);
                break;
            }
            case ("x2"):{
                if (user.hints.get("x2")==0){
                    botApi.sendMessage( user.id, "Подсказки x2 нет");
                    break;
                }
                user.hints.remove("x2");
                handleDouble(user, botMessage);
                botApi.deleteMessage(user.id, botMessage.messageId);
                break;
            }
            default:
                botApi.sendMessage(user.id, "Раздел в разработке. Играйте пока без подсказок :)");
                user.scene = sceneFactory.createGameScene();
                break;
        }
    }

    private void handleFifty(User user){
        botApi.deleteMessage( user.id, user.lastResponseMessageId.lastElement() );
        Question question = user.currentQuestion;
        for (int i=0;i<4;i++){
            String letter = "" + (char)('A'+i);
            if (!question.isRightAnswer( letter )) {
                question.deleteAnswer( letter );
            }
            if (question.numberOfAnswers()==2) {break;}
        }
        String questionText = question.getTextQuestion();
        Buttons answerButtons = new Buttons();
        answerButtons.createAnswerButtons(question);
        System.out.println("cut");
        botApi.sendMessage(user.id, questionText, answerButtons);
    }

    private void handleCall(User user, BotMessage botMessage){
        botApi.sendMessage(user.id, "Введите имя пользователя");
        user.scene = sceneFactory.createCallScene();
    }

    private void handleDouble(User user, BotMessage botMessage){
        user.secondChance=true;
        botApi.deleteMessage( user.id, user.lastResponseMessageId.lastElement() );
        Question question = user.currentQuestion;
        String questionText = question.getTextQuestion();
        Buttons answerButtons = new Buttons();
        answerButtons.createAnswerButtons(question);
        botApi.sendMessage( user.id, "У вас 2 попытки\n"+questionText, answerButtons );
        user.scene = sceneFactory.createGameScene();
    }
}
