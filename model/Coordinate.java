package model;

/**
 * Coordinate.java - a simple class to define the properties for a Coordinate object
 */
public class Coordinate {
	/** defines the x location of the coordinate */
	private int x;
	/** defines the y location of the coordinate */
	private int y;
	
	/**
	 * constructs Coordinate object
	 * @param x - the x variable of the coordinate
	 * @param y - the y variable of the coordinate
	 */
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x variable of the Coordinate
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * sets the x variable of the location
	 * @param x - the x variable of the coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y variable of the Coordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * sets the y variable of the location
	 * @param y - the y variable of the coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	public Coordinate getWall(String direction){
		switch (direction){
		case "north":return new Coordinate(x,y-1);
		case "east":return new Coordinate(x+1,y);
		case "south":return new Coordinate(x,y+1);
		case "west":return new Coordinate(x-1,y);
		}
		return null;
	}
	
	public Coordinate getSquare(String direction){
		switch (direction){
		case "north":return new Coordinate(x,y-2);
		case "east":return new Coordinate(x+2,y);
		case "south":return new Coordinate(x,y+2);
		case "west":return new Coordinate(x-2,y);
		}
		return null;
	}
	
	public String toString(){
		return x+","+y+" ";
	}
	
	public boolean compare(Coordinate coord){
		boolean equal = false;
		if (coord.getX() == x && coord.getY() == y){
			equal = true;
		}
		return equal;
	}


}
