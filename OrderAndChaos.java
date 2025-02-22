/*
    Description:
    Class representing the Order and Chaos board game, extending the BoardGame class.
    This game is played on a 6x6 board with two teams: ORDER and CHAOS.
    ORDER wins by aligning five identical pieces in a row, while CHAOS wins if the board fills without such a sequence.

    Fields:  
    - winPositions: Class level constant of a list of all possible winning positions.  
    - winLength: Class level constant of the number of consecutive pieces needed to win (5).  
    - GAME_NAME: Class level constant of a string representing the game name.  

    Constructors:  
    - OrderAndChaos(): Initializes the board, generates winning positions, assigns teams, and sets the game type.  

    Abstract Method Implementations:  
    - playGame(): Controls the game loop, alternating turns between ORDER and CHAOS, checking for a winner, and handling game resets.  
    - makeNextMove(): Handles player input for placing a piece (X or O) and updates the board.  
    - isWinner(): Checks if any winning condition has been met for the ORDER team only.

    Getter Method:  
    - getWinPositions(): Returns the list of winning positions.  

    Private Methods:  
    - generateWinPositions(): Generates all possible horizontal, vertical, and diagonal win conditions.  
*/

import java.util.ArrayList;
import java.util.List;

public class OrderAndChaos extends BoardGame {
    // CLASS LEVEL CONSTANTS
    private static List<List<int[]>> winPositions; // every OrderAndChaos game has same win positions
    private static final int winLength = 5; // need 5 in a row to win
    private static final String GAME_NAME = "Order and Chaos";

    // CONSTRUCTORS
    public OrderAndChaos() {
        super(6,6, GAME_NAME);
        this.winPositions = generateWinPositions();

        // create and attach teams to the game object
        Team[] teams = new Team[2];
        teams[0] = this.getTeamFromUserInput(0, "ORDER");
        teams[1] = this.getTeamFromUserInput(1, "CHAOS");
        this.setTeams(teams);

        // set the board type to Order And Chaos
        this.getBoard().setGameType(GAME_NAME);
    }

    // GETTER METHOD
    public List<List<int[]>> getWinPositions() {
        return this.winPositions;
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

    // ABSTRACT METHOD IMPLEMENTATIONS
    public void playGame() {
        Board board = this.getBoard();
        board.displayBoard();
        Team orderTeam = this.getTeamByName("ORDER");
        Team chaosTeam = this.getTeamByName("CHAOS");

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

        for (List<int[]> position : this.winPositions) {
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