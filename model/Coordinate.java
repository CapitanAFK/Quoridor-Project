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
	
	public Coordinate getNorthWall(){
		return new Coordinate(x,y-1);
	}
	
	public Coordinate getEastWall(){
		return new Coordinate(x+1,y);
	}
	
	public Coordinate getSouthWall(){
		return new Coordinate(x,y+1);
	}
	
	public Coordinate getWestWall(){
		return new Coordinate(x-1,y);
	}
	
	public Coordinate getNorthSquare(){
		return new Coordinate(x,y-2);
	}
	
	public Coordinate getEastSquare(){
		return new Coordinate(x+2,y);
	}
	
	public Coordinate getSouthSquare(){
		return new Coordinate(x,y+2);
	}
	
	public Coordinate getWestSquare(){
		return new Coordinate(x-2,y);
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
