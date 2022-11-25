
import org.glassfish.grizzly.utils.Pair;
import java.util.List;


public interface IButtons {
    public List<List <Pair<String,String>>> CreateAnswerKeyBoard(String text);
    public List<List <Pair<String,String>>> CreateHintsKeyBoard(String text);
}
