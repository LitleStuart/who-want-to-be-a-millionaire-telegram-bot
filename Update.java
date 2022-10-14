public class Update {
    long chatId;
    Message message;
    String username;

    public Update(long chatId, String username, Message message) {
        this.chatId = chatId;
        this.message = message;
        this.username = username;
    }
}
