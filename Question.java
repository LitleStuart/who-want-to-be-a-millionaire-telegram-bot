public class Question {
    private String textQuestion;
    private String[] answers = new String[4];
    private int correctAnswer;

    Question(QuestionJson questionJson) {
        this.textQuestion = questionJson.question;

        for (int i = 0; i < 4; i++) {
            this.answers[i] = questionJson.answers[i].answer;
            if (questionJson.answers[i].key == 1) {
                this.correctAnswer = i;
            }
        }
    }

    public String getRightAnswer() {
        return "" + (char) ('A' + correctAnswer);
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public String[] getAnswers() {
        return answers;
    }
}