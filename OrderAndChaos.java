/*
    Description:
    Class representing the Order and Chaos board game, extending the ConsecutivePiecesGame class.
    This game is played on a 6x6 board with two teams: ORDER and CHAOS.
    ORDER wins by aligning five identical pieces in a row, while CHAOS wins if the board fills without such a sequence.

    Class Level Constants:  
    - DEFAULT_WIN_LENGTH: default win length
    - GAME_NAME: Class level constant of a string representing the game name.  
    - TEAM_0_NAME: name of order team
    - TEAM_1_NAME: name of chaos team
    - ROWS: default number of rows in board
    - COLS: default number of cols in board

    Constructors:  
    - OrderAndChaos(): Initializes the board, generates winning positions from win length, assigns teams, and sets the game type.  

    Abstract Method Implementations:  
    - playGame(): Controls the game loop, alternating turns between ORDER and CHAOS, checking for a winner, and handling game resets.  
    - makeNextMove(): Handles player input for placing a piece (X or O) and updates the board.  
    - isWinner(): Checks if any winning condition has been met for the ORDER team only.
*/

import java.util.List;

public class OrderAndChaos extends ConsecutivePiecesGame {
    // CLASS LEVEL CONSTANTS
    private static final int DEFAULT_WIN_LENGTH = 5; // need 5 in a row to win
    private static final String GAME_NAME = "Order and Chaos";
    public static final String TEAM_0_NAME = "ORDER";
    public static final String TEAM_1_NAME = "CHAOS";
    private static final int ROWS = 6;
    private static final int COLS = 6;

    // CONSTRUCTORS
    public OrderAndChaos() {
        super(ROWS, COLS, GAME_NAME);
        this.setWinLength(DEFAULT_WIN_LENGTH);
        this.getBoard().setGameType(GAME_NAME);
        this.setTeamsFromUserInput(TEAM_0_NAME, TEAM_1_NAME);
    }

    // ABSTRACT METHOD IMPLEMENTATIONS
    public void playGame() {
        Board board = this.getBoard();
        board.displayBoard();
        Team orderTeam = this.getTeamByName(TEAM_0_NAME);
        Team chaosTeam = this.getTeamByName(TEAM_1_NAME);

        while (true) {
            this.makeNextMove();
            board.displayBoard();

            boolean orderWon = this.isWinner();

            if (orderWon) {
                this.setWinner(orderTeam);
                System.out.println("Congratulations team ORDER! You won the game!");
                orderTeam.incrementPlayerwinCounts();

                this.reset(GAME_NAME);
                board = this.getBoard();

                if (this.isUserDone()) {
                    break;
                } else {
                    board.displayBoard();
                }
            } else if (this.getIsBoardFull()) {
                this.setWinner(chaosTeam);
                System.out.println("Congratulations team CHAOS! You won the game!"); // if board full w/o 5 in a row, CHAOS wins
                chaosTeam.incrementPlayerwinCounts();

                this.reset(GAME_NAME);
                board = this.getBoard();

                if (this.isUserDone()) {
                    break;
                } else {
                    board.displayBoard();
                }
            } else {
                this.incrementTurnNumber();
            }
        }
    };

    public void makeNextMove() {
        Team currentTeam = this.getCurrentTeam(); // the team whose turn it is currently
        Player currentPlayer = currentTeam.getRandomPlayer(); // get a random player from the current team
        Cell inputCell = this.getNextPlayerInputCell(currentTeam, currentPlayer);

        // Order and chaos allows either player to play either game piece
        System.out.print("[TEAM " + currentTeam.getName() + "] " + currentPlayer.getName() + " enter your game piece (X/O):\t");
        String input;
        GamePiece piece;

        while (true) {
            try {
                input = this.getScanner().next();
                char symbol = Character.toUpperCase(input.charAt(0));

                if (symbol != 'X' && symbol != 'O') throw new IllegalArgumentException();

                piece = new GamePiece(symbol);
                break;
            } catch (Exception e) {
                System.out.print("Invalid game piece. Please enter a valid piece (X/O):\t");
            }
        }

        inputCell.setAllFields(piece, currentTeam, this.getTurnNumber(), currentPlayer);
    };

    public boolean isWinner() {
        Board board = this.getBoard();

        for (List<int[]> position : this.getWinPositions()) {
            GamePiece[] pieces = new GamePiece[position.size()];

            for (int i = 0; i < position.size(); i++) {
                int[] coordinate = position.get(i);
                int x = coordinate[0], y = coordinate[1];
                GamePiece piece = board.getCell(x, y).getValue();
                pieces[i] = piece;
            }

            // check if all pieces are equal
            GamePiece firstPiece = pieces[0];
            if (firstPiece == null) continue;
            char firstSymbol = firstPiece.getSymbol();

            for (int i = 0; i < pieces.length; i++) {
                if (pieces[i] == null || pieces[i].getSymbol() != firstSymbol) break;
                if (i == pieces.length - 1) return true; // order wins if 6 in a row in a winning position
            }
        }

        return false; // if got to here, order has not won yet
    }
}