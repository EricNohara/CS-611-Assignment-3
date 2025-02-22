/*
    Description:  
    BoardGameHistory class extends GameHistory and represents the history of a board game. It stores the final board state,
    total turns taken, and the game details from GameHistory in a read-only format.

    Fields:  
    - boardState: The final state of the board at the end of the game.  
    - totalTurns: The total number of turns taken in the game.  

    Constructor:  
    - BoardGameHistory(Team winner, Board boardState, int totalTurns, int number): Initializes the game history with the winning team, 
    final board state, total turns, and game number.  

    Important Methods:  
    - toString(): Returns a formatted string representing the game history, including game details, winner, board size, and a sorted list of moves.  
        uses a TreeMap to iterate over the board cells in row major order and sppending them to the final string in sorted order by turn.
*/


import java.util.Map;
import java.util.TreeMap;

public class BoardGameHistory extends GameHistory {
    // READ ONLY: history cannot be changed
    private final Board boardState;
    private final int totalTurns;

    // CONSTRUCTOR
    public BoardGameHistory(Team winner, Board boardState, int totalTurns, int number) {
        super(winner, totalTurns);
        this.boardState = boardState;
        this.totalTurns = totalTurns;
    }
    
    // GETTER methods
    public Board getBoardState() {
        return this.boardState;
    }

    public int getTotalTurns() {
        return this.totalTurns;
    }

    // TO STRING METHOD
    public String toString() {
        String data = "GAME NUMBER:\t" + this.getGameNumber() + "\n";
        data += "GAME NAME:\t" + this.boardState.getGameType() + "\n";

        if (this.getWinner() != null) data += "WINNER TEAM:\tTeam " + this.getWinner().getName() + "\n";
        else data += "GAME TIED\n";

        data += "TURNS TAKEN:\t" + this.totalTurns + "\n";
        data += "BOARD SIZE:\t" + this.boardState.getRows() + "x" + this.boardState.getColumns() + "\n";
        data += "MOVES (NUMBER, POSITION, PLAYER, PIECE, TEAM):\n";

        Map<Integer, String> sortedMoves = new TreeMap<>(); // use to add the moves in sorted order

        for (Cell[] cellRow : this.boardState.getBoard()) {
            for (Cell cell : cellRow) {
                if (cell.getValue() != null) {
                    String cellData = cell.toString();
                    String[] splitCellData = cellData.split(",");
                    int turnNum = Integer.parseInt(splitCellData[0]);
                    sortedMoves.put(turnNum, cellData);
                } 
            }
        }

        for (Map.Entry<Integer, String> entry : sortedMoves.entrySet()) data += entry.getValue();

        return data;
    }
}