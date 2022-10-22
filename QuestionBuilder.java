import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class QuestionBuilder {

    private String getJsonQuestionBody(int level) throws IOException {
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
        return result.toString();
    }

    private Question jsonToQuestion(String json) throws IOException {
        Gson gson = new Gson();
        Question question = gson.fromJson(json, Question.class);
        question.generateVariantsMap();

        return question;
    }

    public Question getQuestion(int level) throws MalformedURLException, IOException {
        return jsonToQuestion(getJsonQuestionBody(level));
    }

    public String nextQuestionForUser(User user) throws MalformedURLException, IOException {
        Question question = getQuestion(user.currentQuestionIndex);
        user.currentQuestion = question;
        String result = question.getTextQuestion() + '\n';
        for (int i = 0; i < 4; i++) {
            char letter = (char) ('A' + i);
            result += letter + ": " + question.variants.get("" + letter).answer + '\n';
        }
        return result;
    }
}