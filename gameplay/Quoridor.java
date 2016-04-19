package gameplay;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import model.*;
import model.Board.BoardLocation;


public class Quoridor {
	private ModelFacade models;
	private Rules rules;
	private int playersTurn;
	
	public Quoridor(Rules rules){
		this.rules = rules;
		models = new ModelFacade();
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
		if (inBounds(playerPos.getNorthSquare()) == true){
			if (models.getBoard().getBoardLocation(playerPos.getNorthSquare()) == BoardLocation.FREE_SQUARE &&
				models.getBoard().getBoardLocation(playerPos.getNorthWall()) == BoardLocation.FREE_GAP){
				validMoves.add(playerPos.getNorthSquare());
			} else if (inBounds(playerPos.getNorthSquare().getNorthSquare()) == true){
				if (models.getBoard().getBoardLocation(playerPos.getNorthWall()) == BoardLocation.FREE_GAP &&
					models.getBoard().getBoardLocation(playerPos.getNorthSquare().getNorthSquare()) == BoardLocation.FREE_SQUARE && 
					models.getBoard().getBoardLocation(playerPos.getNorthSquare().getNorthWall()) == BoardLocation.FREE_GAP){
					validMoves.add(playerPos.getNorthSquare().getNorthSquare());
				} else if (models.getBoard().getBoardLocation(playerPos.getNorthWall()) == BoardLocation.FREE_GAP &&
							models.getBoard().getBoardLocation(playerPos.getNorthSquare().getNorthSquare()) == BoardLocation.FREE_SQUARE && 
							models.getBoard().getBoardLocation(playerPos.getNorthSquare().getNorthWall()) == BoardLocation.USED_GAP){
					validMoves.add(playerPos.getNorthSquare().getEastSquare());
					validMoves.add(playerPos.getNorthSquare().getWestSquare());
				}
			}
		}
		if (inBounds(playerPos.getEastSquare()) == true){
			if (models.getBoard().getBoardLocation(playerPos.getEastSquare()) == BoardLocation.FREE_SQUARE &&
				models.getBoard().getBoardLocation(playerPos.getEastWall()) == BoardLocation.FREE_GAP){
				validMoves.add(playerPos.getEastSquare());
			} else if (inBounds(playerPos.getEastSquare().getEastSquare()) == true){
				if (models.getBoard().getBoardLocation(playerPos.getEastWall()) == BoardLocation.FREE_GAP &&
					models.getBoard().getBoardLocation(playerPos.getEastSquare().getEastSquare()) == BoardLocation.FREE_SQUARE && 
					models.getBoard().getBoardLocation(playerPos.getEastSquare().getEastWall()) == BoardLocation.FREE_GAP){
					validMoves.add(playerPos.getEastSquare().getEastSquare());
				} else if (models.getBoard().getBoardLocation(playerPos.getEastWall()) == BoardLocation.FREE_GAP &&
							models.getBoard().getBoardLocation(playerPos.getEastSquare().getEastSquare()) == BoardLocation.FREE_SQUARE && 
							models.getBoard().getBoardLocation(playerPos.getEastSquare().getEastWall()) == BoardLocation.USED_GAP){
					validMoves.add(playerPos.getEastSquare().getNorthSquare());
					validMoves.add(playerPos.getEastSquare().getSouthSquare());
				}
			}
		}
		if (inBounds(playerPos.getSouthSquare()) == true){
			if (models.getBoard().getBoardLocation(playerPos.getSouthSquare()) == BoardLocation.FREE_SQUARE &&
				models.getBoard().getBoardLocation(playerPos.getSouthWall()) == BoardLocation.FREE_GAP){
				validMoves.add(playerPos.getSouthSquare());
			}else if (inBounds(playerPos.getSouthSquare().getSouthSquare()) == true){
				if (models.getBoard().getBoardLocation(playerPos.getSouthWall()) == BoardLocation.FREE_GAP &&
					models.getBoard().getBoardLocation(playerPos.getSouthSquare().getSouthSquare()) == BoardLocation.FREE_SQUARE && 
					models.getBoard().getBoardLocation(playerPos.getSouthSquare().getSouthWall()) == BoardLocation.FREE_GAP){
					validMoves.add(playerPos.getSouthSquare().getSouthSquare());
				} else if (models.getBoard().getBoardLocation(playerPos.getSouthWall()) == BoardLocation.FREE_GAP &&
							models.getBoard().getBoardLocation(playerPos.getSouthSquare().getSouthSquare()) == BoardLocation.FREE_SQUARE && 
							models.getBoard().getBoardLocation(playerPos.getSouthSquare().getSouthWall()) == BoardLocation.USED_GAP){
					validMoves.add(playerPos.getSouthSquare().getEastSquare());
					validMoves.add(playerPos.getSouthSquare().getWestSquare());
				}
			}
		}
		if (inBounds(playerPos.getWestSquare()) == true){
			if (models.getBoard().getBoardLocation(playerPos.getWestSquare()) == BoardLocation.FREE_SQUARE &&
				models.getBoard().getBoardLocation(playerPos.getWestWall()) == BoardLocation.FREE_GAP){
				validMoves.add(playerPos.getWestSquare());
			}else if (inBounds(playerPos.getWestSquare().getWestSquare()) == true){
				if (models.getBoard().getBoardLocation(playerPos.getWestWall()) == BoardLocation.FREE_GAP &&
					models.getBoard().getBoardLocation(playerPos.getWestSquare().getWestSquare()) == BoardLocation.FREE_SQUARE && 
					models.getBoard().getBoardLocation(playerPos.getWestSquare().getWestWall()) == BoardLocation.FREE_GAP){
					validMoves.add(playerPos.getWestSquare().getWestSquare());
				} else if (models.getBoard().getBoardLocation(playerPos.getWestWall()) == BoardLocation.FREE_GAP &&
							models.getBoard().getBoardLocation(playerPos.getWestSquare().getWestSquare()) == BoardLocation.FREE_SQUARE && 
							models.getBoard().getBoardLocation(playerPos.getWestSquare().getWestWall()) == BoardLocation.USED_GAP){
					validMoves.add(playerPos.getWestSquare().getNorthSquare());
					validMoves.add(playerPos.getWestSquare().getSouthSquare());
				}
			}
		}
		return validMoves;
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
	public String addPlayerWall(Coordinate boardCoord, boolean isHorizontal){
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
			
		} else {
			return "Invalid Move";
		}
	}
}
