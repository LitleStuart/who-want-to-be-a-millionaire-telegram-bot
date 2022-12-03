import org.glassfish.grizzly.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class Buttons {
    public List<List <Pair<String,String>>> buttons = null;
    public void createAnswerButtons(String message, int numberOfAnswers) {
        System.out.println(message);
        ArrayList<String> variants = new ArrayList<>();
        variants.addAll( List.of( message.split( "\n" ) ) );
        while (variants.size()>numberOfAnswers) {
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
        this.buttons=keyBoard;
    }
    public void createHintButtons(String message, int numberOfHints) {
        System.out.println(message);
        ArrayList<String> variants = new ArrayList<>();
        variants.addAll( List.of( message.split( "\n" ) ) );
        while (variants.size()>numberOfHints) {
            variants.remove( 0 );
        }
        System.out.println(variants);
        List <Pair<String,String>> keyBoardRow = new ArrayList<>();
        for (int i = 0; i < variants.size(); i++){
            Pair<String,String> newButton = new Pair<String,String>(variants.get(i), variants.get(i));
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
