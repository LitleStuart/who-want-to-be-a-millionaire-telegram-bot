public class Question {
    private String textQuestion;
    private String[] answers = new String[4];
    private int correctAnswer;

    Question(String textQuestion, String[] answers, int correctAnswer) {
        this.textQuestion = textQuestion;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
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