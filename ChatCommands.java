public class ChatCommands {
    public String respond(User user) {
        String[] message = (user.lastMessage.text.split(" "));
        String responceMessage = new String();
        switch (message[0]) {
            case ("/help"): {
                responceMessage = new HelpCommand ().doCommand ();
                break;
            }
            case ("/start"): {
                responceMessage = new StartCommand().doCommand(user);
                break;
            }
            case ("/nickname"): {
                responceMessage=new NickCommand().doCommand( user,message );
                break;
            }
            case ("/info"): {
                responceMessage = new InfoCommand().doCommand( user );
                break;
            }

            default: {
                responceMessage = "Неправильный формат ввода, используйте /help для получения информации";
            }
        }

        return responceMessage;
    }
}
