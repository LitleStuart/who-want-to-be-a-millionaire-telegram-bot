import java.util.ArrayList;

public class User {
    int id;
    ArrayList<Message> messages = new ArrayList<>();

    User(int id) {
        this.id = id;
    }
}
