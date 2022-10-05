import java.util.Scanner;

public class ConsoleChatBotProgram {
    public static void main(String[] args) {
        ChatBot bot = new ChatBot((chatId, text) -> {
            System.out.println(text);
        });

        Scanner in = new Scanner(System.in);
        while (true) {
            bot.handleMessage(new Update(0, new Message(in.nextLine())));
        }
    }
}