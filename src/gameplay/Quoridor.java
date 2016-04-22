package gameplay;

import gui.Language;
import gui.GameView.GameState;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Random;
import java.util.ResourceBundle;

import model.*;
import model.Board.BoardLocation;


public class Quoridor {
	private ModelFacade models;
	private Rules rules;
	private int playersTurn;
	private Integer[][] grid = new Integer[17][17];
	private Language currentLanguage;
	private ResourceBundle messages;
	
	public Quoridor(Rules rules){
		this.rules = rules;
		models = new ModelFacade();
		currentLanguage = new Language();
		messages = currentLanguage.getMessages();
	}
	
	public void beginGame(){
		initialisePlayers();
		models.setBoard(new Board());
		models.getBoard().initialiseBoard();
		determineWhoStarts();
	}
	
	public Player[] getPlayers(){
		return models.getPlayers();
	}
	
	public Board getBoard(){
		return models.getBoard();
	}
	
	public Rules getRules(){
		return rules;
	}
	
	public int getPlayersTurn(){
		return playersTurn;
	}
	
	public void endPlayersTurn(){
		playersTurn++;
		if (playersTurn > rules.MAX_PLAYERS-1){
			playersTurn = 0;
		}
	}
	
	public int getPlayersWallsLeft(){
		return getPlayers()[playersTurn].getWallsLeft();
	}
	
	/**
	 * randomly decides which of the players will start the game
	 */
	public void determineWhoStarts(){
		Random random = new Random();
		playersTurn = random.nextInt(rules.MAX_PLAYERS);
	}
	
	/**
	 * creates the Player objects in the players array with their specified colours
	 */
	public void initialisePlayers(){
		String[] playerColours = rules.getPlayerColours();
		models.setPlayers(new Player[rules.MAX_PLAYERS]);
		if (rules.getGameRules() == messages.getString("normal_rules")){
			for (int i = 0; i < rules.MAX_PLAYERS; i++) {
				models.getPlayers()[i] = new Player(rules.MAX_WALLS, playerColours[i]);
				switch (i){
				case 0: models.getPlayers()[i].getPawn().setPosition(new Coordinate(8,16));
				break;
				case 1: models.getPlayers()[i].getPawn().setPosition(new Coordinate(8,0));
				break;
				case 2: models.getPlayers()[i].getPawn().setPosition(new Coordinate(0,8));
				break;
				case 3: models.getPlayers()[i].getPawn().setPosition(new Coordinate(16,8));
				break;
				}
			}
		} else {
			for (int i = 0; i < rules.MAX_PLAYERS; i++) {
				models.getPlayers()[i] = new Player(rules.MAX_WALLS, playerColours[i]);
				switch (i){
				case 0: models.getPlayers()[i].getPawn().setPosition(new Coordinate(16,16));
				break;
				case 1: models.getPlayers()[i].getPawn().setPosition(new Coordinate(0,0));
				break;
				case 2: models.getPlayers()[i].getPawn().setPosition(new Coordinate(0,16));
				break;
				case 3: models.getPlayers()[i].getPawn().setPosition(new Coordinate(16,0));
				break;
				}
			}
		}
	}
	
	/**
	 * moves the specified Player's Pawn to the specified BoardLocation
	 * updates any board locations which may be relevant
	 * @param player - the integer which refers to which Player is being affected
	 * @param BoardLocation - the Coordinate location the Player's Pawn to be moved to on the 2-D BoardLocation array
	 */
	public boolean movePlayersPawn(Coordinate boardCoord){
		//checks if players pawn had previous position to update board
		if (models.getPlayers()[playersTurn].getPawnLocation() != null){
			models.getBoard().setBoardLocation(models.getPlayers()[playersTurn].getPawnLocation(), BoardLocation.FREE_SQUARE);
		}
		//adds players pawn to new location
		models.getBoard().setBoardLocation(boardCoord, BoardLocation.USED_SQUARE);
		//moves player coordinates
		models.getPlayers()[playersTurn].movePawn(boardCoord);
		return true;
	}
	
