import java.io.IOException;

public interface IScene {
    public void handleMessage(User user, Message message) throws IOException;
}
