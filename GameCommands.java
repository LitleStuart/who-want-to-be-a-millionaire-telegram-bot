public class GameCommands {

    public String respond(User user)
    {
        String[] message = (user.lastMessage.text.split(" "));
        String responceMessage = new String();
        if (message[0] .contentEquals ( "A" ) || message[0].contentEquals ( "B" )
                || message[0].contentEquals ( "C" ) || message[0].contentEquals ( "D" )) //refactor later
        {
            Question curQuestion=user.game.showQuestions ().get ( user.curQuestion );
            //game check answer
            //return bool
            if (curQuestion.getAnswers ().get ( (message[0].charAt ( 0 ))-'A' ).isCorrect) {
                user.curQuestion++;
                responceMessage = "Correct, next question \n";
                responceMessage += user.game.showQuestions ().get ( user.curQuestion ).getQuestion ()+'\n';
                for (int i=0;i<4;i++)
                {
                    responceMessage+=(char)('A'+i)+"-"+user.game.showQuestions ().get ( user.curQuestion ).getAnswers ().get ( i ).answer+'\n';
                }
            }
            else
            {
                user.highScore=Math.max(user.highScore,user.curQuestion);
                user.curQuestion=0;
                user.isInGame=false;
                responceMessage = "You lost, to retry type /start";
            }
            return responceMessage;
        }
        if (message[0].contentEquals ( "hint" ))
        {
            Question curQuestion=user.game.showQuestions ().get ( user.curQuestion );
            if (user.hints>0)
            {
                if (curQuestion.hintIsUsed ())
                {
                    responceMessage="You have already used hint here";
                    return responceMessage;
                }
                int wrongCounter=0;
                responceMessage="Wrong answers are:\n";
                for (int i=0;i<4;i++)
                {
                    if (!curQuestion.getAnswers ().get(i).isCorrect)
                    {
                        wrongCounter++;
                        responceMessage+=curQuestion.getAnswers ().get ( i ).answer+'\n';
                    }
                    if (wrongCounter==2) {
                        break;
                    }
                }
                user.hints--;
                curQuestion.useHint ();
                return  responceMessage;
            }
            else
            {
                responceMessage="You have no hints left";
            }

        }
        responceMessage = "Wrong format of message, type /help for more info";
        user.isInGame=false;
        user.curQuestion=0;
        return responceMessage;
    }
}
