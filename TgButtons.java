import org.glassfish.grizzly.utils.Pair;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class TgButtons implements IButtons{
    public List <List <Pair <String, String>>> CreateAnswerKeyBoard(String text) {
        ArrayList<String> variants = new ArrayList<>();
        variants.addAll( List.of( text.split( "\n" ) ) );
        while (variants.size()>4) {
            variants.remove( 0 );
        }
        List<List<Pair<String,String>>> keyBoard = new ArrayList<>();
        List<Pair<String,String>> keyBoardRow = new ArrayList<>();
        for (int i = 0; i < variants.size(); i++){
            Pair<String,String> newButton = new Pair<String,String>(variants.get(i), variants.get(i).substring(0,1));
            keyBoardRow.add(newButton);
            if (i>0 && i%2==1){
                keyBoard.add(keyBoardRow);
                keyBoardRow = new ArrayList<>();
            }
        }
        if (keyBoardRow.size()>0) {
            keyBoard.add(keyBoardRow);
        }
        return keyBoard;
    }
    public List<List <Pair<String,String>>> CreateHintsKeyBoard(String text){
        ArrayList<String> variants = new ArrayList<>();
        variants.addAll( List.of( text.split( "\n" ) ) );
        while (variants.size()>3) {
            variants.remove( 0 );
        }
        List <Pair<String,String>> keyBoardRow = new ArrayList<>();
        for (int i = 0; i < variants.size(); i++){
            Pair<String,String> newButton = new Pair<String,String>(variants.get(i), variants.get(i).substring(0,1));
            keyBoardRow.add(newButton);
        }
        List<List <Pair<String,String>>> keyBoard = new ArrayList<>();
        keyBoard.add(keyBoardRow);
        return keyBoard;
    }

    public InlineKeyboardMarkup createTgKeyBoard(SendMessage message, String buttonType) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        List<List <Pair<String,String>>> keys = new ArrayList<>();
        switch (buttonType){
            case ("withAnswers"):
            {
                keys = this.CreateAnswerKeyBoard( message.getText() );
                break;
            }
            case ("withHints"):
            {
                keys = this.CreateHintsKeyBoard( message.getText() );
                break;
            }
        }
        for (List<Pair<String,String>> row: keys) {
            List <InlineKeyboardButton> keyboardRow = new ArrayList <>();
            for (Pair<String,String> key: row) {
                InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
                keyboardButton.setText(key.getFirst());
                keyboardButton.setCallbackData(key.getSecond());
                keyboardRow.add( keyboardButton );
            }
            rowList.add( keyboardRow );
        }
        keyboardMarkup.setKeyboard( rowList );
        return keyboardMarkup;
    }
}
