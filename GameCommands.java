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
                responceMessage = "Правильно, следующий вопрос \n";
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
                responceMessage = "Вы проиграли, чтобы начать заново /start";
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
                    responceMessage="Вы уже использовали подсказку на этом вопросе";
                    return responceMessage;
                }
                int wrongCounter=0;
                responceMessage="Неправильные ответы:\n";
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
                responceMessage="У вас не осталось подсказок";
                return responceMessage;
            }

        }
        responceMessage = "Неправильный формат ввода, используйте /help для получения информации";
        if (new ChatCommands().respond( user )==responceMessage)
        {
            user.isInGame=false;
            user.curQuestion=0;
            return responceMessage;
        }
        else
        {
            return  new ChatCommands().respond( user );
        }

    }
}
