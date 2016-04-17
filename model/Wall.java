package model;

/**
 * Wall.java - a simple class to describe the properties of a Wall object
 */
public class Wall {
	/** defines the Coordinate location of the Wall on the game board */
	private Coordinate position;
	/** defines whether the rotation of the wall is horizontal or vertical */
	private boolean isHorizontal;
	/** defines the owner of the wall */
	private int owner;
	
	/**
	 * constructs the Wall object
	 * @param position - defines the Coordinate position of the Wall
	 * @param isHorizontal - defines the rotation of the Wall
	 */
	public Wall(Coordinate position, boolean isHorizontal){
		this.position = position;
		this.isHorizontal = isHorizontal;
	}
	
	/**
	 * @return the Coordinate location of the Wall
	 */
	public Coordinate getPosition() {
		return position;
	}
	
	/**
	 * @return boolean to determine whether wall is horizontal or vertical
	 */
	public boolean isHorizontal() {
		return isHorizontal;
	}

	/**
	 * @return int which references the player number
	 */
	public int getOwner() {
		return owner;
	}

	/**
	 * @param sets int which references the player number
	 */
	public void setOwner(int owner) {
		this.owner = owner;
	}		
}
