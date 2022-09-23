import java.util.Scanner;

public class Message {
    String text;

    Message() {
        Scanner in = new Scanner(System.in);
        this.text = in.nextLine();
    }
}
