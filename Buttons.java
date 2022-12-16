import org.glassfish.grizzly.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class Buttons {
    public List<List <Pair<String,String>>> buttons = null;
    public void createAnswerButtons(List<String> answerText) {
        List<List<Pair<String,String>>> keyBoard = new ArrayList<>();
        List<Pair<String,String>> keyBoardRow = new ArrayList<>();
        int i=0;
        for (String text: answerText){
            Pair<String,String> newButton = new Pair<String,String>(text, ""+text.charAt(0));
            keyBoardRow.add(newButton);
            if (i>0 && i%2==1){
                keyBoard.add(keyBoardRow);
                keyBoardRow = new ArrayList<>();
            }
            i++;
        }
        if (keyBoardRow.size()>0) {
            keyBoard.add(keyBoardRow);
        }
        this.buttons=keyBoard;
    }
    public void createHintButtons(List<String> hints) {
        List <Pair<String,String>> keyBoardRow = new ArrayList<>();
        for (String key: hints){
            Pair<String,String> newButton = new Pair<String,String>(key, key);
            keyBoardRow.add(newButton);
        }
        List<List <Pair<String,String>>> keyBoard = new ArrayList<>();
        keyBoard.add(keyBoardRow);
        this.buttons=keyBoard;
    }

    public void createHelpButton(String name) {
        List<List <Pair<String,String>>> keyBoard = new ArrayList<>();
        List <Pair<String,String>> keyBoardRow = new ArrayList<>();
        keyBoardRow.add(new Pair<>("Help "+name, "/HelpAccepted "+name));
        keyBoard.add(keyBoardRow);
        this.buttons=keyBoard;
    }

    public void addHintButton(){
        List<List<Pair<String,String>>> keyBoard = this.buttons;
        List<Pair<String,String>> keyBoardRow = new ArrayList<>();
        keyBoardRow.add(new Pair <>("Использовать подсказку", "/hint"));
        keyBoard.add(keyBoardRow);
    }
    public List<List<Pair<String,String>>> getButtons() {return this.buttons;}
}
