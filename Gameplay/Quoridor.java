package Gameplay;

import java.awt.Color;
import java.util.Random;

import Model.*;
import Model.Board.BoardLocation;


public class Quoridor {
	private AIOpponent[] computerOpponent;
	private ModelFacade models;
	private Rules rules;
	private Controls controls;
	private int playersTurn;
	
	public Quoridor(Controls controls, Rules rules){
		this.controls = controls;
		this.rules = rules;
		initialisePlayers(rules.getPlayerColours());
		models.getBoard().initialiseBoard();
		models.setWalls(new Wall[rules.MAX_WALLS*rules.MAX_PLAYERS]);
	}
	
	/**
	 * randomly decides which of the players will start the game
	 */
	public void determineWhoStarts(){
		Random random = new Random();
		playersTurn = random.nextInt();
	}
	
	/**
	 * creates the Player objects in the players array with their specified colours
	 * @param playerColours - the colours of the Player's Pawn Objects
	 */
	public void initialisePlayers(Color[] playerColours){
		models.setPlayers(new Player[rules.MAX_PLAYERS]);
		for (int i = 0; i < rules.MAX_PLAYERS; i++) {
			models.getPlayers()[i] = new Player(rules.MAX_WALLS, playerColours[i]);
		}
	}
	
	/**
	 * moves the specified Player's Pawn to the specified BoardLocation
	 * updates any board locations which may be relevant
	 * @param player - the integer which refers to which Player is being affected
	 * @param BoardLocation - the Coordinate location the Player's Pawn to be moved to on the 2-D BoardLocation array
	 * @throws if Pawn is placed on any type of BoardLocation which isn't an instance of a Square object
	 */
	public void movePlayersPawn(Board board, Player player, Coordinate boardCoord) throws Exception{
		//checks if board location is a square
		if (board.getBoardLocation(boardCoord) == BoardLocation.FREE_SQUARE){
			//checks if players pawn had previous position to update board
			if (player.getPawnLocation() != null){
				board.setBoardLocation(player.getPawnLocation(), BoardLocation.FREE_SQUARE);
			}
			//adds players pawn to new location
			board.setBoardLocation(boardCoord, BoardLocation.USED_SQUARE);
			//moves player coordinates
			player.movePawn(boardCoord);
		} else {
			throw new Exception("Pawns can only be placed on Squares!");
		}
	}
	
	
	/**
	 * adds the specified Player's Wall to the specified BoardLocation
	 * updates any board locations which may be relevant
	 * @param player - the integer which refers to which Player is being affected
	 * @param boardCoord - the Coordinate location the Player's Wall is to be placed on the 2-D BoardLocation array
	 * @param isHorizontal - determines whether Wall to be placed is horizontal or vertical
	 * @throws if Wall is placed on any type of BoardLocation which isn't an instance of a WallGap object
	 */
	public String addPlayerWall(Board board, Player player, Coordinate boardCoord, boolean isHorizontal) throws Exception{
		//checks if board location is a WallGap
		if (board.getBoardLocation(boardCoord) == BoardLocation.FREE_WALLGAP){
			//checks if Wall fits in BoardLocations and if WallGap is available
			if (board.checkWallFits(boardCoord, isHorizontal) == true){
				//updates players walls placed
				if (player.placeWall(boardCoord, isHorizontal) == false){
					return "Player Has Used All Their Walls!";
				}
				//updates available gaps on board and adds wall to WallGap
				board.updateGapAvailabilty(boardCoord, isHorizontal);
				return "Wall Placed";
			} else {
				return "No Room For Wall";
			}
			
		} else {
			throw new Exception("Walls can only be placed on Gaps!");
		}
	}
}
