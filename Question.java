import java.util.HashMap;
import java.util.Map;

public class Question {
    public class Answer {
        public String answer;
        public int key;
    }

    private Map<String, Answer> variants = new HashMap<String, Answer>();

    private String question;
    private Answer[] answers;

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

    public void deleteAnswer(String letter) {variants.remove( letter );}

    public int numberOfAnswers() {return variants.size();}

    public String getAllAnswerText() {
        String text = new String();
        for (String val:variants.keySet()){
            text += val + ": " + variants.get(val).answer + '\n';
        }
        return text;
    }
}
