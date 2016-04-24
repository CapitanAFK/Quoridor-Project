package gameplay;

import java.awt.Color;
import java.util.ArrayList;

public class Rules {
	public final int MAX_WALLS;
	public final int MAX_PLAYERS;
	private ArrayList<Integer> botPlayerIDs;
	private String[] playerColours; 
	private String[] playerNames; 
	private String gameRules;
	
	public Rules(int MAX_WALLS, int MAX_PLAYERS, String[] playerColours, String[] playerNames, ArrayList<Integer> botPlayerIDs, String gameRules){
		this.MAX_WALLS = MAX_WALLS;
		this.MAX_PLAYERS = MAX_PLAYERS;
		this.playerColours = playerColours;
		this.playerNames = playerNames;
		this.botPlayerIDs = botPlayerIDs;
		this.gameRules = gameRules;
	}
	
	

	public ArrayList<Integer> getBotPlayerIDs() {
		return botPlayerIDs;
	}

	public String[] getPlayerColours() {
		return playerColours;
	}
	
	public String[] getPlayerNames() {
		return playerNames;
	}

	public String getGameRules() {
		return gameRules;
	}
}
