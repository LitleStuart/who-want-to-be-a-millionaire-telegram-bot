import java.util.ArrayList;

public class User {
    int id;
    ArrayList <Message> messages = new ArrayList <> ();

    String name;
    int highScore;
    //in-game variables???

    User(int id) {
        this.id = id;
        name = new String ();
        highScore = 0;
    }
}
