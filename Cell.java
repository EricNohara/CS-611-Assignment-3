/*
    Description:
    Represents a single cell in the game grid. Holds the value of the game piece in the cell and
    information about the team, player, and turn that modified the cell. Provides methods to check
     if the cell is empty and provide a string representation used when printing game histories.

    Fields:  
    - value: The GamePiece placed in the cell.  
    - teamModified: The team that last modified the cell.  
    - playerModified: The player who last modified the cell.  
    - turnModified: The turn number when the cell was modified.  
    - row: The row index of the cell in the grid.  
    - column: The column index of the cell in the grid.  

    Constructors:  
    - Cell(): Initializes the cell with default values (null for value, team, and player; -1 for turn, row, and column).  
    - Cell(int row, int column): Initializes the cell with specified row and column indices, leaving other fields as default.  
    - Cell(GamePiece value, Team teamModified, Player playerModified, int turnModified, int row, int column): Initializes the 
    cell with specified values for the game piece, team, player, turn, row, and column.  

    Interesting Methods:  
    - setAllFields(GamePiece piece, Team team, int turn, Player player): Sets all fields of the cell (value, team, turn, player).  
    - isEmpty(): Checks if the cell is empty (i.e., it does not contain a game piece).  
    - toString(): Returns a string representation of the cell or null if empty.  
*/

public class Cell {
    private GamePiece value;
    private Team teamModified;
    private Player playerModified;
    private int turnModified;
    private int row;
    private int column;

    // CONSTRUCTORS
    public Cell () {
        this.value = null;
        this.teamModified = null;
        this.playerModified = null;
        this.turnModified = -1;
        this.row = -1;
        this.column = -1;
    }

    public Cell (int row, int column) {
        this.value = null;
        this.teamModified = null;
        this.playerModified = null;
        this.turnModified = -1;
        this.row = row;
        this.column = column;
    }

    public Cell(GamePiece value, Team teamModified, Player playerModified, int turnModified, int row, int column) {
        this.value = value;
        this.teamModified = teamModified;
        this.playerModified = playerModified;
        this.turnModified = turnModified;
        this.row = row;
        this.column = column;
    }

    // GETTER METHODS
    public GamePiece getValue() {
        return this.value;
    }

    public Team getTeamModified() {
        return this.teamModified;
    }

    public int getTurnModified() {
        return this.turnModified;
    }

    public Player getPlayerModified() {
        return this.playerModified;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    // SETTER METHODS
    public void setValue(GamePiece piece) {
        this.value = piece;
    }

    public void setTeamModified(Team team) {
        this.teamModified = team;
    }

    public void setTurnModified(int turn) {
        this.turnModified = turn;
    }

    public void setPlayerModified(Player player) {
        this.playerModified = playerModified;
    }

    public void setAllFields(GamePiece piece, Team team, int turn, Player player) {
        this.value = piece;
        this.teamModified = team;
        this.turnModified = turn;
        this.playerModified = player;
    }

    // VALIDATION METHOD: check if the current cell is empty
    public boolean isEmpty() {
        return this.value == null;
    }

    // TO STRING METHOD
    public String toString() {
        if (this.value == null) return null;

        char symVal = this.value.getSymbol();

        return this.turnModified + ",(" + this.row + "," + this.column + ")," + this.playerModified.getName() + ",Piece " + symVal + ",Team " + this.teamModified.getName() + "\n";
    }
}