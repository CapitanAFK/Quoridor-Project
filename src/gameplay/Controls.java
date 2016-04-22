package gameplay;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Controls {
	
	//Class that interacts with the in game objects like moving pawns and placing walls
	//OPTIONAL allow the user to change keyBindings
	public String moveLeft = "A";
	public String moveRight = "D";
	public String moveUp = "W";
	public String moveDown = "S";
	public String verticalWall = "V";
	public String horizontalWall = "H";
	public String removeWall = "Q";
	public String undo = "Z";
	public String endTurn = "M";
	
	public Controls(){
		getKeysFromFile("textFiles/controls.txt");
	}
	
	public void getKeysFromFile(String file){
		// This will reference one line at a time
		String line = null;
		
		// Left
		try {
			// Read the file
			FileReader fr = new FileReader(file);
			
			// Wrap FileReader in BufferedReader
			BufferedReader br = new BufferedReader(fr);	
			
			int x = 0;
			
			while ((line = br.readLine()) != null ){
				x++;
				switch (x){
				case 1:
					moveLeft = line;
					break;
				case 2:
					moveRight = line;
					break;
				case 3:
					moveUp = line;
					break;
				case 4:
					moveDown = line;
					break;
				case 5:
					verticalWall = line;
					break;
				case 6:
					horizontalWall = line;
					break;
				case 7:
					removeWall = line;
					break;
				case 8:
					undo = line;
					break;
				case 9:
					endTurn = line;
					break;
				}
			}
			
			// Clear the line
			line = null;
			
			// Close file
			br.close();
		}
		
		catch(FileNotFoundException ex) {
			System.out.println("No file found.");
		}
			
		catch(IOException ex){
			System.out.println("Error reading the file.");
		}
		
	}
	
	// Get methods
	public String getLeft(){
		return moveLeft;
	}
	
	public String getRight(){
		return moveRight;
	}
	
	public String getUp(){
		return moveUp;
	}
	
	public String getDown(){
		return moveDown;
	}
	
	public String getHorizontalWall(){
		return horizontalWall;
	}
	
	public String getVerticalWall(){
		return verticalWall;
	}
	
	public String getUndo(){
		return undo;
	}
	
	public String getEndTurn(){
		return endTurn;
	}

	public String getRemoveWall() {
		return removeWall;
	}

	public void setRemoveWall(String removeWall) {
		this.removeWall = removeWall;
	}

	public void setMoveLeft(String moveLeft) {
		this.moveLeft = moveLeft;
	}

	public void setMoveRight(String moveRight) {
		this.moveRight = moveRight;
	}

	public void setMoveUp(String moveUp) {
		this.moveUp = moveUp;
	}

	public void setMoveDown(String moveDown) {
		this.moveDown = moveDown;
	}

	public void setHorizontalWall(String horizontalWall) {
		this.horizontalWall = horizontalWall;
	}

	public void setVerticalWall(String verticalWall) {
		this.verticalWall = verticalWall;
	}

	public void setUndo(String undo) {
		this.undo = undo;
	}

	public void setEndTurn(String endTurn) {
		this.endTurn = endTurn;
	}
}