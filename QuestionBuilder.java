import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class QuestionBuilder {

    private JSONObject getJsonQuestionBody(int level) throws IOException {
        String urlString = "https://ru.wwbm.com/game/get-question/";
        URL url = new URL(urlString + level);
        StringBuilder result = new StringBuilder();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null;) {
                result.append(line);
            }
        }
        return new JSONObject(result.toString());
    }

    private Question jsonToQuestion(JSONObject json) throws IOException {
        String[] answers = new String[4];
        int correctAnswer = 0;

        for (int i = 0; i < 4; i++) {
            JSONObject jsonAnswer = json.getJSONArray("answers").getJSONObject(i);
            String textAnswer = jsonAnswer.getString("answer");
            int key = jsonAnswer.getInt("key");

            answers[i] = textAnswer;
            if (key == 1) {
                correctAnswer = i;
            }
        }

        return new Question(json.getString("question"), answers, correctAnswer);
    }

    public Question getQuestion(int level) throws MalformedURLException, IOException {
        return jsonToQuestion(getJsonQuestionBody(level));
    }

    public String nextQuestionForUser(User user) throws MalformedURLException, IOException {
        Question question = getQuestion(user.currentQuestionIndex);
        user.currentQuestion = question;
        String result = question.getTextQuestion() + '\n';
        for (int i = 0; i < 4; i++) {
            result += (char) ('A' + i) + ": "
                    + question.getAnswers()[i] + '\n';
        }
        return result;
    }
}