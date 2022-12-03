public interface IBotApi {
    public void sendAnswer(long chatId, String text, Buttons... buttons);
    public void sendAnswer(String username, String text, Buttons... buttons);
    public void editMessage(long chatId, BotMessage newMessage, Buttons... buttons);
    public void deleteMessage(long chatId, long messageId);
    //???
    public void transferQuestion(String sender, String receiver);
}
