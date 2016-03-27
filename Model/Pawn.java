package Model;

import java.awt.Color;

/**
 * Pawn.java - a simple class to describe the properties of a Pawn object
 */
public class Pawn {
	/** defines Coordinate location of the Pawn object on the game board */
	private Coordinate position;
	/** defines the colour of the Pawn object to be displayed */
	private Color pawnColour;
	
	/**
	 * constructs Pawn object
	 * @param colour - the colour of the Pawn object to be displayed
	 */
	public Pawn(Color colour){
		this.position = null;
		this.pawnColour = colour; 
	}
	
	
	/**
	 * @return the colour of the Pawn object to be displayed
	 */
	public Color getColour(){
		return pawnColour;
	}

	/**
	 * @return the Coordinate location of the Wall
	 */
	public Coordinate getPosition() {
		return position;
	}

	/**
	 * sets the position of the Pawn object
	 * @param position - the position of the Pawn object to be set
	 */
	public void setPosition(Coordinate position) {
		this.position = position;
	}
}
