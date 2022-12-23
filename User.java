import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class User {
    long id;
    /*
    IScene scene;
    IScene prevScene;
    Хранить id/название сцены вместо самой сцены???
    */
    String sceneState;
    String username;
    int highScore;
    Question currentQuestion;
    Question storedQuestion;
    int currentQuestionIndex = 0;
    HashMap<String, Integer> hints;
    Stack<Long> lastResponseMessageId;
    String receiver;
    Boolean secondChance;

    User(long chatId, String username, IScene scene) {
        this.id = chatId;
        /*
        this.scene = scene;
        this.prevScene = null;
        */
        this.sceneState = "MainMenu";
        this.username = username;
        this.highScore = 0;
        this.hints = new HashMap<>();
        this.currentQuestion = null;
        this.lastResponseMessageId = new Stack<Long>();
        this.secondChance = false;
    }

    User(long userId, String username, int currentQuestionIndex, int highscore, IScene scene) {
        this(userId, username, scene);
        this.currentQuestionIndex = currentQuestionIndex;
        this.highScore = highscore;
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
