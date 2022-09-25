public class ConsoleChatBotProgram {
    public static void main(String[] args) {
        ChatBot bot = new ChatBot(new ConsoleBotApi());
        bot.run();
    }
}