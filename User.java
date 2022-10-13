public class User {
    long id;
    IScene scene;

    String name;
    int highScore;
    // in-game variables
    Question curQuestion;
    boolean isInGame;
    int curQuestionIndex;
    int hints;

    User(long chatId, IScene scene) {
        this.id = chatId;
        this.scene = scene;
        name = new String();
        highScore = 0;
    }

    public void startGame() {
        isInGame = true;
        curQuestionIndex = 1;
        hints = 1;
    }
}
