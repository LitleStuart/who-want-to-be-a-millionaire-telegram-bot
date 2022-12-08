import java.io.IOException;

public interface IQuestionProvider {
    void nextQuestionForUser(User user) throws IOException;
}
