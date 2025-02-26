/*
    Description:
    Class for the Super Tic Tac Toe game, which extends ConsecutivePiecesGame and manages a 3x3 grid of individual Tic Tac Toe games.

    Class Level Constants:
    - ROWS: Default rows in the Super Tic Tac Toe grid.
    - COLS: Default columns in the Super Tic Tac Toe grid.
    - GAME_NAME: Name of the game.

    Instance Variables:
    - games: 3x3 grid of TicTacToe games.
    - boards: 3x3 grid of Board objects corresponding to each TicTacToe game.

    Constructors:
    - public SuperTicTacToe(): Initializes a Super Tic Tac Toe game, setting up individual TicTacToe instances 
    with corresponding game ids A-I and their associated boards.

    Abstract Method Implementations:
    - isWinner(): Determines if the current team has won by checking all winning positions in the Super Tic Tac Toe grid.
    - makeNextMove(): Processes the current player's move by selecting a TicTacToe game and making a move within that game.
    - playGame(): Runs the game loop, allowing players to take turns until a winner is found or the game ends in a tie.

    Other Important Methods:
    - getGameByID(String id): Retrieves a specific TicTacToe game by its ID.
    - getGameFromUserInput(Team team, Player player): Prompts the user to select a TicTacToe game from the grid.
    - displayGame(): Displays the current state of all TicTacToe boards.
    - resetGameBoards(): Resets all individual TicTacToe boards while maintaining game history of each game.
    - allGamesFinished(): Checks if all TicTacToe games are either won or tied.
*/

import java.util.List;

public class SuperTicTacToe extends ConsecutivePiecesGame {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static final String GAME_NAME = "Super Tic Tac Toe";

    private TicTacToe[][] games = new TicTacToe[ROWS][COLS]; // 3x3 board of tic tac toe games 
    private Board[][] boards = new Board[ROWS][COLS];

    public SuperTicTacToe() {
        super(ROWS, COLS, GAME_NAME);
        this.getBoard().setGameType(GAME_NAME);
        this.setTeamsFromUserInput("" + TicTacToe.TEAM_0_SYMBOL, "" + "" + TicTacToe.TEAM_1_SYMBOL);

        char gameID = 'A';
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                games[r][c] = new TicTacToe(gameID + "");
                boards[r][c] = games[r][c].getBoard();
                games[r][c].setTeams(this.getTeams());
                gameID++;
            }
        }
    }

    // GETTER methods
    public TicTacToe[][] getGames() {
        return this.games;
    }

    public Board[][] getBoards() {
        return this.boards;
    }

    // returns the game associated with the give id
    public TicTacToe getGameByID(String id) {
        for (TicTacToe[] gameRow : this.games) {
            for (TicTacToe game : gameRow) {
                if (game.getGameID().equals(id)) return game;
            }
        }

        return null;
    }
    
    // SETTER methods
    public void setGames(TicTacToe[][] games) {
        this.games = games;
    }

    public void setBoards(Board[][] boards) {
        this.boards = boards;
    }

    // USER INPUT METHOD:
    public TicTacToe getGameFromUserInput(Team team, Player player) {
        String input;
        char inputChar;

        System.out.print("[TEAM " + team.getName() + "] " + player.getName() + " enter the game grid ID to make a move (A-I):\t");

        while(true) {
            try {
                input = this.getScanner().next();
                inputChar = Character.toUpperCase(input.charAt(0));
                if (inputChar < 'A' || inputChar > 'I') throw new IllegalArgumentException();
                input = inputChar + ""; // convert it to a string since all IDs are strings
                break;
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a valid game ID (A-I): \t");
            }
        }

        return this.getGameByID(input);
    }

    // DISPLAY METHOD
    public void displayGame() {
        Board.displayBoards(boards);
    }

    // resets every individual tic tac toe board
    public void resetGameBoards() {
        for (TicTacToe[] gameRow : games) {
            for (TicTacToe game : gameRow) {
                Board board = game.getBoard();
                this.addGameHistory(new BoardGameHistory(game.getWinner(), board, game.getTurnNumber(), this.getGameNumber() - 1, game.getGameID()));
                game.setBoard(new Board(board.getRows(), board.getColumns(), TicTacToe.GAME_NAME));
            }
         }
    }

    public boolean allGamesFinished() {
        for (TicTacToe[] gameRow : games) {
            for (TicTacToe game : gameRow) {
                if (game.getWinner() == null && !game.getIsBoardFull()) return false; // if there is a game without a winner that is not tied yet
            }
        }
        return true;
    }

    // ABSTRACT METHOD IMPLEMENTATIONS
    public boolean isWinner() {
        Team currentTeam = this.getCurrentTeam();

        // iterate over all winning positions and see if there is a team that has won all games in a winning position
        for (List<int[]> position : this.getWinPositions()) {
            for (int i = 0; i < position.size(); i++) {
                int[] coordinate = position.get(i);
                int x = coordinate[0], y = coordinate[1];

                Team currentGameWinner = games[x][y].getWinner();

                if (currentGameWinner == null || games[x][y].getWinner().getNumber() != currentTeam.getNumber()) break;
                if (i == position.size() - 1) return true;
            }
        }

        return false;
    }

    public void makeNextMove() {
        Team currentTeam = this.getCurrentTeam(); // the team whose turn it is currently
        Player currentPlayer = currentTeam.getRandomPlayer(); // get a random player from the current team

        TicTacToe game = this.getGameFromUserInput(currentTeam, currentPlayer);
        Board gameBoard = game.getBoard();
        Cell inputCell = this.getNextPlayerInputCell(currentTeam, currentPlayer, gameBoard); // get the input cell for the selected game board

        GamePiece piece = currentTeam.getNumber() == 0 ? new GamePiece(TicTacToe.TEAM_0_SYMBOL) : new GamePiece(TicTacToe.TEAM_1_SYMBOL);
        game.makeMove(inputCell, piece, currentTeam, this.getTurnNumber(), currentPlayer);
    }

    public void playGame() {
        this.displayGame();

        while (true) {
            this.makeNextMove();
            this.displayGame();
            
            Team currentTeam = this.getCurrentTeam();
            boolean teamWon = this.isWinner();

            if (teamWon) {
                this.setWinner(currentTeam);
                System.out.println("Congratulations team " + currentTeam.getName() + "! You won the game!");

                currentTeam.incrementPlayerwinCounts();
                this.reset(GAME_NAME);
                this.resetGameBoards();

                if (this.isUserDone()) break;
                else this.displayGame();
            } else if (this.allGamesFinished()) {
                System.out.println("The game has ended in a tie.");

                this.reset(GAME_NAME);
                this.resetGameBoards();

                if (this.isUserDone()) break;
                else this.displayGame();
            } else {
                this.incrementTurnNumber();
            }
        }
    }

}