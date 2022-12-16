public interface IBotApi {
    void sendBotToUserMessage(long chatId, String text, Buttons... buttons);
    void sendBotToUserMessage(String username, String text, Buttons... buttons);
    void sendUserToUserMessage(String senderUsername, String receiverUsername);
    void editMessage(long chatId, BotMessage newMessage, Buttons... buttons);
    void deleteMessage(long chatId, long messageId);
}
