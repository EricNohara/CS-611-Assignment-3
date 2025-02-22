/*
    Description:
    Class describing the Tic Tac Toe game which implements the abstract methods of BoardGame.

    Fields:
    - TEAM_0_SYMBOL: class level constant for any Tic Tac Toe game.
    - TEAM_1_SYMBOL: class level constant for any Tic Tac Toe game.
    - GAME_NAME: class level constant for any Tic Tac Toe game.
    - MAX_ROWS: class level constant for any Tic Tac Toe game.
    - MAX_COLUMNS: class level constant for any Tic Tac Toe game.
    - winLength: constant that defines the number of pieces in a row needed for a user to win.
    - winPositions: constant calculated at construction time which contains all possible winning
                    positions given the board size and winLength.

    Abstract Method Implementations:
    - playGame(): runs the game loop, allowing players to take turns until a winner is found or the game ends in a tie.
    - isWinner(): iterates over all winPositions and checks if the current team has winLength pieces in a row.
    - makeNextMove(): Processes the current player's move by selecting a cell and placing their game piece.

    Important Methods:
    - setBoardSizeFromUserInput(): Prompts the user for board size and initializes the board accordingly.
    - getWinLengthFromUserInput(): Prompts the user for the win condition (pieces in a row to win).
    - generateWinPositions(): Generates all possible winning positions on the board (called in constructor).
*/

import java.util.ArrayList;
import java.util.List;

public class TicTacToe extends BoardGame {
    // CLASS LEVEL CONSTANTS
    private static final char TEAM_0_SYMBOL = 'X';
    private static final char TEAM_1_SYMBOL = 'O';
    private static final String GAME_NAME = "Tic Tac Toe";
    private static final int MAX_ROWS = 40;
    private static final int MAX_COLUMNS = 40;

    // OBJECT LEVEL CONSTANTS: since TicTacToe games can have different board sizes and win lengths
    private final int winLength;
    private final List<List<int[]>> winPositions;

    // CONSTRUCTORS
    public TicTacToe() {
        super(GAME_NAME);

         // set the teams 
        Team[] teams = new Team[2]; // 2 teams
        teams[0] = this.getTeamFromUserInput(0, "" + TEAM_0_SYMBOL);
        teams[1] = this.getTeamFromUserInput(1, "" + TEAM_1_SYMBOL);
        this.setTeams(teams);

        // set the board size and type
        setBoardSizeFromUserInput();
        this.getBoard().setGameType(GAME_NAME);

        this.winLength = getWinLengthFromUserInput();
        this.winPositions = generateWinPositions();
    }

    // GETTER METHODS
    public List<List<int[]>> getWinPositions() {
        return this.winPositions;
    }

    public int getWinLength() {
        return this.winLength;
    }

    // GENERATING ALL WIN CONDITIONS 
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

    // SET BOARD SIZE + WIN LENGTH FROM USER INPUT
    public void setBoardSizeFromUserInput() {
        String input;
        int rows, columns;

        System.out.print("Enter desired board size (rows, columns) or leave blank to play default game:\t");

        while (true) {
            try {
                input = this.getScanner().nextLine().trim();
                if (input.length() == 0) {
                    this.setBoard(new Board(3, 3, GAME_NAME));
                    break;
                }

                String[] parts = input.split(",");
                if (parts.length != 2) throw new IllegalArgumentException();

                rows = Integer.parseInt(parts[0].trim());
                columns = Integer.parseInt(parts[1].trim());

                if (rows < 1 || columns < 1 || rows > MAX_ROWS || columns > MAX_COLUMNS) {
                    System.out.println("Invalid input. Rows \u2208 [1, " + MAX_ROWS + "], columns \u2208 [1, " + MAX_COLUMNS + "]");
                    throw new IllegalArgumentException();
                }

                this.setBoard(new Board(rows, columns, GAME_NAME));
                break;
            } catch (Exception e) {
                System.out.print("Enter desired board size (rows, columns) or leave blank to play default game:\t");
            }
        }
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

    // ABSTRACT METHOD IMPLEMENTATIONS
    public void makeNextMove() {
        Team currentTeam = this.getCurrentTeam(); // the team whose turn it is currently
        Player currentPlayer = currentTeam.getRandomPlayer(); // get a random player from the current team

        Cell inputCell = this.getNextPlayerInputCell(currentTeam, currentPlayer);
        GamePiece playerPiece = currentTeam.getNumber() == 0 ? new GamePiece(TEAM_0_SYMBOL) : new GamePiece(TEAM_1_SYMBOL);

        inputCell.setAllFields(playerPiece, currentTeam, this.getTurnNumber(), currentPlayer);
    }

    public boolean isWinner() {
        char symbol = this.getCurrentTeam().getNumber() == 0 ? TEAM_0_SYMBOL : TEAM_1_SYMBOL;
        Board board = this.getBoard();

        for (List<int[]> position : this.winPositions) {
            for (int i = 0; i < position.size(); i++) {
                int[] coordinate = position.get(i);
                int x = coordinate[0], y = coordinate[1];
                GamePiece piece = board.getCell(x, y).getValue();
                if (piece == null || piece.getSymbol() != symbol) break;
                if (i == position.size() - 1) return true;
            }
        }

        return false; // if got to here, no direction yeilds a win condition
    }

    public void playGame() {
        Board board = this.getBoard();
        board.displayBoard();

        while (true) {
            this.makeNextMove();
            board.displayBoard();

            boolean playerWon = this.isWinner();
            Team currentTeam = this.getCurrentTeam();

            if (playerWon) {
                this.setWinner(currentTeam);
                System.out.println("Congratulations team " + currentTeam.getName() + "! You won the game!");

                currentTeam.incrementPlayerwinCounts();
                this.reset(GAME_NAME);
                board = this.getBoard();

                if (this.isUserDone()) break;
                else board.displayBoard();
            } else if (this.getIsBoardFull()) {
                System.out.println("The game has ended in a tie.");

                this.reset(GAME_NAME);
                board = this.getBoard();

                if (this.isUserDone()) break;
                else board.displayBoard();
            } else {
                this.incrementTurnNumber();
            }
        }
    }
}