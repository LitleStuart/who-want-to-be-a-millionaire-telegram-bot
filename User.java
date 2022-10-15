public class User {
    long id;
    IScene scene;

    String name;
    int highScore;
    Question currentQuestion;
    int currentQuestionIndex;
    int hints;

    User(long chatId, String username, IScene scene) {
        this.id = chatId;
        this.scene = scene;
        this.name = username;
        highScore = 0;
    }
}
