import org.glassfish.grizzly.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Buttons {
    public List<List <Pair<String,String>>> buttons = null;
    public void createAnswerButtons(Question question) {
        Map <String,String> variants = question.getAllAnswers();
        List<List<Pair<String,String>>> keyBoard = new ArrayList<>();
        List<Pair<String,String>> keyBoardRow = new ArrayList<>();
        int i=0;
        for (String key: variants.keySet()){
            Pair<String,String> newButton = new Pair<String,String>(variants.get(key), key);
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
        keyBoardRow = new ArrayList<>();
        keyBoardRow.add(new Pair <>("Использовать подсказку", "/hint"));
        keyBoard.add(keyBoardRow);
        this.buttons=keyBoard;
    }
    public void createHintButtons(HashMap <String, Integer> hints) {
        List <Pair<String,String>> keyBoardRow = new ArrayList<>();
        for (String key:hints.keySet()){
            Pair<String,String> newButton = new Pair<String,String>(key, key);
            keyBoardRow.add(newButton);
        }
        List<List <Pair<String,String>>> keyBoard = new ArrayList<>();
        keyBoard.add(keyBoardRow);
        this.buttons=keyBoard;
    }

    public void createHelpButtons(String name) {
        List<List <Pair<String,String>>> keyBoard = new ArrayList<>();
        List <Pair<String,String>> keyBoardRow = new ArrayList<>();
        keyBoardRow.add(new Pair<>("Help "+name, "/HelpAccepted "+name));
        keyBoard.add(keyBoardRow);
        this.buttons=keyBoard;
    }

    public List<List<Pair<String,String>>> getButtons() {return this.buttons;}
}
