public class AnswerChecker {
    public AnswerChecker() {
    }

    public boolean isAnswerCorrect(User user, String answer) {
        int variant = answer.charAt(0) - 'A';
        if (user.curQuestion.getAnswers().get(variant).isCorrect) {
            return true;
        } else {
            return false;
        }
    }
}
