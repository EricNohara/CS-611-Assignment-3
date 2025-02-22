/*
    Description:  
    BoardGame abstract class extends Game and implements TurnBased, representing a turn-based board game.
    It manages game mechanics such as board state, turns, user input, and game history storage 
    common to all board games.

    Fields:  
    - FILENAME: Class level constant filename for exporting game history.  
    - scanner: Constant scanner object for user input.  
    - board: The game board.  
    - turnNumber: Tracks the current turn number.  
    - gameNumber: Tracks the game number across multiple sessions.  

    Constructors:  
    - BoardGame(String name): Initializes a board game with default board size.  
    - BoardGame(int rows, int columns, String name): Initializes a board game with a custom board size.  

    Abstract Method Implementations:
    - exitGame(): Saves game history to a file before exiting.  
    - isValidMove(int row, int column): Checks if a move is valid.      
    - getTurnNumber(): Gets the current turn number.
    - getCurrentTeam(): Gets the current team based off of the current turn.
    - incrementTurnNumber: Increments the current turn number.
    - resetTurnNumber: Resets the turn number.

    Important Methods:  
    - getIsBoardFull(): used to check if the board is full of pieces
    - reset(String gameType): Saves game history, resets the board, and prepares for a new game.  
    - getTeamFromUserInput(int number, String name): Gets team input from the user.  
    - isUserDone(): Checks if the user wants to continue playing.  
    - getNextPlayerInputCell(Team team, Player player): Gets the player's move input.  
*/


import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;  
import java.io.FileWriter;
import java.io.IOException; 

public abstract class BoardGame extends Game implements TurnBased {
    // constant filename to export game history data
    private static final String FILENAME = "data.txt";

    private final Scanner scanner;
    private Board board;
    private int turnNumber;
    private int gameNumber;

    // CONSTRUCTORS
    public BoardGame(String name) {
        super(name);
        this.board = new Board();
        this.turnNumber = 0;
        this.scanner = new Scanner(System.in);
        this.gameNumber = 1;
    }

    public BoardGame(int rows, int columns, String name) {
        super(name);
        this.board = new Board(rows, columns);
        this.turnNumber = 0;
        this.scanner = new Scanner(System.in);
        this.gameNumber = 1;
    }

    // GETTER METHODS
    public Board getBoard() {
        return this.board;
    }

    public int getTurnNumber() {
        return this.turnNumber;
    }

    public Team getCurrentTeam() {
        Team[] teams = this.getTeams();
        return teams[this.turnNumber % teams.length];
    }

    public Scanner getScanner() {
        return this.scanner;
    }

    public boolean getIsBoardFull() {
        // check if there are any empty cells left on the board
        for (Cell[] row : this.board.getBoard()) {
            for (Cell cell : row) {
                if (cell.getValue() == null) return false;
            }
        }
        return true;
    }

    public int getGameNumber() {
        return this.gameNumber;
    }

    // SETTER METHODS
    public void incrementTurnNumber() {
        this.turnNumber += 1;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void resetTurnNumber() {
        this.turnNumber = 0;
    }

    public void incrementGameNumber() {
        this.gameNumber++;
    }

    public void reset(String gameType) {
        this.addGameHistory(new BoardGameHistory(this.getWinner(), board, this.turnNumber + 1, this.gameNumber));
        this.board = new Board(board.getRows(), board.getColumns(), gameType);
        this.resetTurnNumber();
        this.incrementGameNumber();
        this.setWinner(null);
    }

    // COMMON METHODS FOR ALL GAME SUBCLASSES
    // USER INPUT METHODS
    public Team getTeamFromUserInput(int number, String name) {
        System.out.print("Enter comma seperated list of players on team " + name + " (leave blank for default player):\t");
        String input;

        while (true) {
            try {
                input = this.scanner.nextLine().trim();
                if (input.length() == 0) return new Team(new Player(), number, name); 
                
                String[] names = input.split(",");
                for (int i = 0; i < names.length; i++) names[i] = names[i].trim();
                
                Player[] players = new Player[names.length];
                for (int i = 0; i < names.length; i++) players[i] = new Player(names[i]);
                
                return new Team(players, number, name);
            } catch (Exception e) {
                System.out.print("Enter comma seperated list of players on team " + name + " (leave blank for default player):\t");
            }
        }
    }

    public boolean isUserDone() {
        System.out.print("Would you like to play again (y/n):\t");

        String resp;
        while (true) {
            resp = this.scanner.next();

            if (resp.equalsIgnoreCase("y")) {
                return false;
            } else if (resp.equalsIgnoreCase("n")) {
                // display the win counts for all players on all teams before quitting
                for (Team team : this.getTeams()) {
                    team.displayPlayerWinCounts();
                }
                return true;
            } else {
                System.out.print("Invalid input. Please enter y/n:\t");
            }
        }
    }

    public Cell getNextPlayerInputCell(Team team, Player player) {
        System.out.print("[TEAM " + team.getName() + "] " + player.getName() + " enter your move (row,col):\t");
        int row, column;
        String input;

        while (true) {
            try {
                input = this.scanner.next();
                String[] parts = input.split(",");

                if (parts.length != 2) throw new IllegalArgumentException();

                row = Integer.parseInt(parts[0].trim());
                column = Integer.parseInt(parts[1].trim());

                if (!this.isValidMove(row, column)) throw new IllegalArgumentException();
                break;
            } catch (Exception e) {
                System.out.print("Invalid move. Please enter a valid move (row,col):\t");
            }   
        }

        return this.board.getCell(row, column);
    }

    // VALIDATION METHOD TO CHECK IF THE MOVE IS VALID
    public boolean isValidMove(int row, int column) {
        return row >= 0 && row < this.board.getRows() && 
                column >= 0 && column < this.board.getColumns() &&
                this.board.isBoardCellEmpty(row, column);
    }

    // EXIT ROUTINE FOR BOARD GAMES: save the list of game histories to a seperate file
    public void exitGame() {
        String input;
        System.out.print("Would you like to save your game histories to " + FILENAME + " (y/n):\t");

        while (true) {
            input = this.scanner.next();
            if (input.equalsIgnoreCase("n")) return; // immediately return if user does not want data.txt file
            else if (input.equalsIgnoreCase("y")) break;
            else System.out.print("Invalid input. Enter y or n:\t");           
        }

        try {
            File file = new File(FILENAME);
            file.createNewFile();
            FileWriter writer = new FileWriter(file, false); // overwrite enabled
            
            String data = "";
            for (GameHistory history : this.getGameHistory()) data += history.toString() + "\n";
            
            writer.write(data);
            writer.close();

            System.out.println("Saved game history data in " + FILENAME);
        } catch (IOException e) {
            System.out.println("Error writing game history data to " + FILENAME);
        }
    }

    public void setBoardSizeFromUserInput() {};

    public void setWinLengthFromUserInput() {};
}