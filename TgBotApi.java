import java.io.IOException;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TgBotApi extends TelegramLongPollingBot implements IBotApi {
    private Bot bot;

    TgBotApi() {
        bot = new Bot(this);
    }

    @Override
    public void sendAnswer(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(org.telegram.telegrambots.meta.api.objects.Update update) {
        String message = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        String username = update.getMessage().getFrom().getUserName() != null
                ? update.getMessage().getFrom().getUserName()
                : update.getMessage().getFrom().getFirstName();
        try {
            bot.handleMessage(new Update(chatId, username, new Message(message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
