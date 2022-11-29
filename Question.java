import java.util.HashMap;
import java.util.Map;

public class Question {
    public class Answer {
        public String answer;
        public int key;
    }

    public Map<String, Answer> variants = new HashMap<String, Answer>();

    public int id;
    public String question;
    public Answer[] answers;

    public void generateVariantsMap() {
        for (int i = 0; i < 4; i++) {
            variants.put("" + (char) ('A' + i), answers[i]);
        }
    }

    public boolean isRightAnswer(String letter) {
        return variants.get(letter).key == 1;
    }

    public String getTextQuestion() {
        return question;
    }

    public String getAnswerText(String letter) {return this.variants.get(letter).answer;}
}
