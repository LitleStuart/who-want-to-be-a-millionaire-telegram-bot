import java.io.IOException;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TgBotApi extends TelegramLongPollingBot implements IBotApi {
    private Bot bot;

    TgBotApi() {
        bot = new Bot(this);
    }

    @Override
    public void sendAnswer(long chatId, String text, Buttons... buttons) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        if (buttons.length>0)
        {
            TgButtons tgButtons = new TgButtons();
            sendMessage.setReplyMarkup(tgButtons.createTgKeyBoard( buttons[0] ));
        }

        try {
            long lastMessageId = execute(sendMessage).getMessageId();
            if (buttons.length>0)
            {
                bot.setLastRespMessageId(lastMessageId, chatId);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendAnswer(String username, String text, Buttons... buttons) {
        long chatId = bot.getChatId(username);
        sendAnswer(chatId, text, buttons);
    }

    @Override
    public void editMessage(long chatId, BotMessage newMessage, Buttons... buttons) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId((int)newMessage.messageId);
        editMessageText.setChatId(chatId);
        editMessageText.setText(newMessage.text);
        if (buttons.length>0)
        {
            TgButtons tgButtons = new TgButtons();
            editMessageText.setReplyMarkup(tgButtons.createTgKeyBoard(buttons[0]));
        }
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteMessage(long chatId, long messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId((int)messageId);
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void transferQuestion(String sender, String receiver) {
        long senderId = bot.getChatId(sender);
        long receiverId = bot.getChatId(receiver);
        bot.transferQuestion(senderId, receiverId);
    }

    @Override
    public void onUpdateReceived(org.telegram.telegrambots.meta.api.objects.Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String text = message.getText();
            long chatId = message.getChatId();
            long messageId = message.getMessageId();
            String username = message.getFrom().getUserName() != null
                    ? message.getFrom().getUserName()
                    : message.getFrom().getFirstName();
            try {
                bot.handleMessage( new Update( chatId, username, new BotMessage( text, messageId  ) ) );
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if (update.hasCallbackQuery()) {
                Message message = update.getCallbackQuery().getMessage();
                String text = update.getCallbackQuery().getData();
                long chatId = message.getChatId();
                long messageId = message.getMessageId();
                String username = message.getFrom().getUserName() != null
                        ? message.getFrom().getUserName()
                        : message.getFrom().getFirstName();

                if (message.getMessageId() == bot.getLastRespMessageId(chatId))
                {
                    System.out.println(text);
                    try {
                        bot.remLastRespMessageId(chatId);
                        bot.handleMessage( new Update( chatId, username, new BotMessage( text, messageId  ) ) );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

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
