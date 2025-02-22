/*
    Description:  
    The Board class represents a game board consisting of a grid of Cell objects.
    It provides methods to manage the board state, retrieve board information, 
    and display the board in a structured format.

    Fields:  
    - rows: The number of rows in the board.  
    - columns: The number of columns in the board.  
    - gameType: The type of game being played on this board.  
    - board: A 2D array representing the board grid, containing Cell objects.  

    Constructors:  
    - Board(): Creates an empty board with undefined dimensions and game type.  
    - Board(int rows, int columns): Creates a board with specified dimensions and initializes all cells.  
    - Board(int rows, int columns, String type): Creates a board with specified dimensions and game type.  

    Important Methods:  
    - getCell(int row, int column): Returns the Cell object at the specified coordinates.  
    - displayBoard(): Prints the current state of the board with row and column labels.  
    - isBoardCellEmpty(int row, int column): Checks if the specified cell on the board is empty.  
 */

public class Board {
    private final int rows;
    private final int columns;
    private String gameType;
    private Cell[][] board;

    // CONSTRUCTORS
    // to create empty board
    public Board() {
        this.rows = -1;
        this.columns = -1;
        this.gameType = null;
        this.board = null;
    }

    // to create a board of rows x columns size
    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.gameType = null;
        this.board = new Cell[rows][columns];

        // fill the board with empty cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = new Cell(i,j);
            }
        }
    }

    public Board(int rows, int columns, String type) {
        this.rows = rows;
        this.columns = columns;
        this.gameType = type;
        this.board = new Cell[rows][columns];

        // fill the board with empty cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = new Cell(i,j);
            }
        }
    }

    // GETTER methods
    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public String getGameType() {
        return this.gameType;
    }

    public Cell[][] getBoard() {
        return this.board;
    }

    public Cell getCell(int row, int column) {
        return this.board[row][column];
    }

    // SETTER methods - can't change the rows/columns of a board after you create it
    public void setGameType(String type) {
        this.gameType = type;
    }

    // VALIDATION method - if a cell on the board is filled or not
    public boolean isBoardCellEmpty(int row, int column) {
        return this.board[row][column].isEmpty();
    }

    // DISPLAY methods:
    // helper function used to get the board width separators
    private String getBoardWidthSeperator(boolean withNumbers) {
        String seperator = "";

        for (int i = 0; i < this.columns; i++) {
            if (i == 0 && withNumbers) seperator += "   ";
            seperator += "+---";
        }

        return seperator + "+\n";
    }

    // toString method to get the string representation of the board
    public String toStringWithNumbers() {
        String board = "";

        for (int i = 0; i < this.rows; i++) {
            if (i == 0) {
                board += "     ";
                for (int c = 0; c < this.columns; c++) {
                    if (c < 10) board += (c + "   ");
                    else board += (c + "  ");
                }
                board += "\n";
            }
            board += this.getBoardWidthSeperator(true);

            for (int j = 0; j < this.columns; j++) {
                if (j == 0) {
                    if (i < 10) board += (i + "  ");
                    else board += (i + " ");
                }

                char cellValue = this.board[i][j].getValue() != null ? this.board[i][j].getValue().getSymbol() : ' ';

                board += ("| " + cellValue + " ");

                if (j == this.columns - 1) board += "|\n";
            }
        }

        board += this.getBoardWidthSeperator(true);

        return board;
    }

    public String toString() {
        String board = "";

        for (int i = 0; i < this.rows; i++) {
            board += this.getBoardWidthSeperator(false);

            for (int j = 0; j < this.columns; j++) {
                char cellValue = this.board[i][j].getValue() != null ? this.board[i][j].getValue().getSymbol() : ' ';

                board += ("| " + cellValue + " ");

                if (j == this.columns - 1) board += "|\n";
            }
        }

        board += this.getBoardWidthSeperator(false);

        return board;
    }

    // method to display the current state of the board
    public void displayBoard() {       
        System.out.print(this.toStringWithNumbers());
    }
}