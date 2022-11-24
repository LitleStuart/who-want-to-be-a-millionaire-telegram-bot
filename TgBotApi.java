import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TgBotApi extends TelegramLongPollingBot implements IBotApi {
    private Bot bot;

    TgBotApi() {
        bot = new Bot(this);
    }

    @Override
    public void sendAnswer(long chatId, String text, Boolean... withChoice) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        if (withChoice.length>0&&withChoice[0]) {
            ArrayList<String> variants = new ArrayList<>();
            variants.addAll( List.of( text.split( "\n" ) ) );
            while (variants.size()>4) {
                variants.remove( 0 );
            }
            KeyBoardCreator keyBoardCreator = new KeyBoardCreator();
            sendMessage.setReplyMarkup( keyBoardCreator.createAnswerKeyBoard( variants ) );
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(org.telegram.telegrambots.meta.api.objects.Update update) {
        if (update.hasMessage()) {
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String username = update.getMessage().getFrom().getUserName() != null
                    ? update.getMessage().getFrom().getUserName()
                    : update.getMessage().getFrom().getFirstName();
            try {
                bot.handleMessage( new Update( chatId, username, new Message( message ) ) );
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if (update.hasCallbackQuery()) {
            System.out.println("callback");
                String message = update.getCallbackQuery().getData();
                long chatId = update.getCallbackQuery().getFrom().getId();
                System.out.println(message);
                System.out.println(chatId);
                String username = update.getCallbackQuery().getFrom().getUserName() != null
                        ? update.getCallbackQuery().getFrom().getUserName()
                        : update.getCallbackQuery().getFrom().getFirstName();
                try {
                    bot.handleMessage( new Update( chatId, username, new Message( message ) ) );
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public String getBotUsername() {
        return "MillBotTest";
    }

    @Override
    public String getBotToken() {
        return "5974442642:AAFyxQBpcK7T93UkKmp7vTLi81hxApUl0nc";
    }

}
