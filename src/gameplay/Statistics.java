package gameplay;


public class Statistics {
	private int[] wallsPlaced;
	private int[] stepsTaken;
	private int[] undosTaken;
	private int[] wallsRemoved;
	private int winnerId;
	
	public Statistics(int players){
		wallsPlaced = new int[players];
		stepsTaken = new int[players];
		undosTaken = new int[players];
		wallsRemoved = new int[players];
	}
	
	public void incrementWallsPlaced(int player){
		wallsPlaced[player]++;
	}
	
	public void incrementStepsTaken(int player){
		stepsTaken[player]++;
	}
	
	public void incrementUndosTaken(int player){
		undosTaken[player]++;
	}
	
	public void incrementWallsRemoved(int player){
		wallsRemoved[player]++;
	}
	
	public void setWinner(int playerID){
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

	public int getWinnerId() {
		return winnerId;
	}

	
}
