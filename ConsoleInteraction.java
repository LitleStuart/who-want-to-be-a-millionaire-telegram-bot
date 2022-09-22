import java.util.Scanner;

public class ConsoleInteraction
{
    public static void StartConsoleInteraction(){
        input();
        //output(ChatBot.generateAnswer());
        //change dialogState;

    }
    public static String input(){
        Scanner scanner = new Scanner(System. in);
        String inputMessage = scanner. nextLine();
        return inputMessage;
    }
    public static void output(String inputMessage){
        System.out.println("Your string from console: " + inputMessage);
    }

}
