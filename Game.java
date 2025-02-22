/*
    Description:
    Abstract class representing a generic game. This class stores the teams participating in the game,
    the winner, the game name, and a history of previously played games. It provides basic methods for
    accessing and modifying these fields, as well as abstract methods that must be implemented by
    subclasses to define game-specific logic.

    Fields:  
    - teams: An array of teams participating in the game.  
    - winner: The team that won the game.  
    - name: The name of the game.  
    - gameHistory: A list of past game history records.  

    Constructors:  
    - Game(): Initializes a game with default teams (2 teams, each with 1 player).  
    - Game(String name): Initializes a game with the specified name and default teams.  

    Getter Methods:  
    - getTeams(): Returns the array of teams participating in the game.  
    - getWinner(): Returns the team that won the game.  
    - getName(): Returns the name of the game.  
    - getGameHistory(): Returns the list of game history records.  
    - getTeamByName(String name): Returns the team with the specified name.  

    Setter Methods:  
    - setTeams(Team[] teams): Sets the teams participating in the game.  
    - setWinner(Team winner): Sets the winning team.  
    - setName(String name): Sets the name of the game.  
    - setGameHistory(List<GameHistory> history): Sets the game history.  
    - addGameHistory(GameHistory g): Adds a game history record.  

    Abstract Methods:  
    - playGame(): Must be implemented to define the logic for playing the game.  
    - isWinner(): Must be implemented to determine if a winner has been decided.  
    - exitGame(): Must be implemented to handle the actions required when the game is exited.  
*/

import java.util.ArrayList;
import java.util.List;

public abstract class Game {
    // every game has teams, a winner, a name, and a list of previously played games
    private Team[] teams;
    private Team winner;
    String name;
    private List<GameHistory> gameHistory;

    // CONSTRUCTORS
    public Game() {
        this.teams = new Team[] { new Team(new Player(), 0), new Team(new Player(), 1) }; // default of 2 teams with 1 player each
        this.winner = null;
        this.name = null;
        this.gameHistory = new ArrayList<>();
    }

    public Game(String name) {
        this.teams = new Team[] { new Team(new Player(), 0), new Team(new Player(), 1) }; // default of 2 teams with 1 player each
        this.winner = null;
        this.name = name;
        this.gameHistory = new ArrayList<>();
    }

    // GETTER METHODS
    public Team[] getTeams() {
        return this.teams;
    }

    public Team getWinner() {
        return this.winner;
    }

    public String getName() {
        return this.name;
    }

    public List<GameHistory> getGameHistory() {
        return this.gameHistory;
    }

    public Team getTeamByName(String name) {
        for (Team team : this.teams) {
            if (team.getName().equals(name)) return team;
        }

        return null; // team with inputted name was not found
    }

    // SETTER METHODS
    public void setTeams(Team[] teams) {
        this.teams = teams;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGameHistory(List<GameHistory> history) {
        this.gameHistory = history;
    }

    public void addGameHistory(GameHistory g) {
        this.gameHistory.add(g);
    }

    // ABSTRACT METHODS
    abstract void playGame();

    abstract boolean isWinner();

    abstract void exitGame();
}