import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BuildJSONObject{

    public BuildJSONObject(){

    }
    /**
     * Processes GET API call and returns response.
     * @param url URL of question
     * @return Response (JSONObject)
     * @throws IOException
     */

    public JSONObject getResponse(URL url) throws IOException {
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

    /**
     * Returns question by index.
     * @return Questions (ArrayList)
     * @throws IOException
     */
    public Question toQuestion(int index) throws IOException {
        String url = "https://ru.wwbm.com/game/get-question/";

        URL questionUrl = new URL(url + index);
        BuildJSONObject jsonObjectBuilder=new BuildJSONObject();
        JSONObject json = jsonObjectBuilder.getResponse(questionUrl);
        Question question = new Question(json.getString("question"), json.getJSONArray("answers"));
        return question;
    }

}