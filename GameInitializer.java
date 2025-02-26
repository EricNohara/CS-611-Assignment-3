/*
    Description:
    The GameInitializer class is responsible for initializing the selected board game based on user input.
    To be used in Main.

    Instance Variables:
    - scanner: A Scanner object used for reading user input.

    Constructors:
    - public GameInitializer(): Initializes the scanner for reading input from the console.

    Important Methods:
    - initializeBoardGame(): Prompts the user to select a game type (Tic Tac Toe, Order and Chaos, or Super Tic Tac Toe).
    - Ensures valid input ('T', 'O', or 'S') before proceeding.
    - Returns an instance of the selected BoardGame implementation.
*/


import java.util.Scanner;

public class GameInitializer {
    private Scanner scanner;

    public GameInitializer() {
        this.scanner = new Scanner(System.in);
    }

    public BoardGame initializeBoardGame() {
        System.out.print("Please enter game to play (T/O/S): \t");

        String input;
        char inputChar;

        while(true) {
            try {
                input = scanner.next();
                inputChar = Character.toUpperCase(input.charAt(0));
                if (inputChar != 'O' && inputChar != 'T' && inputChar != 'S') throw new IllegalArgumentException();
                break;
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter game to play (T/O): \t");
            }
        }

        if (inputChar == 'T') return new TicTacToe();
        
        else if (inputChar == 'O') return new OrderAndChaos();
        
        return new SuperTicTacToe();
    }
}