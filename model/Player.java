package model;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Player.java - a class to describe the properties and functionality of a Player object
 */
public class Player {
	/** defines the maximum amount of walls the Player is allowed to place */
	private final int MAX_WALL_LIMIT;
	/** defines the current amount of walls the Player has placed */
	private ArrayList<Wall> wallsPlaced;

	/** defines the Pawn object which represents the Player's piece */
	private Pawn pawn;
	
	/**
	 * constructs the player object
	 * @param MAX_WALL_LIMIT - sets the MAX_WALL_LIMIT variable
	 * @param colour - sets the Player's Pawn objects colour
	 */
	public Player(int MAX_WALL_LIMIT, String colour){
		this.MAX_WALL_LIMIT = MAX_WALL_LIMIT;
		wallsPlaced = new ArrayList<Wall>();
		pawn = new Pawn(colour);
	}
	
	/**
	 * @return the coordinate of the Player's Pawn object
	 */
	public Coordinate getPawnLocation(){
		return pawn.getPosition();
	}
	
	/**
	 * updates the Player's current walls placed
	 * @return boolean to state if Player has placed all their walls or not
	 */
	public Boolean placeWall(Coordinate coord, boolean isHorizontal){
		//checks to see if a Player has placed as many walls as they have available
		if (wallsPlaced.size() < MAX_WALL_LIMIT){
			wallsPlaced.add(new Wall(coord, isHorizontal));
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * updates the current position of the Player's Pawn object
	 */
	public void movePawn(Coordinate position){
		pawn.setPosition(position);
	}
	
	/**
	 * @return the Player's Pawn object
	 */
	public Pawn getPawn(){
		return pawn;
	}

	public ArrayList<Wall> getWallsPlaced() {
		return wallsPlaced;
	}

}
