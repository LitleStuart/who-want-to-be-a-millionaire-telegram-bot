public class Update {
    long chatId;
    Message message;

    Update(long id, Message message) {
        this.chatId = id;
        this.message = message;
    }
}
