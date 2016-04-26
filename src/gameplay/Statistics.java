package gameplay;


public class Statistics {
	private int[] wallsPlaced;
	private int[] stepsTaken;
	private int[] undosTaken;
	private int[] wallsRemoved;
	private boolean[] isBot;
	private String[] playerNames;
	private String winnerId;
	
	public Statistics(Rules rules){
		int players = rules.MAX_PLAYERS;
		wallsPlaced = new int[players];
		stepsTaken = new int[players];
		undosTaken = new int[players];
		wallsRemoved = new int[players];
		isBot = new boolean[players];
		playerNames = rules.getPlayerNames();
		for (int i = 0; i < players; i++) {
			InnerLoop:
			for (int j = 0; j < rules.getBotPlayerNames().size(); j++) {
				if (rules.getBotPlayerNames().get(j).equals(playerNames[i])){
					isBot[i] = true;
				} else {
					isBot[i] = false;
				}
				break InnerLoop;
			}
		}
	}
	
	public void incrementWallsPlaced(int player){
		wallsPlaced[player]++;
	}
	
	public void decrementWallsPlaced(int player){
		wallsPlaced[player]--;
	}
	
	public void incrementStepsTaken(int player){
		stepsTaken[player]++;
	}
	
	public void decrementStepsTaken(int player){
		stepsTaken[player]--;
	}
	
	public void incrementUndosTaken(int player){
		undosTaken[player]++;
	}
	
	public void incrementWallsRemoved(int player){
		wallsRemoved[player]++;
	}
	
	public void decrementWallsRemoved(int player){
		wallsRemoved[player]--;
	}
	
	public void setWinner(String playerID){
		winnerId = playerID;
	}

	public int[] getWallsPlaced() {
		return wallsPlaced;
	}

	public int[] getStepsTaken() {
		return stepsTaken;
	}

	public int[] getUndosTaken() {
		return undosTaken;
	}

	public int[] getWallsRemoved() {
		return wallsRemoved;
	}

	public String getWinnerId() {
		return winnerId;
	}
	
	public String[] getPlayerNames() {
		return playerNames;
	}

	public boolean[] getIsBot() {
		return isBot;
	}	
}
