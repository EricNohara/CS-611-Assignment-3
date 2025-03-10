/*
    Description:
    The ConsecutivePiecesGame class is an abstract subclass of BoardGame, representing board games where players must get a 
    certain number of pieces in a row to win. It manages win conditions and dynamically calculates possible winning positions.

    Instance Variables:
    - DEFAULT_LENGTH: Class level constant default number of consecutive pieces required to win.
    - winLength: The current win length (modifiable by user input).
    - winPositions: A list of all possible winning positions on the board.

    Constructors:
    - public ConsecutivePiecesGame(String name): Initializes a game with a default board size and win conditions.
    - public ConsecutivePiecesGame(int rows, int cols, String name): Initializes a game with a custom board size.
    - public ConsecutivePiecesGame(int rows, int cols, String name, String gameID): Initializes a game with a custom board 
      size and an optional game ID (used in Super).

    Important Methods:
    - getWinPositions(): Returns all possible winning positions.
    - getWinLengthFromUserInput(): Prompts the user to specify a win length, ensuring valid input.
    - setWinLengthFromUserInput(): Updates the win length based on user input.
    - generateWinPositions(): Generates and stores all possible winning positions (horizontal, vertical, and diagonal).
*/

import java.util.List;
import java.util.ArrayList;

public abstract class ConsecutivePiecesGame extends BoardGame {
    private static final int DEFAULT_LENGTH = 3;
    private int winLength;
    private List<List<int[]>> winPositions;

    public ConsecutivePiecesGame(String name) {
        super(name);
        this.winLength = DEFAULT_LENGTH;
        this.winPositions = generateWinPositions();
    }

    public ConsecutivePiecesGame(int rows, int cols, String name) {
        super(rows, cols, name);
        this.winLength = DEFAULT_LENGTH;
        this.winPositions = generateWinPositions();
    }

    public ConsecutivePiecesGame(int rows, int cols, String name, String gameID) {
        super(rows, cols, name, gameID);
        this.winLength = DEFAULT_LENGTH;
        this.winPositions = generateWinPositions();
    }

    // GETTER methods
    public int getWinLength() {
        return this.winLength;
    }

    public List<List<int[]>> getWinPositions() {
        return this.winPositions;
    }

    public int getWinLengthFromUserInput() {
        String inputString;
        int input;

        System.out.print("Enter desired win length (pieces in a row to win) or leave blank to play default:\t");

        Board board = this.getBoard();
        int rows = board.getRows(), columns = board.getColumns();

        while (true) {
            try {
                inputString = this.getScanner().nextLine().trim();
                if (inputString.length() == 0) return Math.min(rows, columns);
                input = Integer.parseInt(inputString);

                if (input < 2 || input > Math.max(rows, columns)) {
                    System.out.println("Invalid input. winLength \u2208 [2, max{" + rows + ", " + columns + "}]");
                    throw new IllegalArgumentException();
                }

                return input;
            } catch (Exception e) {
                System.out.print("Please enter your desired grid size (rows, columns) or leave blank to play default game:\t");
            }
        }
    }

    // SETTER methods
    public void setWinLength(int length) {
        this.winLength = length;
        this.winPositions = this.generateWinPositions();
    }
    
    public void setWinLengthFromUserInput() {
        this.winLength = this.getWinLengthFromUserInput();
        this.winPositions = this.generateWinPositions();
    }

    // METHOD TO GENERATE ALL WINNING POSITIONS OF winLength pieces in a row
    private List<List<int[]>> generateWinPositions() {
        List<List<int[]>> positions = new ArrayList<>();
        int numRows = this.getBoard().getRows();
        int numCols = this.getBoard().getColumns();

        // Horizontal wins
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c <= numCols - this.winLength; c++) {
                List<int[]> position = new ArrayList<>();
                for (int i = 0; i < this.winLength; i++) position.add(new int[] {r, c + i}); // add the coordinates
                positions.add(position);
            }
        }

        // Vertical wins
        for (int c = 0; c < numCols; c++) {
            for (int r = 0; r <= numRows - this.winLength; r++) {
                List<int[]> position = new ArrayList<>();
                for (int i = 0; i < this.winLength; i++) position.add(new int[] {r + i, c}); // add the coordinates
                positions.add(position);
            }
        }

        // Top left to bottom right diagonal
        for (int r = 0; r <= numRows - this.winLength; r++) {
            for (int c = 0; c <= numCols - this.winLength; c++) {
                List<int[]> position = new ArrayList<>();
                for (int i = 0; i < this.winLength; i++) position.add(new int[]{r + i, c + i});
                positions.add(position);
            }
        }

        // Top right to bottom left diagonal
        for (int r = 0; r <= numRows - this.winLength; r++) {
            for (int c = this.winLength - 1; c < numCols; c++) {
                List<int[]> position = new ArrayList<>();
                for (int i = 0; i < this.winLength; i++) {
                    position.add(new int[]{r + i, c - i});
                }
                positions.add(position);
            }
        }

        return positions;
    }
}