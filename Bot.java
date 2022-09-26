public class Bot {
    private IBotApi botApi;

    Bot(IBotApi botApi) {
        this.botApi = botApi;
    }

    public void start() {
        this.botApi.registerOnUpdate((update) -> {
            updateUserMessageHistory(update.chatId, update.message);
            User user = getUser(update.chatId);
            String answer = generateAnswer(user);
            this.botApi.sendAnswer(update.chatId, answer);
        });
    }

    private String generateAnswer(User user) {
        return null;
    }

    private User getUser(long chatId) {
        return null;
    }

    private void updateUserMessageHistory(long chatId, Message message) {
    }
}
