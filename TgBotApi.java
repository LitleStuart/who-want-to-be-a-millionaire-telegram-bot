import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TgBotApi extends TelegramLongPollingBot implements IBotApi {
    private Bot bot;
    private String botName;
    private String botToken;

    TgBotApi(IQuestionProvider questionProvider) {
        SceneFactory sceneFactory = new SceneFactory(this, questionProvider);
        bot = new Bot(sceneFactory);
        try (Scanner s = new Scanner(new File("botinfo.txt")).useDelimiter("\\n")) {
            this.botName = s.next();
            this.botToken = s.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendBotToUserMessage(long chatId, String text, Buttons... buttons) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        if (buttons.length > 0) {
            TgButtons tgButtons = new TgButtons();
            sendMessage.setReplyMarkup(tgButtons.createTgKeyBoard(buttons[0]));
        }

        try {
            long lastMessageId = execute(sendMessage).getMessageId();
            if (buttons.length > 0) {
                bot.setLastRespMessageId(lastMessageId, chatId);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendBotToUserMessage(String username, String text, Buttons... buttons) {
        long chatId = bot.getChatId(username);
        sendBotToUserMessage(chatId, text, buttons);
    }

    @Override
    public void editMessage(long chatId, BotMessage newMessage, Buttons... buttons) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId((int) newMessage.messageId);
        editMessageText.setChatId(chatId);
        editMessageText.setText(newMessage.text);
        if (buttons.length > 0) {
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
        deleteMessage.setMessageId((int) messageId);
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendUserToUserMessage(String senderUsername, String receiverUsername) {
        long senderId = bot.getChatId(senderUsername);
        long receiverId = bot.getChatId(receiverUsername);
        try {
            bot.transferQuestion(senderId, receiverId);
            return;
        } catch (NullPointerException e) {
            System.out.println("Transfering from " + senderUsername + " to " + receiverUsername);
            sendBotToUserMessage(senderId, "Игрок " + receiverUsername + " не найден");
            e.printStackTrace();
        }

    }

    public String getLeaderboard() {
        return bot.getLeaderboard();
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
            System.out.println(chatId + " – " + username + " – " + text);
            try {
                bot.handleMessage(new Update(chatId, username, new BotMessage(text, messageId)));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (update.hasCallbackQuery()) {
            Message message = update.getCallbackQuery().getMessage();
            String text = update.getCallbackQuery().getData();
            long chatId = message.getChatId();
            long messageId = message.getMessageId();
            String username = message.getFrom().getUserName() != null
                    ? message.getFrom().getUserName()
                    : message.getFrom().getFirstName();

            System.out.println(chatId + " – " + username + " – " + text);
            if (message.getMessageId() == bot.getLastRespMessageId(chatId)) {
                try {
                    bot.handleMessage(new Update(chatId, username, new BotMessage(text, messageId)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
