package Model;

public class ModelFacade {
	private Board board;
	private Player[] players;
	private Wall[] walls;
	
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

	public Wall[] getWalls() {
		return walls;
	}

	public void setWalls(Wall[] walls) {
		this.walls = walls;
	}	
}
