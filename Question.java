import org.json.JSONArray;
import org.json.JSONObject;

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
            JSONObject answer = answers.getJSONObject(index);
            this.answers.add(new Answer(answer.getString("answer"), Objects.equals(answer.getInt("key"), 1)));
        }
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }
}