import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TgBotProgram {
    public static void main(String[] args) {
        try {
            IQuestionProvider questionProvider = new JsonQuestionProvider();
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TgBotApi(questionProvider));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}