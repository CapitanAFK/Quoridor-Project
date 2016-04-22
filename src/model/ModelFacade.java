package model;

public class ModelFacade {
	private Board board;
	private Player[] players;
	
	public ModelFacade(){
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}	
}
