package gameplay;

import java.awt.Color;

public class Rules {
	public final int MAX_WALLS;
	public final int MAX_PLAYERS;
	private String[] playerColours; 
	
	public Rules(int MAX_WALLS, int MAX_PLAYERS, String[] playerColours){
		this.MAX_WALLS = MAX_WALLS;
		this.MAX_PLAYERS = MAX_PLAYERS;
		this.setPlayerColours(playerColours);
	}

	public String[] getPlayerColours() {
		return playerColours;
	}

	public void setPlayerColours(String[] playerColours) {
		this.playerColours = playerColours;
	}
}
