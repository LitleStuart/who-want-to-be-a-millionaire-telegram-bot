import java.io.IOException;
import java.net.MalformedURLException;

public class User {
    long id;
    IScene scene;

    String name;
    int highScore;
    Question currentQuestion;
    int currentQuestionIndex;
    int hints;

    User(long chatId, String username, IScene scene) {
        this.id = chatId;
        this.scene = scene;
        this.name = username;
        highScore = 0;
    }

    public String nextQuestion() throws MalformedURLException, IOException {
        Question question = new QuestionBuilder().getQuestion(currentQuestionIndex);
        this.currentQuestion = question;
        String result = question.getTextQuestion() + '\n';
        for (int i = 0; i < 4; i++) {
            result += (char) ('A' + i) + ": "
                    + question.getAnswers()[i] + '\n';
        }
        return result;
    }
}
