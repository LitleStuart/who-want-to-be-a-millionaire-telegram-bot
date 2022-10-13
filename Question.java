import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class Question {
    class Answer {
        public String answer;
        public boolean isCorrect;

        public Answer(String answer, boolean isCorrect) {
            this.answer = answer;
            this.isCorrect = isCorrect;
        }
    }

    private final String question;
    private final ArrayList<Answer> answers;

    public Question(String question, JSONArray answers) {
        this.question = question;
        this.answers = new ArrayList<>();

        for (int index = 0; index < 4; index++) {
            JSONObject answerJSON = answers.getJSONObject(index);
            Answer answer = new Answer(answerJSON.getString("answer"),
                    Objects.equals(answerJSON.getInt("key"), 1));
            this.answers.add(answer);
        }
    }

    /**
     * Returns question by index.
     * @return Questions (ArrayList)
     * @throws IOException
     */
    public ArrayList<Question> getQuestion(int index) throws IOException {
        String url = "https://ru.wwbm.com/game/get-question/";

        URL questionUrl = new URL(url + index);
        JSONObject json = BuildJSONObjest.getResponse(questionUrl);
        Question question = new Question(json.getString("question"), json.getJSONArray("answers"));
        return question;
    }

    public ArrayList<Answer> getAnswers() {

        return answers;
    }
}