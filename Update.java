public class Update {
    long chatId;
    BotMessage botMessage;
    String username;

    public Update(long chatId, String username, BotMessage botMessage) {
        this.chatId = chatId;
        this.botMessage = botMessage;
        this.username = username;
    }
}
