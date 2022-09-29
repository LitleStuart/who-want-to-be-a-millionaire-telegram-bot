public interface IBotApi {
    public void registerOnUpdate(IOnUpdate updateHandler);

    public void sendAnswer(long chatId, String text);
}
