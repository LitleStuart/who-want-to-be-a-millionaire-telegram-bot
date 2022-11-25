public interface IBotApi {
    public void sendAnswer(long chatId, String text, String... specialFlags);
}
