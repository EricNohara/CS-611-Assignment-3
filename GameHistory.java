/*
    Description:
    Abstract class representing a game history record for a generic game. 
    This class stores the winner of a game and the game number. 
    Since game history should remain unchanged, fields are declared as final.

    Fields:  
    - winner: The team that won the game.  
    - gameNumber: The unique identifier for the game.  

    Constructor:  
    - GameHistory(Team winner, int number): Initializes a game history object with the given winner and game number.  

    Getter Methods:  
    - getWinner(): Returns the winning team of the game.  
    - getGameNumber(): Returns the game number.  

    Abstract Method:  
    - toString(): Must be implemented by subclasses to provide a string representation of the game history.  
*/

public abstract class GameHistory {
    // READ ONLY: history cannot be changed
    private final Team winner;
    private final int gameNumber;

    public GameHistory(Team winner, int number) {
        this.winner = winner;
        this.gameNumber = number;
    }

    // GETTER METHODS
     public Team getWinner() {
        return this.winner;
    }

    public int getGameNumber() {
        return this.gameNumber;
    }

    // ABSTRACT METHODS
    public abstract String toString();
}