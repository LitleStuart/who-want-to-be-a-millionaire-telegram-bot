public class StartCommand {
    public String execute(User user) {
        String responceMessage;
        user.startGame();
        responceMessage = user.game.questions.get(user.curQuestion).getQuestion() + '\n';
        for (int i = 0; i < 4; i++) {
            responceMessage += (char) ('A' + i) + "-"
                    + user.game.questions.get(user.curQuestion).getAnswers().get(i).answer + '\n';
        }
        return responceMessage;
    }
}
