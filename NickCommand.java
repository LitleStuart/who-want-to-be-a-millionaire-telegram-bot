public class NickCommand {
    public String doCommand(User user, String[] message) {
        if (message.length == 2) {
            if (user.name.isEmpty()) {
                user.name = message[1];
                return "Your name set to " + message[1];
            } else {
                user.name = message[1];
                return "Your name was changed to " + message[1];
            }

        } else {
            return "Неправильный формат ввода, используйте /help для получения информации";
        }
    }
}
