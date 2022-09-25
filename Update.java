import java.util.Scanner;

public class Update {
    int chatId;
    Message message;

    Update(int id) {
        this.chatId = id;

        Scanner in = new Scanner(System.in);
        this.message = new Message(in.nextLine());
    }
}
