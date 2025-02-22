/*
    Description:
    Class representing a game piece in a board game. Each piece has a single character symbol (e.g., 'X' or 'O').

    Fields:  
    - symbol: A character representing the game piece.  

    Constructor:  
    - GamePiece(char symbol): Initializes a game piece with the given symbol.  

    Getter Method:  
    - getSymbol(): Returns the symbol of the game piece.  

    Setter Method:  
    - setSymbol(char symbol): Updates the symbol of the game piece.  
*/

public class GamePiece {
    private char symbol;

    public GamePiece(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return this.symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}