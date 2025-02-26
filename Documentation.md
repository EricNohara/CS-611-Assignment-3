# CS611-Assignment 3 Documentation

## Tic Tac Toe - Take II

---

### Student Information
- **Name: Eric Nohara-LeClair**  
- **Email: ernohara@bu.edu**  
- **Student ID: U90387562**  

---

## Class Hierarchy Structure

- Game (abstract)
    - BoardGame (abstract)

---

## Notes


### Design Decisions
- Created the abstract Game class to allow for future games to be developed using the same framework.
- TicTacToe, OrderAndChaos, and SuperTicTacToe extend ConsecutivePiecesGame rather than OrderAndChaos being a subclass of TicTacToe because they are both board games with different rules (although similar) but require getting n pieces in a row to win.
- The Game -> BoardGame -> ConsecutivePiecesGame -> TicTacToe/OrderAndChaos/SuperTicTacToe hierarchy makes it easy to create new board games with different rules and win conditions, and also to create new games that are not board games.
- TurnBased interface allows ALL board games to be turned based, while allowing generic games to not be (e.g. shooters).
- BoardGameHistory extending GameHistory allows me to store the game histories of generic games later on.
- I broke up Board and Cell into seperate classes to more easily store metadata about the game state.
- I broke up Player and Team to easily store player names and win counts seperately from team names and other team fields.

### Implementation Notes
- TicTacToe supports arbitrary board sizes and win lengths. Board width and height is capped at 40 due resolution limitations on the terminal (it becomes hard to see the values on the board).
- Implemented GameHistory and BoardGameHistory to store the complete history data of games and allow the user to export the data to a data.txt file after they are done playing.
- Any GameHistory type requires a custom toString method to return a string of the formatted game history data.
- BoardGameHistory returns a list of all turns in order by sorting cells using a TreeMap since cells are iterated over in row major order, not in order of turn number.
- Implemented Team to allow only teams to play board games rather than individual players. Individual players are treated as teams with only one player.
- Implemented a method to select a random player from a given team to make the next move.
- Implemented certain user I/O methods in BoardGame class because all board games can query a user to create teams, get the next input cell, or check if the user is done playing.
- All ConsecutivePiecesGames precompute all possible winning positions as a set of lists of (row, col) pairs based on the board's dimensions and the specified win length. This is done to make checking the win condition much more efficient, as the isWinner methods only need to check the precomputed positions to see if a player has won rather iterating over all cells in the board every turn. 
- If a user wants to play again, rather than a new game object being made, the board is reset along with the turn number. This was done to avoid recomputing winning positions unnecessarily.
- Implemented input error checking for all user inputs.
- If inputted, the game histories for ALL games played by the user will be exported to a data.txt file.
- The SuperTicTacToe game will export the game history of the Super game and all TicTacToe games it contains.
- Implemented GameInitializer class to allow easy extendibility to allow a user to choose from more games in the future.


---

## How to Compile and Run


```java
javac Main.java     // compile
java Main           // run
```


---

## Input/Output Example


```
Please enter game to play (T/O/S):      s
Enter comma seperated list of players on team X (leave blank for default player):       Eric
Enter comma seperated list of players on team O (leave blank for default player):       Player1,Player2,Player3
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+

[TEAM X] Eric enter the game grid ID to make a move (A-I):      a
[TEAM X] Eric enter your move (row,col):        0,0
+---+---+---+ +---+---+---+ +---+---+---+
| X |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+

[TEAM O] Player2 enter the game grid ID to make a move (A-I):   b
[TEAM O] Player2 enter your move (row,col):     0,0
+---+---+---+ +---+---+---+ +---+---+---+
| X |   |   | | O |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+

...

[TEAM X] Eric enter the game grid ID to make a move (A-I):      i
[TEAM X] Eric enter your move (row,col):        0,2
X won board I
+---+---+---+ +---+---+---+ +---+---+---+
| X | X | X | | O | O | O | | O | O | O |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | | X | X | X | | O | O |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | | X | X | X |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+
|   |   |   | |   |   |   | |   |   |   |
+---+---+---+ +---+---+---+ +---+---+---+

Congratulations team X! You won the game!
Would you like to play again (y/n):     n
[TEAM X] Eric:  1 wins
[TEAM O] Player1:       0 wins
[TEAM O] Player2:       0 wins
[TEAM O] Player3:       0 wins
Would you like to save your game histories to data.txt (y/n):   y
Saved game history data in data.txt
```


