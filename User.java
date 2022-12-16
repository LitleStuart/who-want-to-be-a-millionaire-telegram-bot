import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class User {
    long id;
    IScene scene;
    IScene prevScene;
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
        this.prevScene = null;
        this.name = username;
        this.highScore = 0;
        this.hints = new HashMap<>();
        this.currentQuestion = null;
        this.lastResponseMessageId = new Stack<Long>();
        this.secondChance = false;
    }

    public void createHints() {
        this.hints.put("50/50", 1);
        this.hints.put("Call", 1);
        this.hints.put("x2", 1);
    }

    public void remLastCallBack() {
        if (lastResponseMessageId.size() > 0) {
            lastResponseMessageId.pop();
        }
    }

    public List<String> getHints() {
        return hints.keySet().stream().toList();
    }

    /*
     * FOR DEBUG ONLY
     * public void printResp(){
     * System.out.println(lastResponseMessageId);
     * }
     */
}
