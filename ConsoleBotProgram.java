import java.util.Scanner;

public class ConsoleBotProgram {
    public static void main(String[] args) {
        Bot bot = new Bot((chatId, text) -> {
            System.out.println(text);
        });

        Scanner in = new Scanner(System.in);
        while (true) {
            bot.handleUpdate(new Update(0, new Message(in.nextLine())));
        }
    }
}