package gameplay;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Controls {
	
	//Class that interacts with the in game objects like moving pawns and placing walls
	//OPTIONAL allow the user to change keyBindings
	public String moveLeft = "a";
	public String moveRight = "d";
	public String moveUp = "w";
	public String moveDown = "s";
	public String horizontalWall = "h";
	public String verticalWall = "v";
	public String undo = "z";
	public String endTurn = "m";
	
	public Controls(){
		setKeys("textFiles/controls/left.txt", "left");
		setKeys("textFiles/controls/right.txt", "right");
		setKeys("textFiles/controls/up.txt", "up");
		setKeys("textFiles/controls/down.txt", "down");
		setKeys("textFiles/controls/hor.txt", "hor");
		setKeys("textFiles/controls/ver.txt", "ver");
		setKeys("textFiles/controls/undo.txt", "undo");
		setKeys("textFiles/controls/endTurn.txt", "endTurn");
	}
	
	public void setKeys(String file, String key){
		// This will reference one line at a time
		String line = null;
		
		// Left
		try {
			// Read the file
			FileReader fr = new FileReader(file);
			
			// Wrap FileReader in BufferedReader
			BufferedReader br = new BufferedReader(fr);	
			
			if((line = br.readLine()) != null){
				if(key == "left"){
					moveLeft = line;
				} else if(key == "right"){
					moveRight = line;
				} else if(key == "up"){
					moveUp = line;
				} else if(key == "down"){
					moveDown = line;
				} else if(key == "hor"){
					horizontalWall = line;
				} else if(key == "ver"){
					verticalWall = line;
				} else if(key == "undo"){
					undo = line;
				} else if(key == "endTurn"){
					endTurn = line;
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
}