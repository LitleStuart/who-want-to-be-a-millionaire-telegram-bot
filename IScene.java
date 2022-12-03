import java.io.IOException;

public interface IScene {
    public void handleMessage(User user, BotMessage botMessage) throws IOException;
}
