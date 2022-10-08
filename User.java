import java.util.ArrayList;

public class User {
    int id;
    Message lastMessage;

    String name;
    int highScore;
    //in-game variables
    Game game;
    boolean isInGame;
    int curQuestion;
    int hints;


    User(int id) {
        this.id = id;
        lastMessage=new Message (new String ());
        name = new String ();
        highScore = 0;
    }

    public void startGame() {
        isInGame = true;
        game=new Game ();
        curQuestion = 0;
        hints=1;
    }
}
