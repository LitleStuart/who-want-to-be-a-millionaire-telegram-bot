import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Game {
    public ArrayList<Question> questions;

    Game() {
        try {
            questions = this.buildQustions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getResponse(URL url) throws IOException {
        StringBuilder result = new StringBuilder();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null;) {
                result.append(line);
            }
        }
        return new JSONObject(result.toString());
    }

    private ArrayList<Question> buildQustions() throws IOException {
        String url = "https://ru.wwbm.com/game/get-question/";

        ArrayList<Question> questions = new ArrayList<>();
        for (int index = 1; index <= 15; index++) {
            URL questionUrl = new URL(url + index);
            JSONObject json = getResponse(questionUrl);
            Question question = new Question(json.getString("question"), json.getJSONArray("answers"));
            questions.add(question);
        }
        return questions;
    }
}