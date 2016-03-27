package Gameplay;

import java.awt.Color;

public class Rules {
	public final int MAX_WALLS;
	public final int MAX_PLAYERS;
	public final int NO_OF_AI_OPPONENTS;
	private Color[] playerColours; 
	
	public Rules(int MAX_WALLS, int MAX_PLAYERS, int NO_OF_AI_OPPONENTS, Color[] playerColours){
		this.MAX_WALLS = MAX_WALLS;
		this.MAX_PLAYERS = MAX_PLAYERS;
		this.NO_OF_AI_OPPONENTS = NO_OF_AI_OPPONENTS;
		this.setPlayerColours(playerColours);
	}

	public Color[] getPlayerColours() {
		return playerColours;
	}

	public void setPlayerColours(Color[] playerColours) {
		this.playerColours = playerColours;
	}
}
