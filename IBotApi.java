public interface IBotApi {
    public void registerOnUpdate(IOnUpdate onUpdate);

    public void sendAnswer(long chatId, String message);
}
