import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question {
    public class Answer {
        public String answer;
        public int key;
    }

    private Map<String, Answer> variants = new HashMap<String, Answer>();

    private String question;
    private Answer[] answers;

    private Map<String, String> answersText = new HashMap();
    private boolean hintIsUsed=false;

    public void generateVariantsMap() {
        for (int i = 0; i < 4; i++) {
            variants.put("" + (char) ('A' + i), answers[i]);
        }
    }
    public void updateAnswerText() {
        for (int i = 0; i < 4; i++){
            String ans = variants.get(""+(char)('A'+i)).answer;
            answersText.put(""+(char)('A'+i), ans);
        }
    }

    public boolean isRightAnswer(String letter) {
        return variants.get(letter).key == 1;
    }

    public void deleteAnswer(String letter) {
        variants.remove(letter);
        answersText.remove(letter);
    }

    public int numberOfAnswers() {return variants.size();}

    public String getTextQuestion() {
        return question;
    }

    public String getAnswerText(String letter) {return this.variants.get(letter).answer;}

    public Map<String, String> getAllAnswers() {
        return answersText;
    }
}
