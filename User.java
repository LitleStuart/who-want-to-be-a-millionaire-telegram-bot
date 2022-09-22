import java.util.Map;
import java.util.HashMap;
public class User {
    //constructor
    int userID;
    public enum DialogState {
        NO_MESSAGE,CHATTING,STARTED,SPECIAL_COMMANDS;
    }
    //add history / add file for saving
    Map <String, String> userInfo = new HashMap<String,String>();
    //userInfo.put("Name","pupsich");

}
