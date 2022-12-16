import org.glassfish.grizzly.utils.Pair;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class TgButtons {
    public InlineKeyboardMarkup createTgKeyBoard(Buttons buttons) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (List<Pair<String, String>> row : buttons.getButtons()) {
            List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
            for (Pair<String, String> key : row) {
                InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
                keyboardButton.setText(key.getFirst());
                keyboardButton.setCallbackData(key.getSecond());
                keyboardRow.add(keyboardButton);
            }
            rowList.add(keyboardRow);
        }
        keyboardMarkup.setKeyboard(rowList);
        return keyboardMarkup;
    }
}
