/* 
    Description:
    This class is the entry point, allowing user to choose between two board games: 
    Tic-Tac-Toe (T) and Order and Chaos (O). It prompts the user for input, validates
    the selection, then initializes and starts the game.

    Features:
    - Accepts user input to determine which game to play.
    - Validates user input.
    - Calls the playGame() method to start the selected game.
    - Calls the exitGame() after game is complete.

    Usage:
    Run the program and enter 'T' for Tic-Tac-Toe or 'O' for Order and Chaos.
    The corresponding game will then be played.
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to CS 611 Assignment 2.");
        System.out.print("Please enter game to play (T/O): \t");

        String input;
        char inputChar;

        while(true) {
            try {
                input = scanner.next();
                inputChar = Character.toUpperCase(input.charAt(0));
                if (inputChar != 'O' && inputChar != 'T') throw new IllegalArgumentException();
                break;
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter game to play (T/O): \t");
            }
        }

        BoardGame game;

        if (inputChar == 'T') {
            game = new TicTacToe();
        } else {
            game = new OrderAndChaos();
        }

        game.playGame();
        game.exitGame();
    }
}