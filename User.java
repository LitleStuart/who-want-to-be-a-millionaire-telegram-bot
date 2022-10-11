public class User {
    long id;
    IScene scene;

    String name;
    int highScore;
    // in-game variables
    Game game;
    boolean isInGame;
    int curQuestion;
    int hints;

    User(long chatId, IScene scene) {
        this.id = chatId;
        this.scene = scene;
        name = new String();
        highScore = 0;
    }

    public void startGame() {
        isInGame = true;
        game = new Game();
        curQuestion = 0;
        hints = 1;
    }
}
