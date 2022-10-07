import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class Question {
    private final String question;
    private final HashMap<String, Boolean> answers;

    public Question(String question, JSONArray answers) {
        this.question = question;
        this.answers = new HashMap<>();

        for (int index = 0; index < 4; index++) {
            JSONObject answer = answers.getJSONObject(index);
            this.answers.put(answer.getString("answer"), Objects.equals(answer.getInt("key"), 1));
        }
    }

    public String getQuestion() {
        return question;
    }

    public HashMap<String, Boolean> getAnswers() {
        return answers;
    }
}