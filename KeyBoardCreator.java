import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyBoardCreator {
    public InlineKeyboardMarkup createAnswerKeyBoard(String text) {
        ArrayList<String> variants = new ArrayList<>();
        variants.addAll( List.of( text.split( "\n" ) ) );
        while (variants.size()>4) {
            variants.remove( 0 );
        }
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List <InlineKeyboardButton> keyBoardRow = new ArrayList <>();
        for (int i = 0; i < variants.size(); i++){
            InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
            keyboardButton.setText( variants.get(i));
            char CB = (char) ('A'+i);
            keyboardButton.setCallbackData(""+CB);
            keyBoardRow.add( keyboardButton );
            if (i>0 && i%2==1){
                rowList.add(keyBoardRow);
                keyBoardRow = new ArrayList<>();
            }
        }
        rowList.add(keyBoardRow);
        keyboardMarkup.setKeyboard( rowList );
        return keyboardMarkup;
    }
    public InlineKeyboardMarkup createHintsKeyBoard(String text){

        ArrayList<String> variants = new ArrayList<>();
        variants.addAll( List.of( text.split( "\n" ) ) );
        while (variants.size()>3) {
            variants.remove( 0 );
        }
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List <InlineKeyboardButton> keyBoardRow = new ArrayList <>();
        for (int i = 0; i < variants.size(); i++){
            InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
            keyboardButton.setText( variants.get(i).substring(2));
            keyboardButton.setCallbackData(variants.get(i).substring(0,1));
            keyBoardRow.add( keyboardButton );
        }
        rowList.add(keyBoardRow);
        keyboardMarkup.setKeyboard( rowList );
        return keyboardMarkup;
    }
}
