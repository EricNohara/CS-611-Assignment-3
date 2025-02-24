/*
    Description:
    Interface that describes methods needed for implementing any turn based game.
    It is an interface rather than abstract class because it defines only desired behaviors.

    Abstract methods:
    - getTurnNumber(): used to get the current turn number
    - getCurrentTeam(): used to get the current team
    - incrementTurnNumber(): used to increment the current turn
    - resetTurnNumber(): used to reset the turn number
    - makeNextMove(): used to make the next move
    - isValidMove(): used to validate a move
*/

interface TurnBased {
    // all turn based games must have these methods
    int getTurnNumber();
    Team getCurrentTeam();
    void incrementTurnNumber();
    void resetTurnNumber();
    void makeNextMove(); 
    boolean isValidMove(int row, int column);
}