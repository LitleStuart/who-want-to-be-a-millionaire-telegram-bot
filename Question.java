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
    private boolean hintUsed = false;

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

    public String getQuestion() {
        return question;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public boolean hintIsUsed() {
        return hintUsed;
    }

    public void useHint() {
        hintUsed = true;
    }
}