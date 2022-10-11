public class Message {
    String text;
    boolean isCommand = false;

    Message(String text) {
        this.text = text;
    }

    Message(String text, boolean isCommand) {
        this.text = text;
        this.isCommand = isCommand;
    }
}
