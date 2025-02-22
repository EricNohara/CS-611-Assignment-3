/*
    Description:
    Class representing a Player with a name and a win count.

    Fields:  
    - DEFAULT_PLAYER_NAME: A class level constant string representing default player name.  
    - name: The name of the player, defaults to "Anonymous Player" if not provided.  
    - winCount: The number of wins the player has accumulated.  

    Constructors:  
    - Player(): Initializes a player with the default name and a win count of 0.  
    - Player(String name): Initializes a player with a given name and a win count of 0.  

    Getter Methods:  
    - getName(): Returns the name of the player.  
    - getWinCount(): Returns the number of wins the player has.  

    Setter Methods:  
    - setName(String name): Updates the player's name.  
    - setWinCount(int winCount): Sets the player's win count to a specified value.  
    - incrementWinCount(): Increases the player's win count by 1.  
*/

public class Player {
    private String name;
    private int winCount;
    private static final String DEFAULT_PLAYER_NAME = "Anonymous Player";

    // CONSTRUCTORS
    public Player() {
        this.name = DEFAULT_PLAYER_NAME;
        this.winCount = 0;
    }
    
    public Player(String name) {
        this.name = name;
        this.winCount = 0;
    }

    // GETTER METHODS
    public String getName() {
        return this.name;
    };

    public int getWinCount() {
        return this.winCount;
    }

    // SETTER METHODS
    public void setName(String name) {
        this.name = name;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public void incrementWinCount() {
        this.winCount += 1;
    }
}
