public class HelpCommand {
    public String doCommand() {
        return ("Millionaire bot\n"
                + "Список команд\n"
                + "/help | для получения информации\n"
                + "/start | для начала игры\n"
                + "/nickname [your name] | для создания/смены имени\n"
                + "/info | для статистики пользователя\n\n"
                +"Во время игры\n"
                +"A-D | для выбора ответа\n"
                +"hint | для подсказки\n"
                +"/start | для новой игры\n");
    }
}
