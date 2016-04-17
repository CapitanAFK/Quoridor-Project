package gameplay;

import java.awt.Color;

public class Rules {
	public final int MAX_WALLS;
	public final int MAX_PLAYERS;
	private String[] playerColours; 
	private String gameRules;
	
	public Rules(int MAX_WALLS, int MAX_PLAYERS, String[] playerColours, String gameRules){
		this.MAX_WALLS = MAX_WALLS;
		this.MAX_PLAYERS = MAX_PLAYERS;
		this.setPlayerColours(playerColours);
		this.gameRules = gameRules;
	}

	public String[] getPlayerColours() {
		return playerColours;
	}

	public void setPlayerColours(String[] playerColours) {
		this.playerColours = playerColours;
	}

	public String getGameRules() {
		return gameRules;
	}

	public void setGameRules(String gameRules) {
		this.gameRules = gameRules;
	}
}
