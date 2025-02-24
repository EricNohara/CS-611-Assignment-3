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

        BoardGame game;
        Team[] teams = new Team[2]; // 2 teams

        if (inputChar == 'T') {
            game = new TicTacToe();
            teams[0] = game.getTeamFromUserInput(0, "" + TicTacToe.TEAM_0_SYMBOL);
            teams[1] = game.getTeamFromUserInput(1, "" + TicTacToe.TEAM_1_SYMBOL);
            game.setTeams(teams);
            game.setBoardSizeFromUserInput(); // only does stuff for tic tac toe games
            game.setWinLengthFromUserInput(); // only for tic tac toe games
        } else if (inputChar == 'O') {
            game = new OrderAndChaos();
            teams[0] = game.getTeamFromUserInput(0, "ORDER");
            teams[1] = game.getTeamFromUserInput(1, "CHAOS");
            game.setTeams(teams);
        } else {
            game = new SuperTicTacToe();
            teams[0] = game.getTeamFromUserInput(0, "" + TicTacToe.TEAM_0_SYMBOL);
            teams[1] = game.getTeamFromUserInput(1, "" + TicTacToe.TEAM_1_SYMBOL);
            game.setTeams(teams);
        }

        game.playGame();
        game.exitGame();
        scanner.close();
    }
}