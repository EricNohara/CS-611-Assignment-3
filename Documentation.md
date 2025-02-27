# CS611-Assignment 3 Documentation

## Tic Tac Toe - Take II

---

### Student Information
- **Name: Eric Nohara-LeClair**  
- **Email: ernohara@bu.edu**  
- **Student ID: U90387562**  

---

## Class Hierarchy Structure

```
Game (abstract)
├── Has an array of Team
├── Has a winner Team
├── Has a List of GameHistory
│
├── BoardGame (abstract) ── implements TurnBased
    ├── Has a Board
    │
    ├── ConsecutivePiecesGame (abstract)
        │
        ├── TicTacToe
        ├── OrderAndChaos
        ├── SuperTicTacToe
            ├── Has an array of TicTacToe games

Board
├── Has an array of Cell

GameHistory (abstract)
├── BoardGameHistory

Team
├── Has an array of Player

Main
├── Has a GameInitializer

Standalone Classes:
Cell
Player
GamePiece
GameInitializer
```

---

## Scalability and Extendibility

- The Game -> BoardGame implements TurnBased -> ConsecutivePiecesGame -> ... class hierarchy allows me to easily implement other board games that require n pieces in a row to win (e.g. connect 4). Additionally, it allows me to easily implement other board games that have different win conditions while maintaining the core fields and methods that all board games share. Finally, it allows me to create other non board games using the same framework by having another game class extend Game (e.g. VideoGame, CardGame, etc.).

- The GameHistory -> BoardGameHistory class hierarchy allows me to easily keep track of the game histories of board games played. Implementing a new board game would simply need to pass in the board which all board games must have to store the state of the board game with BoardGameHistory. Additionally, the structure allows me to store the game histories of other types of games by creating a respective class which extends GameHistory (e.g. VideoGameHistory, CardGameHistory, etc.).

- The GameInitializer class allows me to abstract away the action of asking the user which game they would like to play and initializing the inputted game. Adding future games will only need to modify this class to be able to be initialized by user input.
- The GamePiece class allows me to abstract the notion of a piece on the board. In the future, I could easily add fields and methods to track the name and moveset of the piece to be used in games such as chess or checkers.

- The Cell class allows me to abstract an individual cell on a board game board, storing metadata on the current state of the cell and/or the move that is made within it. This allows me to write a generic BoardGameHistory class which works for any type of board game because it just saves the state of the board, which itself is a 2D array of cells, each with its own metadata about the game played. Thus, any board game that stores information in the cells can use the BoardGameHistory class to store information about a previous game.

- The SuperTicTacToe class contains a 2D array of TicTacToe games, rather than handling the TicTacToe logic for each individual board on the super grid within the SuperTicTacToe class. Rather, the game simply chooses which TicTacToe game to make a move on and calls TicTacToe methods such as isWinner, makeMove, etc. to make a move on that specific game using the interface provided in TicTacToe. Not only does this reduce repeated code, it ensures that the games within SuperTicTacToe follow the exact same game logic as in TicTacToe.

## Notable Changes from Assignment 2

- All logic regarding winLength and winPositions were abstracted away into the ConsecutivePiecesGame to reduce repeated code and ensure that all games that require n in a row to win generated winning positions in the same way. 

- Board was updated to provide a toString method along with a display method. Additionally, the static methods displayBoards and boardsToString were added to take as input a grid of boards and display or return a string representation of the boards. These static methods are used to display the SuperTicTacToe games.

- The Game class received an additional optional field called gameID which acts as an identifier for a given game object. TicTacToe games are initialized in the SuperTicTacToe constructor to have gameIDs ranging from A - I. The gameID is what the user utilizes to reference a specific game to make a move in for SuperTicTacToe. Additionally, it allows sub games to store their respective game histories while keeping track of the ID of the game.

- The team generation for TicTacToe, OrderAndChaos, and SuperTicTacToe were put into their respective constructors, since creating teams is an action that should be done when the game object is initialized.

- TicTacToe got a new constructor TicTacToe(String gameID) which skips the team generation and board size input steps and sets the gameID. This constructor is what SuperTicTacToe uses, since we do not want to get user input from all 9 TicTacToe games when the Super game is chosen.

- Game initialization was put in its own class GameInitializer to remove any specific logic from the Main class and improve extendibility for the future.

