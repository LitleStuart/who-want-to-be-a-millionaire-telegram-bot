public class MainMenuScene implements IScene {
    private IBotApi botApi;
    private SceneFactory sceneFactory;

    MainMenuScene(IBotApi botApi, SceneFactory sceneFactory) {
        this.botApi = botApi;
        this.sceneFactory = sceneFactory;
    }

    @Override
    public void handleMessage(User user, Message message) {
        switch (message.text) {
            case ("/help"): {
                executeHelpCommand(user);
                return;
            }
            case ("/start"): {
                executeStartGameCommand(user);
                return;
            }
            case ("/info"): {
                executeInfoCommand(user);
                return;
            }

            default: {
                botApi.sendAnswer(user.id, "Неправильный формат ввода, используйте /help для получения информации");
            }
        }
    }

    private void executeHelpCommand(User user) {
        String responseMessage = "/start – Новая игра\n" +
                "/info – Ваша статистика\n" +
                "/hint – Использовать подсказку\n" +
                "/exit – Выход из игры\n" +
                "/help – Показать это сообщение";
        botApi.sendAnswer(user.id, responseMessage);
    }

    private void executeStartGameCommand(User user) {
        String responseMessage;
        user.startGame();
        responseMessage = user.game.questions.get(user.curQuestion).getQuestion() + '\n';
        for (int i = 0; i < 4; i++) {
            responseMessage += (char) ('A' + i) + ": "
                    + user.game.questions.get(user.curQuestion).getAnswers().get(i).answer + '\n';
        }
        botApi.sendAnswer(user.id, responseMessage);
        user.scene = sceneFactory.createGameScene();
    }

    private void executeInfoCommand(User user) {
        String responseMessage = "Ваша статистика:\n"
                + "Имя – " + user.name + '\n'
                + "Рекорд – " + user.highScore + "\n"
                + "Текущий вопрос – " + user.curQuestion;
        botApi.sendAnswer(user.id, responseMessage);
    }
}
