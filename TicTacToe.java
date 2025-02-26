/*
    Description:
    Class for the Tic Tac Toe game which implements the abstract methods of ConsecutivePiecesGame.

    Class Level Constants:
    - TEAM_0_SYMBOL: default team symbol
    - TEAM_1_SYMBOL: default team symbol
    - GAME_NAME: default game name
    - MAX_ROWS: default max input for this field
    - MAX_COLUMNS: default max input for this field
    - DEFAULT_ROWS: default number of rows on the board
    - DEFAULT_COLS:  default number of cols on the board

    Constructors:
    - public TicTacToe(): creates a generic TicTacToe game based off of user inputted fields
    - public TicTacToe(String gameID): creates a simple TicTacToe game with a given game id without user input (used in SuperTicTacToe)

    Abstract Method Implementations:
    - playGame(): runs the game loop, allowing players to take turns until a winner is found or the game ends in a tie.
    - isWinner(): iterates over all winPositions and checks if the current team has winLength pieces in a row.
    - makeNextMove(): Processes the current player's move by selecting a cell and placing their game piece.

    Important Methods:
    - setBoardSizeFromUserInput(): Prompts the user for board size and initializes the board accordingly.
    - makeMove(Cell input, GamePiece piece, Team team, int turnNumber, Player player): makes a generic move based off of inputted values (used in Super)
    - isWinner(Team team): checks if the inputted team is the winner of the game (used in Super)
*/

import java.util.List;

public class TicTacToe extends ConsecutivePiecesGame {
    // CLASS LEVEL CONSTANTS
    public static final char TEAM_0_SYMBOL = 'X';
    public static final char TEAM_1_SYMBOL = 'O';
    public static final String GAME_NAME = "Tic Tac Toe";
    private static final int MAX_ROWS = 40;
    private static final int MAX_COLUMNS = 40;
    private static final int DEFAULT_ROWS = 3;
    private static final int DEFAULT_COLS = 3;

    // CONSTRUCTORS
    public TicTacToe() {
        super(DEFAULT_ROWS, DEFAULT_COLS, GAME_NAME);
        this.getBoard().setGameType(GAME_NAME);
        this.setTeamsFromUserInput("" + TEAM_0_SYMBOL, "" + "" + TEAM_1_SYMBOL);
        this.setBoardSizeFromUserInput();
        this.setWinLengthFromUserInput(); 
    }

    // simple TicTacToe constructor used in SuperTicTacToe
    public TicTacToe(String gameID) {
        super(DEFAULT_ROWS, DEFAULT_COLS, GAME_NAME, gameID);
        this.getBoard().setGameType(GAME_NAME);
    }

    // USER INPUT METHOD
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

    // SUPER TICTACTOE UTILITY METHODS
    // used to make a generic move given all information about the move
    public void makeMove(Cell input, GamePiece piece, Team team, int turnNumber, Player player) {
        input.setAllFields(piece, team, turnNumber, player);
        this.incrementTurnNumber();
        if (this.isWinner(team)) {
            System.out.println(team.getName() + " won board " + this.getGameID());
            this.setWinner(team);
        }
    }

    // used to determine if a given team is the winner
    public boolean isWinner(Team team) {
        if (this.getWinner() != null) return false; // if there is already a winner, then return false immediately
        
        char symbol = team.getNumber() == 0 ? TEAM_0_SYMBOL : TEAM_1_SYMBOL;
        Board board = this.getBoard();

        for (List<int[]> position : this.getWinPositions()) {
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

    // ABSTRACT METHOD IMPLEMENTATIONS
    public void makeNextMove() {
        Team currentTeam = this.getCurrentTeam(); // the team whose turn it is currently
        Player currentPlayer = currentTeam.getRandomPlayer(); // get a random player from the current team

        Cell inputCell = this.getNextPlayerInputCell(currentTeam, currentPlayer);
        GamePiece playerPiece = currentTeam.getNumber() == 0 ? new GamePiece(TEAM_0_SYMBOL) : new GamePiece(TEAM_1_SYMBOL);

        inputCell.setAllFields(playerPiece, currentTeam, this.getTurnNumber(), currentPlayer);
    }

    public boolean isWinner() {
        return isWinner(this.getCurrentTeam());
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