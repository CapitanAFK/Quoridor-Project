package model;

/**
 * ModelFacade.java - A facade which passes objects to and from the models.
 */
public class ModelFacade {
	/** The board model being used for the game */
	private Board board;
	/** The array of players which will be used for the game*/
	private Player[] players;
	
	/**
	 * Constructs the ModelFacade object
	 */
	public ModelFacade(){
	}
	
	/**
	 * @return the Board model
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Sets the Board model
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * @return the array of player models in the game
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * Sets the array of players models
	 * @param players - the array of Player object to replace the current array
	 */
	public void setPlayers(Player[] players) {
		this.players = players;
	}	
}
