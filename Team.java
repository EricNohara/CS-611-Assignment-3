/*
    Description:
    Class representing a Team of one or more players.

    Fields:
    - random: A class level instance of Random used to get a random player from a team.
    - team: An array of Player objects representing the members of the team.
    - number: The unique index of the team within the game.
    - name: The name of the team, defaulting to "Team #" based on the team number.

    Constructors:
    - Team(): Initializes a team with a single default player and an undefined team number.
    - Team(Player player, int number): Creates a team with a single player and assigns it a team number.
    - Team(Player player, int number, String name): Creates a team with a single player, a team number, and a custom name.
    - Team(Player[] team, int number): Initializes a team with multiple players and assigns it a team number.
    - Team(Player[] team, int number, String name): Initializes a team with multiple players, a team number, and a custom name.

    Interesting Methods:
    - getRandomPlayer(): Selects and returns a random player from the team.
    - displayPlayerWinCounts(): Prints each player's win count to stdout.
    - incrementPlayerWinCounts(): Increments the win count for all players on the team.
*/


import java.util.Random;

public class Team {
    private static final Random random = new Random(); // used on the class level
    private Player[] team; // array of players on the same team
    private int number; // the index of the team in the list of teams in the Game class
    private String name; // name of the team - default to Team #
    
    // CONSTRUCTORS
    public Team () {
        this.team = new Player[] {new Player()};
        this.number = -1;
        this.name = "" + this.number;
    }

    public Team (Player player, int number) {
        this.team = new Player[] {player};
        this.number = number;
        this.name = "" + this.number;
    }

    public Team (Player player, int number, String name) {
        this.team = new Player[] {player};
        this.number = number;
        this.name = name;
    }

    public Team(Player[] team, int number) {
        this.team = team;
        this.number = number;
        this.name = "" + this.number;
    }

    public Team(Player[] team, int number, String name) {
        this.team = team;
        this.number = number;
        this.name = name;
    }

    // GETTER METHODS
    public Player[] getTeam() {
        return this.team;
    }

    public String getName() {
        return this.name;
    }

    public int getNumber() {
        return this.number;
    }

    public Player getRandomPlayer() {
        return this.team[this.random.nextInt(this.team.length)];
    }

    // SETTER METHODS
    public void setTeam(Player[] team) {
        this.team = team;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    // PLAYER WIN COUNT INTERFACE
    public void displayPlayerWinCounts() {
        for (Player player : this.team) {
            System.out.println("[TEAM " + this.name + "] " + player.getName() + ":\t" + player.getWinCount() + " wins");
        }
    }

    public void incrementPlayerwinCounts() {
        for (Player player : this.team) {
            player.incrementWinCount();
        }
    }

}