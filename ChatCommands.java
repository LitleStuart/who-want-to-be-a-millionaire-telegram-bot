public class ChatCommands {
    public String respond(User user) {
        String[] message = (user.lastMessage.text.split(" "));
        String responceMessage = new String();
        switch (message[0]) {
            case ("/help"): {
                responceMessage = ("Millionaire bot\n"
                        + "List of commands\n"
                        + "/help | to get information\n"
                        + "/start | to start the game\n"
                        + "/nickname [your name] | to set your name\n"
                        + "/info | to get your statistics");
                break;
            }
            case ("/start"): {
                if (user.name.isEmpty ()) {
                    responceMessage = "Please, enter your name first";
                    return responceMessage;
                }

                user.startGame ();
                responceMessage = user.game.showQuestions ().get ( user.curQuestion ).getQuestion ()+'\n';
                for (int i=0;i<4;i++)
                {
                    responceMessage+=(char)('A'+i)+"-"+user.game.showQuestions ().get ( user.curQuestion ).getAnswers ().get ( i ).answer+'\n';
                }
                break;
            }
            case ("/nickname"): {
                if (message.length == 2) {
                    if (user.name.isEmpty()) {
                        responceMessage = "Your name set to " + message[1];
                    } else {
                        responceMessage = "Your name is changed from " + user.name + " to " + message[1];
                    }
                    user.name = message[1];
                } else {
                    responceMessage = "Wrong format of message, type /help for more info";
                }
                break;
            }
            case ("/info"): {
                responceMessage = ("User№" + user.id + " info\n"
                        + "Name - " + user.name + '\n'
                        + "Highscore - " + user.highScore
                        // current state of game???
                );
                break;
            }

            default: {
                responceMessage = "Wrong format of message, type /help for more info";
            }
        }

        return responceMessage;
    }
}
