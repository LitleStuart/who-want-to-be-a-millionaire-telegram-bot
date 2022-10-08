public class StartCommand {
    public String doCommand(User user) {
        String responceMessage;
        if (user.name.isEmpty ()) {
            responceMessage = "Сначала введите имя";
            return responceMessage;
        }

        user.startGame ();
        responceMessage = user.game.showQuestions ().get ( user.curQuestion ).getQuestion ()+'\n';
        for (int i=0;i<4;i++)
        {
            responceMessage+=(char)('A'+i)+"-"+user.game.showQuestions ().get ( user.curQuestion ).getAnswers ().get ( i ).answer+'\n';
        }
        return responceMessage;
    }
}
