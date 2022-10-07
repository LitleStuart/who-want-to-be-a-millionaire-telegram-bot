import org.json.*;
import java.io.*;
import java.net.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final String url = "https://ru.wwbm.com/game/get-question/";

    public Game() {}

    private JSONObject getResponse(URL url) throws IOException {
        StringBuilder result = new StringBuilder();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return new JSONObject(result.toString());
    }

    public ArrayList<Question> getQuestions() throws IOException {
        ArrayList<Question> questions = new ArrayList<>();
        for (int index = 1; index <= 15; index++) {
            URL questionUrl = new URL(this.url + index);
            JSONObject json = getResponse(questionUrl);
            Question question = new Question(json.getString("question"), json.getJSONArray("answers"));
            questions.add(question);
        }

        return questions;
    }
}
