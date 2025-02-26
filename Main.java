/*
    Description:
    Main class that serves as the entry point for running the board game.

    Important Methods:
    - main(String[] args): Initializes the game using GameInitializer, starts the game loop, and exits the game after completion.
*/

public class Main {
    public static void main(String[] args) {
        GameInitializer initializer = new GameInitializer();
        BoardGame game = initializer.initializeBoardGame();
        game.playGame();
        game.exitGame();
    }
}