	public ArrayList<Coordinate> getValidMoves(){
		ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
		Coordinate playerPos = getPlayers()[getPlayersTurn()].getPawnLocation();
		validMoves.addAll(getDirectionalValidMoves(playerPos, "north"));
		validMoves.addAll(getDirectionalValidMoves(playerPos, "east"));
		validMoves.addAll(getDirectionalValidMoves(playerPos, "south"));
		validMoves.addAll(getDirectionalValidMoves(playerPos, "west"));
		return validMoves;
	}
	
	public boolean checkClosedCircuit(Coordinate wallCoord, boolean isHorizontal){
		Deque<Coordinate> stack = new ArrayDeque<Coordinate>(); 
		Coordinate pos = new Coordinate(0, 0);
		int sucesses = 0;
		boolean done = false;
		boolean failed = false;
		for (int i = 0; i < rules.MAX_PLAYERS; i++) {
			updateGrid(wallCoord, isHorizontal);
			stack = new ArrayDeque<Coordinate>(); 
			stack.push(getPlayers()[i].getPawnLocation());
			done = false;
			failed = false;
			while (done == false && failed == false){
				if (stack.size() > 0){
					pos = stack.pop();
				} else if (stack.size() == 0) {
					failed = true;
				}
				if (failed == false){
					if (grid[pos.getX()][pos.getY()] == 0){
						grid[pos.getX()][pos.getY()] = 1;
					} else if (grid[pos.getX()][pos.getY()] == 2){
						grid[pos.getX()][pos.getY()] = 3;
					}
					if (checkFinish(i, pos) == true){
						done = true;
						sucesses++;
					} else {
						if (validCoord(pos.getWall("north"))) {
							stack.push(pos.getWall("north"));
						}
						if (validCoord(pos.getWall("east"))) {
							stack.push(pos.getWall("east"));
						}
						if (validCoord(pos.getWall("south"))) {
							stack.push(pos.getWall("south"));
						}
						if (validCoord(pos.getWall("west"))) {
							stack.push(pos.getWall("west"));
						}
					}
				}
			}
		}
		if (sucesses == rules.MAX_PLAYERS){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean validCoord(Coordinate coord){
		if (inBounds(coord) == true){
			if (grid[coord.getX()][coord.getY()] != 1 &&
				grid[coord.getX()][coord.getY()] != 3 &&
				grid[coord.getX()][coord.getY()] != 4 &&
				grid[coord.getX()][coord.getY()] != 5){
				return true;
			} else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	public void updateGrid(Coordinate wallCoord, boolean isHorizontal){
		for (int x = 0; x < 17; x++) {
			for (int y = 0; y < 17; y++) {
				BoardLocation boardLoc = getBoard().getBoardLocation(new Coordinate(x,y));
				if (boardLoc == BoardLocation.FREE_GAP){
					grid[x][y] = 2;
				} else if (boardLoc == BoardLocation.USED_GAP){
					grid[x][y] = 4;
				} else if (boardLoc == BoardLocation.FREE_WALLGAP ||
							boardLoc == BoardLocation.USED_WALLGAP){
					grid[x][y] = 5;
				} else {
					grid[x][y] = 0;
				}
			}
		}
		grid[wallCoord.getX()][wallCoord.getY()] = 5;
		if (isHorizontal == true){
			grid[wallCoord.getWall("east").getX()][wallCoord.getY()] = 4;
			grid[wallCoord.getWall("west").getX()][wallCoord.getY()] = 4;
		} else {
			grid[wallCoord.getX()][wallCoord.getWall("north").getY()] = 4;
			grid[wallCoord.getX()][wallCoord.getWall("south").getY()] = 4;
		}
	}
	
	public String gridToString(){
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < 17; x++) {
			sb.append("[");
			for (int y = 0; y < 17; y++) {
				sb.append(grid[x][y].toString()+" ");
				if (y == 16){
					sb.append("] \n");
				}
			}
		}
		return sb.toString();
	}
	
	public boolean checkFinish(int playersTurn, Coordinate coord){
		boolean result = false;
		if (rules.getGameRules() == messages.getString("normal_rules")){
		switch (playersTurn){
		case 0:
			if (coord.getY() == 0){
				result = true;
			}
		break;
		case 1:
			if (coord.getY() == 16){
				result = true;
			}
		break;
		case 2:
			if (coord.getX() == 16){
				result = true;
			}
		break;
		case 3:
			if (coord.getX() == 0){
				result = true;
			}
		break;
		}
		} else {
			switch (playersTurn){
			case 0:
				if (coord.compare(new Coordinate(0,0))){
					result = true;
				}
			break;
			case 1:
				if (coord.compare(new Coordinate(16,16))){
					result = true;
				}
			break;
			case 2:
				if (coord.compare(new Coordinate(16,0))){
					result = true;
				}
			break;
			case 3:
				if (coord.compare(new Coordinate(0,16))){
					result = true;
				}
			break;
			}
		}
		return result;
	}
	
	public ArrayList<Coordinate> getDirectionalValidMoves(Coordinate playerPos, String direction){
		ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
		if (inBounds(playerPos.getSquare(direction))){
			
			if (checkFreeSquare(playerPos.getSquare(direction)) &&
				checkFreeGap(playerPos.getWall(direction))){
				
				validMoves.add(playerPos.getSquare(direction));
				
			} else if (inBounds(playerPos.getSquare(direction).getSquare(direction))){
				
				if (checkFreeGap((playerPos.getWall(direction)))&&
					checkFreeSquare(playerPos.getSquare(direction).getSquare(direction)) && 
					checkFreeGap(playerPos.getSquare(direction).getWall(direction))){
					
					validMoves.add(playerPos.getSquare(direction).getSquare(direction));
					
				} else if ( checkFreeGap(playerPos.getWall(direction)) &&
							checkFreeSquare(playerPos.getSquare(direction).getSquare(direction)) && 
							!checkFreeGap(playerPos.getSquare(direction).getWall(direction))){
					
					if (direction == "north" || direction == "south"){
						if (inBounds(playerPos.getSquare(direction).getSquare("east"))){
							if (checkFreeSquare(playerPos.getSquare(direction).getSquare("east"))&&
								checkFreeGap(playerPos.getSquare(direction).getWall("east"))){
								validMoves.add(playerPos.getSquare(direction).getSquare("east"));
							}
						}
						if (inBounds(playerPos.getSquare(direction).getSquare("west"))){
							if (checkFreeSquare(playerPos.getSquare(direction).getSquare("west"))&&
								checkFreeGap(playerPos.getSquare(direction).getWall("west"))){
								validMoves.add(playerPos.getSquare(direction).getSquare("west"));
							}
						}
					} else {
						if (inBounds(playerPos.getSquare(direction).getSquare("north"))){
							if (checkFreeSquare(playerPos.getSquare(direction).getSquare("north")) &&
								checkFreeGap(playerPos.getSquare(direction).getWall("north"))){
								validMoves.add(playerPos.getSquare(direction).getSquare("north"));
							}
						}
						if (inBounds(playerPos.getSquare(direction).getSquare("south"))){
							if (checkFreeSquare(playerPos.getSquare(direction).getSquare("south")) &&
								checkFreeGap(playerPos.getSquare(direction).getWall("south"))){
								validMoves.add(playerPos.getSquare(direction).getSquare("south"));
							}
						}
					}
				}
			}
			
		}
		return validMoves;
	}
	
	public boolean checkFreeSquare(Coordinate coord){
		return models.getBoard().getBoardLocation(coord) == BoardLocation.FREE_SQUARE;
	}
	
	public boolean checkFreeGap(Coordinate coord){
		return models.getBoard().getBoardLocation(coord) == BoardLocation.FREE_GAP;
	}
	
	public boolean checkFreeWallGap(Coordinate coord, boolean isHorizontal){
		boolean isFree = false;
		if (isHorizontal == true){
			if (models.getBoard().getBoardLocation(coord) == BoardLocation.FREE_WALLGAP &&
				checkFreeGap(coord.getWall("east")) &&
				checkFreeGap(coord.getWall("west"))){
				isFree = true;
			}
		} else {
			if (models.getBoard().getBoardLocation(coord) == BoardLocation.FREE_WALLGAP &&
				checkFreeGap(coord.getWall("north")) &&
				checkFreeGap(coord.getWall("south"))){
				isFree = true;
			}
		}
		return isFree;
	}
	
	public boolean inBounds(Coordinate coord){
		boolean inBounds = true;
		if (coord.getX() < 0 || coord.getX() > 16){
			inBounds = false;
		}
		if (coord.getY() < 0 || coord.getY() > 16){
			inBounds = false;
		}
		return inBounds;
	}	
	
	/**
	 * adds the specified Player's Wall to the specified BoardLocation
	 * updates any board locations which may be relevant
	 * @param player - the integer which refers to which Player is being affected
	 * @param boardCoord - the Coordinate location the Player's Wall is to be placed on the 2-D BoardLocation array
	 * @param isHorizontal - determines whether Wall to be placed is horizontal or vertical
	 */
	public String addPlayerWall(int playersTurn, Coordinate boardCoord, boolean isHorizontal){
		if (checkClosedCircuit(boardCoord, isHorizontal) == true){
			//checks if board location is a WallGap
			if (models.getBoard().getBoardLocation(boardCoord) == BoardLocation.FREE_WALLGAP){
				//checks if Wall fits in BoardLocations and if WallGap is available
				if (models.getBoard().checkWallFits(boardCoord, isHorizontal) == true){
					//updates players walls placed
					if (models.getPlayers()[playersTurn].placeWall(boardCoord, isHorizontal) == false){
						return "Player Has Used All Their Walls!";
					}
					//updates available gaps on board and adds wall to WallGap
					models.getBoard().updateGapAvailabilty(boardCoord, isHorizontal);
					return "Wall Placed";
				} else {
					return "No Room For Wall";
				}
				
			}
		}
		return "Invalid Move";
	}
	
	public String removePlayerWall(Coordinate boardCoord, boolean isUndo){
		boolean success = false;
		boolean isHorizontal = true;
		int i = 0;
		int playerNumber = 0;
		for (Player player : getPlayers()) {
			for (Wall wall : player.getWallsPlaced()) {
				if (wall.getPosition().compare(boardCoord)){
					isHorizontal = wall.isHorizontal();
					if (isUndo == false){
						if (playersTurn != i){
							player.getWallsPlaced().remove(wall);
							success = true;
							playerNumber = i;
						}
					} else {
						player.getWallsPlaced().remove(wall);
						success = true;
					}
					break;
				}
			}
			i++;
		}
		if (success == true){
			getBoard().setBoardLocation(boardCoord, BoardLocation.FREE_WALLGAP);
			if (isHorizontal == true){
				getBoard().setBoardLocation(boardCoord.getWall("east"), BoardLocation.FREE_GAP);
				getBoard().setBoardLocation(boardCoord.getWall("west"), BoardLocation.FREE_GAP);
			} else {
				getBoard().setBoardLocation(boardCoord.getWall("north"), BoardLocation.FREE_GAP);
				getBoard().setBoardLocation(boardCoord.getWall("south"), BoardLocation.FREE_GAP);
			}
			if (isHorizontal){
				return "p"+playerNumber+" t"+" Wall Removed";
			} else {
				return "p"+playerNumber+" f"+" Wall Removed";
			}
		} else {
			return "Failed";
		}
	}
}
