public class InfoCommand {
    public String doCommand(User user) {
        return "User №" + user.id + " info\n"
                + "Имя - " + user.name + '\n'
                + "Рекорд - " + user.highScore + "\n"
                + "Текущий вопрос - " + user.curQuestion;
    }
}
