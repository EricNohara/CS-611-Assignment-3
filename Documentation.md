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
│   ├── Has a Board
│   │
│   ├── ConsecutivePiecesGame (abstract)
│   │   │
│   │   ├── TicTacToe
│   │   ├── OrderAndChaos
│   │   ├── SuperTicTacToe
│   │       ├── Has an array of TicTacToe games

Board
├── Has an array of Cell

Cell

GameHistory (abstract)
│
├── BoardGameHistory

Team
├── Has an array of Player

Player

GamePiece

GameInitializer

Main
├── Has a GameInitializer

```

---

## Notes


