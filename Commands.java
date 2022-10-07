public class Commands {

    public String messageReact(User user) {
        String[] message = { "123" };
        String outputMessage = new String();
        switch (message[0]) {
            case ("/help"): {
                outputMessage = ("Millionaire bot\n"
                        + "/help – get information\n"
                        + "/start – start the game\n"
                        + "/info – get your info");
                break;
            }
            case ("/start"): {
                if (user.name == "") {
                    outputMessage = "Please, enter your name first";
                }
                outputMessage = "Starting game";
                // add game support
                break;
            }
            case ("/nickname"): {
                if (message.length == 2) {
                    if (user.name.isEmpty()) {
                        outputMessage = "Your name set to " + message[1];
                    } else {
                        outputMessage = "Your name is changed from " + user.name + " to " + message[1];
                    }
                    user.name = message[1];
                } else {
                    outputMessage = "Wrong format of message, type /help for more info";
                }
                break;
            }
            case ("/info"): {
                outputMessage = ("User№" + user.id + " info\n"
                        + "Name - " + user.name + '\n'
                        + "Highscore - " + user.highScore
                // current state of game???
                );
                break;
            }

            default: {
                outputMessage = "Wrong format of message, type /help for more info";
            }
        }

        return outputMessage;
    }
}
