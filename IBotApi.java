public interface IBotApi {
    void sendMessage(long chatId, String text, Buttons... buttons);
    void sendMessage(String username, String text, Buttons... buttons);
    void sendMessage(String sender, String receiver);
    void editMessage(long chatId, BotMessage newMessage, Buttons... buttons);
    void deleteMessage(long chatId, long messageId);
}
