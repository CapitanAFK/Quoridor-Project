package GUI;
/**
 * This class sets user controls
 */
public class Controls {
	private String up;
	private String down;
	private String left;
	private String right;
	private String vertical;
	private String horizontal;
	
	public Controls() {
		up = "w";
		down = "s";
		left = "a";
		right = "d";
		vertical = "v";
		horizontal = "h";
	}
	
	public void setUp(String character) {
		up = character;
	}
	
	public void setDown(String character) {
		down = character;
	}
	
	public void setLeft(String character) {
		left = character;
	}
	
	public void setRight(String character) {
		right = character;
	}
	
	public void setVertical(String character) {
		vertical = character;
	}
	
	public void setHorizontal(String character) {
		horizontal = character;
	}
	
	public String getUp() {
		return up;
	}
	
	public String getDown() {
		return down;
	}
	
	public String getLeft() {
		return left;
	}
	
	public String getRight() {
		return right;
	}
	
	public String getVertical() {
		return vertical;
	}
	
	public String getHorizontal() {
		return horizontal;
	}
}
