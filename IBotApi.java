public interface IBotApi {
    public void registerOnUpdate(IOnUpdate updateHandler);

    public void sendAnswer(String text);
}
