# Who want to be a millionaire – telegram-bot

```mermaid
classDiagram
class Scene {
    +handleMessage(...) void
}
class SceneFactory {
    +createMainMenuScene() Scene
    +createGameScene() Scene
    +createHintScene() Scene
}
class GameScene {
    +executeHintCommand(...) void
    +executeGameEndCommand(...) void
    +handleAnswerCommand(...) void
    +handleUnknownCommand(...) void
}
class MainMenuScene {
    +executeStartCommand(...) void
    +executeHelpCommand(...) void
    +executeInfoCommand(...) void
}
class HintScene {
    +execute50on50Hint(...) void
    +executeAllowMistakeHint(...) void
    +handleUnknownCommand(...) void
}
class User {
    +scene Scene
}
class Bot {
    +handleMessage(userId, botMessage) void
}


Scene <.. GameScene
Scene <.. HintScene
Scene <.. MainMenuScene
GameScene <--> SceneFactory
HintScene <--> SceneFactory
MainMenuScene <--> SceneFactory
Scene <-- User
SceneFactory <-- Bot
User <-- Bot
```

## Run

1. Clone to your machine this repository
2. Open terminal inside this repository
3. run commands replacing "path_to_repo" with path to this repo on your computer:

```
javac -d bin -cp "path_to_repo/lib/telegrambots-6.1.0-jar-with-dependencies.jar:path_to_repo/lib/gson-2.9.1.jar" Bot.java GameScene.java HintScene.java IBotApi.java IScene.java MainMenuScene.java Question.java SceneFactory.java TgBotApi.java TgBotProgram.java Update.java User.java TgButtons.java AssistScene.java BotMessage.java Buttons.java CallScene.java IQuestionProvider.java JsonQuestionProvider.java
cd bin
java -cp ".:path_to_repo/lib/telegrambots-6.1.0-jar-with-dependencies.jar:path_to_repo/lib/gson-2.9.1.jar" TgBotProgram
```

## Usage

Type `/help` to see help botMessage
