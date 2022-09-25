public class TgChatBotProgram {
    public static void main(String[] args) {
        ChatBot bot = new ChatBot(new TgBotApi());
        bot.run();
    }
}