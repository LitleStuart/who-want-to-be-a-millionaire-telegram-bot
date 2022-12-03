import java.util.HashMap;
import java.util.Stack;

public class User {
    long id;
    IScene scene;

    String name;
    int highScore;
    Question currentQuestion;
    Question storedQuestion;
    int currentQuestionIndex;
    HashMap<String, Integer> hints;
    Stack<Long> lastResponseMessageId;
    String receiver;
    Boolean secondChance;

    User(long chatId, String username, IScene scene) {
        this.id = chatId;
        this.scene = scene;
        this.name = username;
        this.highScore = 0;
        this.hints = new HashMap<>();
        this.currentQuestion = null;
        this.lastResponseMessageId = new Stack <Long>();
        this.secondChance=false;
    }

    public void createHints(){
        this.hints.put("50/50",1);
        this.hints.put("Call",1);
        this.hints.put("x2",1);
    }

    public String getHints(){
        String result = new String();
        for (String hint:hints.keySet()) {
            if (hints.get( hint ) > 0) {
                result += "\n"+hint;
            }
        }
        return result;
    }
}
