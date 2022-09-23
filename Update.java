public class Update {
    int chatId;
    Message message;

    Update(int id) {
        this.chatId = id;
        this.message = new Message();
    }
}
