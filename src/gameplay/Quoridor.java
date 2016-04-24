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
		models.setBoard(new Board());
		models.getBoard().initialiseBoard();
		initialisePlayers();
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

	public boolean checkBotsGo(){
		for (int i = 0; i < rules.getBotPlayerIDs().size(); i++) {
			if (playersTurn == rules.getBotPlayerIDs().get(i)){		
				return true;
			}
		}
		return false;
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
		String[] playerNames = rules.getPlayerNames();
		models.setPlayers(new Player[rules.MAX_PLAYERS]);
		if (rules.getGameRules() == messages.getString("normal_rules")){
			for (int i = 0; i < rules.MAX_PLAYERS; i++) {
				models.getPlayers()[i] = new Player(rules.MAX_WALLS, playerColours[i], playerNames[i]);
				switch (i){
				case 0: movePlayersPawn(i, new Coordinate(8,16));
				break;
				case 1: movePlayersPawn(i, new Coordinate(8,0));
				break;
				case 2: movePlayersPawn(i, new Coordinate(0,8));
				break;
				case 3: movePlayersPawn(i, new Coordinate(16,8));
				break;
				}
			}
		} else {
			for (int i = 0; i < rules.MAX_PLAYERS; i++) {
				models.getPlayers()[i] = new Player(rules.MAX_WALLS, playerColours[i], playerNames[i]);
				switch (i){
				case 0: movePlayersPawn(i, new Coordinate(16,16));
				break;
				case 1: movePlayersPawn(i, new Coordinate(0,0));
				break;
				case 2: movePlayersPawn(i, new Coordinate(0,16));
				break;
				case 3: movePlayersPawn(i, new Coordinate(16,0));
				break;
				}
			}
		}
	}

	public void takeBotsTurn(){
		ArrayList<Coordinate> route = new ArrayList<Coordinate>();
		ArrayList<Coordinate> validMoves = new ArrayList<Coordinate>();
		Random random = new Random();
		int choice = random.nextInt(2);
		if (choice == 1){
			String wallAdded = "Invalid Move";
			int distance = 0;
			int playerToBlock = random.nextInt(rules.MAX_PLAYERS);
			if (playerToBlock == playersTurn){
				playerToBlock++;
			}
			if (playerToBlock >= rules.MAX_PLAYERS){
				playerToBlock = 0;
			}
			route = getFastestPlayerRoute(playerToBlock);
			while (wallAdded != "Wall Placed" && distance != route.size()-1){
				distance++;
				Coordinate newPos = route.get(distance);
				if (route.get(distance-1).getSquare("north").compare(newPos)){
					if (inBounds(route.get(distance-1).getWall("north").getWall("east"))){
						wallAdded = addPlayerWall(playersTurn, route.get(distance-1).getWall("north").getWall("east"), true);
					} else if (inBounds(route.get(distance-1).getWall("north").getWall("west"))){
						wallAdded = addPlayerWall(playersTurn, route.get(distance-1).getWall("north").getWall("west"), true);
					}
					
				} else if (route.get(distance-1).getSquare("south").compare(newPos)){
					if (inBounds(route.get(distance-1).getWall("south").getWall("east"))){
						wallAdded = addPlayerWall(playersTurn, route.get(distance-1).getWall("south").getWall("east"), true);
					} else if (inBounds(route.get(distance-1).getWall("south").getWall("west"))){
						wallAdded = addPlayerWall(playersTurn, route.get(distance-1).getWall("south").getWall("west"), true);
					}
					
				} else if (route.get(distance-1).getSquare("east").compare(newPos)){
					if (inBounds(route.get(distance-1).getWall("east").getWall("north"))){
						wallAdded = addPlayerWall(playersTurn, route.get(distance-1).getWall("east").getWall("north"), false);
					} else if (inBounds(route.get(distance-1).getWall("east").getWall("south"))){
						wallAdded = addPlayerWall(playersTurn, route.get(distance-1).getWall("east").getWall("south"), false);
					}
				} else if (route.get(distance-1).getSquare("west").compare(newPos)){
					if (inBounds(route.get(distance-1).getWall("west").getWall("north"))){
						wallAdded = addPlayerWall(playersTurn, route.get(distance-1).getWall("west").getWall("north"), false);
					} else if (inBounds(route.get(distance-1).getWall("west").getWall("south"))){
						wallAdded = addPlayerWall(playersTurn, route.get(distance-1).getWall("west").getWall("south"), false);
					}
				}
			}
			if (wallAdded != "Wall Placed"){
				choice = 0;
			}
		}
		if (choice == 0){
			route = getFastestPlayerRoute(playersTurn);
			validMoves = getValidMoves();
			for (int i = 0; i < route.size(); i++) {
				Coordinate newPos = route.get(i);
				for (int j = 0; j < validMoves.size(); j++) {
					if (newPos.compare(validMoves.get(j))){
						movePlayersPawn(getPlayersTurn(), newPos);
					}
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
	public boolean movePlayersPawn(int player, Coordinate boardCoord){
		//checks if players pawn had previous position to update board
		if (models.getPlayers()[player].getPawnLocation() != null){
			models.getBoard().setBoardLocation(models.getPlayers()[player].getPawnLocation(), BoardLocation.FREE_SQUARE);
		}
		//adds players pawn to new location
		models.getBoard().setBoardLocation(boardCoord, BoardLocation.USED_SQUARE);
		//moves player coordinates
		models.getPlayers()[player].movePawn(boardCoord);
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
		int sucesses = 0;
		for (int i = 0; i < rules.MAX_PLAYERS; i++) {
			updateGridWithWall(wallCoord, isHorizontal);
			stack = checkPlayerRoute(i);
			if (stack.size() > 0){
				sucesses++;
			}
		}
		if (sucesses == rules.MAX_PLAYERS){
			return true;
		} else {
			return false;
		}
	}

	public Deque<Coordinate> checkPlayerRoute(int player){
		Deque<Coordinate> stack = new ArrayDeque<Coordinate>(); 
		Coordinate pos = new Coordinate(0, 0);
		boolean done = false;
		boolean failed = false;
		stack = new ArrayDeque<Coordinate>(); 
		stack.push(getPlayers()[player].getPawnLocation());
		done = false;
		failed = false;
		while (done == false && failed == false){
			if (stack.size() > 0){
				pos = stack.pop();
			} else if (stack.size() == 0) {
				failed = true;
			}
			if (failed == false){
				if (getGridValue(pos) == 0){
					grid[pos.getX()][pos.getY()] = 1;
				} else {
					grid[pos.getX()][pos.getY()] = 2;
				}
				if (checkFinish(player, pos) == true){
					done = true;
				} else {
					String[] directionalStrings = getDirectionalStrings(player);
					if (validCoord(pos.getWall(directionalStrings[3]))) {
						stack.push(pos.getWall(directionalStrings[3]));
					}
					if (validCoord(pos.getWall(directionalStrings[2]))) {
						stack.push(pos.getWall(directionalStrings[2]));
					}
					if (validCoord(pos.getWall(directionalStrings[1]))) {
						stack.push(pos.getWall(directionalStrings[1]));
					}
					if (validCoord(pos.getWall(directionalStrings[0]))) {
						stack.push(pos.getWall(directionalStrings[0]));
					}
				}
			}
		}
		return stack;
	}

	public ArrayList<Coordinate> getFastestPlayerRoute(int player){
		ArrayList<ArrayList<Coordinate>> allRoutes = new ArrayList<ArrayList<Coordinate>>();
		for (int i = 0; i < 24; i++) {
			updateGrid();
			ArrayList<Coordinate> testRoute = new ArrayList<Coordinate>();
			Deque<Coordinate> stack = new ArrayDeque<Coordinate>(); 
			Coordinate pos = new Coordinate(0, 0);
			boolean done = false;
			boolean failed = false;
			stack = new ArrayDeque<Coordinate>(); 
			stack.push(getPlayers()[player].getPawnLocation());
			done = false;
			failed = false;
			while (done == false && failed == false){
				if (stack.size() > 0){
					pos = stack.pop();
					if (getGridValue(pos) == 0){
						testRoute.add(pos);
					}
				} else if (stack.size() == 0) {
					failed = true;
				}
				if (failed == false){
					if (getGridValue(pos) == 0){
						grid[pos.getX()][pos.getY()] = 1;
					} else {
						grid[pos.getX()][pos.getY()] = 2;
					}
					if (checkFinish(player, pos) == true){
						done = true;
					} else {
						String[] directionalStrings = getDirectionalStrings(i);
						if (validCoord(pos.getWall(directionalStrings[3]))) {
							stack.push(pos.getWall(directionalStrings[3]));
						}
						if (validCoord(pos.getWall(directionalStrings[2]))) {
							stack.push(pos.getWall(directionalStrings[2]));
						}
						if (validCoord(pos.getWall(directionalStrings[1]))) {
							stack.push(pos.getWall(directionalStrings[1]));
						}
						if (validCoord(pos.getWall(directionalStrings[0]))) {
							stack.push(pos.getWall(directionalStrings[0]));
						}
					}
				}
			}
			allRoutes.add(updateRoute(testRoute));
		}
		
		ArrayList<Coordinate> fastestRoute = allRoutes.get(0);
		for (int i = 0; i < allRoutes.size(); i++) {
			if (allRoutes.get(i).size() < fastestRoute.size() &&
				checkRoutePossible(allRoutes.get(i))){
				fastestRoute = allRoutes.get(i);
			}
		}
		return fastestRoute;
	}

	public ArrayList<Coordinate> updateRoute(ArrayList<Coordinate> route){
		updateGrid();
		ArrayList<Coordinate> newRoute = new ArrayList<Coordinate>();
		for (int x = 0; x < route.size(); x++) {
			newRoute.add(route.get(x));
			for (int y = route.size()-1; y > x+1 ; y--) {
				if ((route.get(x).compare(route.get(y).getSquare("north")) && 
					getGridValue(route.get(y).getWall("north")) == 3) ||
					(route.get(x).compare(route.get(y).getSquare("east")) && 
					getGridValue(route.get(y).getWall("east")) == 3) ||
					(route.get(x).compare(route.get(y).getSquare("south")) && 
					getGridValue(route.get(y).getWall("south")) == 3) ||
					(route.get(x).compare(route.get(y).getSquare("west")) && 
					getGridValue(route.get(y).getWall("west")) == 3)){
					newRoute.add(route.get(y));
					x = y;
				}
			}
		}
		return newRoute;
	}
	
	public boolean checkRoutePossible(ArrayList<Coordinate> route){
		for (int i = 0; i < route.size()-1; i++) {
			if (!route.get(i).getSquare("north").compare(route.get(i+1)) &&
				!route.get(i).getSquare("east").compare(route.get(i+1)) &&
				!route.get(i).getSquare("south").compare(route.get(i+1)) &&
				!route.get(i).getSquare("west").compare(route.get(i+1))){
				return false;
			}
		}
		return true;
	}

	public String[] getDirectionalStrings(int player){
		switch (player){
		case 0: return new String[]{"north","west","east","south"};
		case 1: return new String[]{"north","west","south","east"};
		case 2: return new String[]{"north","east","south","west"};
		case 3: return new String[]{"north","east","west","south"};
		case 4: return new String[]{"north","south","east","west"};
		case 5: return new String[]{"north","south","west","east"};
		
		case 6: return new String[]{"east","west","north","south"};
		case 7: return new String[]{"east","west","south","north"};
		case 8: return new String[]{"east","north","south","west"};
		case 9: return new String[]{"east","north","west","south"};
		case 10: return new String[]{"east","south","north","west"};
		case 11: return new String[]{"east","south","west","north"};
		
		case 12: return new String[]{"south","west","north","east"};
		case 13: return new String[]{"south","west","east","north"};
		case 14: return new String[]{"south","north","east","west"};
		case 15: return new String[]{"south","north","west","east"};
		case 16: return new String[]{"south","east","north","west"};
		case 17: return new String[]{"south","east","west","north"};
		
		case 18: return new String[]{"west","east","north","south"};
		case 19: return new String[]{"west","east","south","north"};
		case 20: return new String[]{"west","north","south","east"};
		case 21: return new String[]{"west","north","east","south"};
		case 22: return new String[]{"west","south","north","east"};
		case 23: return new String[]{"west","south","east","north"};
		default: return null;
		}
	}

	public boolean validCoord(Coordinate coord){
		if (inBounds(coord) == true){
			if (getGridValue(coord) == 0 ||
				getGridValue(coord) == 3){
				return true;
			} else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	public int getGridValue(Coordinate coord){
		return grid[coord.getX()][coord.getY()];
	}

	public void updateGrid(){
		for (int x = 0; x < 17; x++) {
			for (int y = 0; y < 17; y++) {
				BoardLocation boardLoc = getBoard().getBoardLocation(new Coordinate(x,y));
				if (boardLoc == BoardLocation.FREE_WALLGAP ||
					boardLoc == BoardLocation.USED_WALLGAP || 
					boardLoc == BoardLocation.USED_GAP){
					grid[x][y] = 4;
				} else if (boardLoc == BoardLocation.FREE_GAP){
					grid[x][y] = 3;
				} else {
					grid[x][y] = 0;
				}
			}
		}
	}

	public void updateGridWithWall(Coordinate wallCoord, boolean isHorizontal){
		for (int x = 0; x < 17; x++) {
			for (int y = 0; y < 17; y++) {
				BoardLocation boardLoc = getBoard().getBoardLocation(new Coordinate(x,y));
				if (boardLoc == BoardLocation.FREE_WALLGAP ||
					boardLoc == BoardLocation.USED_WALLGAP || 
					boardLoc == BoardLocation.USED_GAP){
					grid[x][y] = 4;
				} else if (boardLoc == BoardLocation.FREE_GAP){
					grid[x][y] = 3;
				} else {
					grid[x][y] = 0;
				}
			}
		}
		grid[wallCoord.getX()][wallCoord.getY()] = 4;
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
				sb.append(grid[y][x].toString()+" ");
				if (y == 16){
					sb.append("] \n");
				}
			}
		}
		sb.append("\n");
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