---

## data.txt Output Example

```
GAME NUMBER:    1
GAME NAME:      Super Tic Tac Toe
WINNER TEAM:    Team X
TURNS TAKEN:    17
BOARD SIZE:     3x3
MOVES (NUMBER, POSITION, PLAYER, PIECE, TEAM):

GAME NUMBER:    1
GAME ID:        A
GAME NAME:      Tic Tac Toe
WINNER TEAM:    Team X
TURNS TAKEN:    3
BOARD SIZE:     3x3
MOVES (NUMBER, POSITION, PLAYER, PIECE, TEAM):
0,(0,0),Eric,Piece X,Team X
2,(0,1),Eric,Piece X,Team X
4,(0,2),Eric,Piece X,Team X

GAME NUMBER:    1
GAME ID:        B
GAME NAME:      Tic Tac Toe
WINNER TEAM:    Team O
TURNS TAKEN:    3
BOARD SIZE:     3x3
MOVES (NUMBER, POSITION, PLAYER, PIECE, TEAM):
1,(0,0),Player2,Piece O,Team O
3,(0,1),Player3,Piece O,Team O
5,(0,2),Player3,Piece O,Team O

GAME NUMBER:    1
GAME ID:        C
GAME NAME:      Tic Tac Toe
WINNER TEAM:    Team O
TURNS TAKEN:    3
BOARD SIZE:     3x3
MOVES (NUMBER, POSITION, PLAYER, PIECE, TEAM):
7,(0,0),Player3,Piece O,Team O
9,(0,1),Player1,Piece O,Team O
11,(0,2),Player3,Piece O,Team O

GAME NUMBER:    1
GAME ID:        D
GAME NAME:      Tic Tac Toe
GAME TIED
TURNS TAKEN:    0
BOARD SIZE:     3x3
MOVES (NUMBER, POSITION, PLAYER, PIECE, TEAM):

GAME NUMBER:    1
GAME ID:        E
GAME NAME:      Tic Tac Toe
WINNER TEAM:    Team X
TURNS TAKEN:    3
BOARD SIZE:     3x3
MOVES (NUMBER, POSITION, PLAYER, PIECE, TEAM):
6,(0,0),Eric,Piece X,Team X
8,(0,1),Eric,Piece X,Team X
10,(0,2),Eric,Piece X,Team X

GAME NUMBER:    1
GAME ID:        F
GAME NAME:      Tic Tac Toe
GAME TIED
TURNS TAKEN:    2
BOARD SIZE:     3x3
MOVES (NUMBER, POSITION, PLAYER, PIECE, TEAM):
13,(0,0),Player3,Piece O,Team O
15,(0,1),Player1,Piece O,Team O

GAME NUMBER:    1
GAME ID:        G
GAME NAME:      Tic Tac Toe
GAME TIED
TURNS TAKEN:    0
BOARD SIZE:     3x3
MOVES (NUMBER, POSITION, PLAYER, PIECE, TEAM):

GAME NUMBER:    1
GAME ID:        H
GAME NAME:      Tic Tac Toe
GAME TIED
TURNS TAKEN:    0
BOARD SIZE:     3x3
MOVES (NUMBER, POSITION, PLAYER, PIECE, TEAM):

GAME NUMBER:    1
GAME ID:        I
GAME NAME:      Tic Tac Toe
WINNER TEAM:    Team X
TURNS TAKEN:    3
BOARD SIZE:     3x3
MOVES (NUMBER, POSITION, PLAYER, PIECE, TEAM):
12,(0,1),Eric,Piece X,Team X
14,(0,0),Eric,Piece X,Team X
16,(0,2),Eric,Piece X,Team X
```


---

## Requirements


- Tested on 5.15.167.4-microsoft-standard-WSL2
- Tested on Microsoft Windows [Version 10.0.26100.3194]


---


## Testing Strategy


- Tested with small and large board sizes and win lengths to ensure correctness of win conditions.
- Tested with varying team sizes to ensure players are chosen at random to play next.
- Compared data.txt output with known final game states from finished game.


---

## References and Attribution


- Used [w3schools](https://www.w3schools.com/java/java_files_create.asp) to learn how to do file I/O in java for writing game history data to data.txt file.
- Used [documentation](https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html) to sort turns in BoardGameHistory.
