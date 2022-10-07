import java.util.ArrayList;

public class User {
    long id;
    String name = "";
    int highScore = 0;
    int currentScore = 0;
    ArrayList<Message> messages = new ArrayList<>();

    User(int id) {
        this.id = id;
    }
}
