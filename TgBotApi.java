import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class TgBotApi extends TelegramLongPollingBot implements IBotApi {
    private IOnUpdate updateHandler;

    @Override
    public void registerOnUpdate(IOnUpdate onUpdate) {
        this.updateHandler = onUpdate;
    }

    @Override
    public void sendAnswer(long chatId, String text) {
        System.out.println(text);
    }

    @Override
    public void onUpdateReceived(org.telegram.telegrambots.meta.api.objects.Update update) {
        String message = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        updateHandler.onUpdate(new Update(chatId, new Message(message)));
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }

}
