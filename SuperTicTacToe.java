import java.util.List;
import java.util.ArrayList;

public class SuperTicTacToe extends ConsecutivePiecesGame {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static final String GAME_NAME = "Super Tic Tac Toe";
    private static final int DEFAULT_WIN_LENGTH = 3;
    // private static final List<List<int[]>> winPositions = generateWinPositions();

    TicTacToe[][] games = new TicTacToe[ROWS][COLS]; // 3x3 board of tic tac toe games 

    public SuperTicTacToe() {
        super(GAME_NAME);
        this.setWinLength(DEFAULT_WIN_LENGTH);

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                games[r][c] = new TicTacToe();
            }
        }
    }

    // ABSTRACT METHOD IMPLEMENTATIONS
    public boolean isWinner() {
        Team currentTeam = this.getCurrentTeam();

        // iterate over all winning positions and see if there is a team that has won all games in a winning position
        for (List<int[]> position : this.getWinPositions()) {
            for (int i = 0; i < position.size(); i++) {
                int[] coordinate = position.get(i);
                int x = coordinate[0], y = coordinate[1];
                if (!games[x][y].getWinner().equals(currentTeam)) break;
                if (i == position.size() - 1) return true;
            }
        }

        return false;
    }

    public void makeNextMove() {}

    public void playGame() {}

}