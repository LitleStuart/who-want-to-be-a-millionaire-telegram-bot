public class GameCommands {

    public String respond(User user, String message) {
        String responceMessage = new String();
        if (message.contentEquals("A") || message.contentEquals("B")
                || message.contentEquals("C") || message.contentEquals("D")) // refactor later
        {
            Question curQuestion = user.game.questions.get(user.curQuestion);
            // game check answer
            // return bool
            if (curQuestion.getAnswers().get((message.charAt(0)) - 'A').isCorrect) {
                user.curQuestion++;
                responceMessage = "Правильно, следующий вопрос \n";
                responceMessage += user.game.questions.get(user.curQuestion).getQuestion() + '\n';
                for (int i = 0; i < 4; i++) {
                    responceMessage += (char) ('A' + i) + "-"
                            + user.game.questions.get(user.curQuestion).getAnswers().get(i).answer + '\n';
                }
            } else {
                user.highScore = Math.max(user.highScore, user.curQuestion);
                user.curQuestion = 0;
                user.isInGame = false;
                responceMessage = "Вы проиграли, чтобы начать заново /start";
            }
            return responceMessage;
        }
        if (message.contentEquals("/hint")) {
            Question curQuestion = user.game.questions.get(user.curQuestion);
            if (user.hints > 0) {
                if (curQuestion.hintIsUsed()) {
                    responceMessage = "Вы уже использовали подсказку на этом вопросе";
                    return responceMessage;
                }
                int wrongCounter = 0;
                responceMessage = "Неправильные ответы:\n";
                for (int i = 0; i < 4; i++) {
                    if (!curQuestion.getAnswers().get(i).isCorrect) {
                        wrongCounter++;
                        responceMessage += curQuestion.getAnswers().get(i).answer + '\n';
                    }
                    if (wrongCounter == 2) {
                        break;
                    }
                }
                user.hints--;
                curQuestion.useHint();
                return responceMessage;
            } else {
                responceMessage = "У вас не осталось подсказок";
                return responceMessage;
            }

        }
        if (message.contentEquals("/exit")) {
            user.isInGame = false;
            user.curQuestion = 0;
        }
        return new ChatCommands().respond(user, message);
    }
}
