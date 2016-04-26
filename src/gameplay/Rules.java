package gameplay;

import java.awt.Color;
import java.util.ArrayList;

public class Rules {
	public final int MAX_WALLS;
	public final int MAX_PLAYERS;
	private ArrayList<String> botPlayerNames;
	private String[] playerColours; 
	private String[] playerNames; 
	private String gameRules;
	
	public Rules(int MAX_WALLS, int MAX_PLAYERS, String[] playerColours, String[] playerNames, ArrayList<String> botPlayerNames, String gameRules){
		this.MAX_WALLS = MAX_WALLS;
		this.MAX_PLAYERS = MAX_PLAYERS;
		this.playerColours = playerColours;
		this.playerNames = playerNames;
		this.botPlayerNames = botPlayerNames;
		this.gameRules = gameRules;
	}

	public ArrayList<String> getBotPlayerNames() {
		return botPlayerNames;
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
