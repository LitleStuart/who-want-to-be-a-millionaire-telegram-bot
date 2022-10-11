public class ChatCommands {
    public String respond(User user, String message) {
        String responceMessage = new String();
        switch (message) {
            case ("/help"): {
                responceMessage = new HelpCommand().doCommand();
                break;
            }
            case ("/start"): {
                responceMessage = new StartCommand().doCommand(user);
                break;
            }
            case ("/info"): {
                responceMessage = new InfoCommand().doCommand(user);
                break;
            }

            default: {
                responceMessage = "Неправильный формат ввода, используйте /help для получения информации";
            }
        }

        return responceMessage;
    }
}
