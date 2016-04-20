package gui;

import gameplay.Controls;
import gameplay.Quoridor;
import gameplay.Rules;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.Coordinate;
import model.Player;
import model.Wall;
import model.Board.BoardLocation;

/**
 * This class creates the panel which is displayed when the player plays the
 * game.
 */
public class GameView implements ViewPanel {
	private Controls controls;
	private JPanel panel;
	private JPanel boardPanel;
	JLabel playersTurnLabel;
	private JButton exitButton;
	private JButton vWall;
	private JButton hWall;
	private JButton movePawn;
	private JButton undoMove;
	private JButton endTurn;
	private Quoridor quoridor;
	private JLabel[][] boardLocations;
	private JLabel[][] boardWallGaps;

	public static enum GameState {
		PlacingVWall, PlacingHWall, MovingPlayer, TurnTaken, GameEnded
	};

	private GameState currentState;
	private String turnTaken;
	private ArrayList<Coordinate> validMoves;
	private Image squareIMG = null;
	private Image squareHighlightedIMG = null;
	private Image vWallIMG = null;
	private Image hWallIMG = null;
	private Image hoverVWallIMG = null;
	private Image hoverHWallIMG = null;
	private Image redPawnIMG = null;
	private Image greenPawnIMG = null;
	private Image bluePawnIMG = null;
	private Image yellowPawnIMG = null;

	private Language currentLanguage;
	
	public GameView(Rules rules) {
		// Set the current language
		currentLanguage = new Language();
		ResourceBundle messages = currentLanguage.getMessages();
		
		// Initialise Game
		controls = new Controls();
		quoridor = new Quoridor(rules);
		quoridor.beginGame();
		turnTaken = null;
		currentState = GameState.MovingPlayer;
		// setup Images
		try {
			squareIMG = ImageIO.read(new File("Images/Square.png"));
			squareHighlightedIMG = ImageIO.read(new File("Images/SquareHighlight.png"));
			vWallIMG = ImageIO.read(new File("Images/vwall.png"));
			hWallIMG = ImageIO.read(new File("Images/hwall.png"));
			hoverVWallIMG = ImageIO.read(new File("Images/hovervwall.png"));
			hoverHWallIMG = ImageIO.read(new File("Images/hoverhwall.png"));
			redPawnIMG = ImageIO.read(new File("Images/RedPawn.png"));
			greenPawnIMG = ImageIO.read(new File("Images/GreenPawn.png"));
			bluePawnIMG = ImageIO.read(new File("Images/BluePawn.png"));
			yellowPawnIMG = ImageIO.read(new File("Images/YellowPawn.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		squareIMG = squareIMG.getScaledInstance(62, 62,
				BufferedImage.TYPE_INT_ARGB);
		squareHighlightedIMG = squareHighlightedIMG.getScaledInstance(62, 62,
				BufferedImage.TYPE_INT_ARGB);
		vWallIMG = vWallIMG.getScaledInstance(8, 62,
				BufferedImage.TYPE_INT_ARGB);
		hWallIMG = hWallIMG.getScaledInstance(62, 8,
				BufferedImage.TYPE_INT_ARGB);
		hoverVWallIMG = hoverVWallIMG.getScaledInstance(8, 62,
				BufferedImage.TYPE_INT_ARGB);
		hoverHWallIMG = hoverHWallIMG.getScaledInstance(62, 8,
				BufferedImage.TYPE_INT_ARGB);
		redPawnIMG = redPawnIMG.getScaledInstance(62, 62,
				BufferedImage.TYPE_INT_ARGB);
		greenPawnIMG = greenPawnIMG.getScaledInstance(62, 62,
				BufferedImage.TYPE_INT_ARGB);
		bluePawnIMG = bluePawnIMG.getScaledInstance(62, 62,
				BufferedImage.TYPE_INT_ARGB);
		yellowPawnIMG = yellowPawnIMG.getScaledInstance(62, 62,
				BufferedImage.TYPE_INT_ARGB);

		exitButton = new JButton();
		vWall = new JButton();
		hWall = new JButton();
		movePawn = new JButton();
		undoMove = new JButton();
		endTurn = new JButton();

		// Set the properties of the components
		playersTurnLabel = new JLabel(messages.getString("players_turn"));
		
		exitButton.setText(messages.getString("give_up"));
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.setMinimumSize(new Dimension(100, 50));
		exitButton.setPreferredSize(new Dimension(100, 50));
		exitButton.setMaximumSize(new Dimension(100, 50));

		vWall.setText(messages.getString("ver_wall"));
		vWall.setAlignmentX(Component.CENTER_ALIGNMENT);
		vWall.setMinimumSize(new Dimension(100, 50));
		vWall.setPreferredSize(new Dimension(100, 50));
		vWall.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		vWall.setEnabled(false);

		hWall.setText(messages.getString("hor_wall"));
		hWall.setAlignmentX(Component.CENTER_ALIGNMENT);
		hWall.setMinimumSize(new Dimension(100, 50));
		hWall.setPreferredSize(new Dimension(100, 50));
		hWall.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		hWall.setEnabled(false);

		movePawn.setText(messages.getString("move_pawn"));
		movePawn.setAlignmentX(Component.CENTER_ALIGNMENT);
		movePawn.setMinimumSize(new Dimension(100, 50));
		movePawn.setPreferredSize(new Dimension(100, 50));
		movePawn.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		movePawn.setEnabled(false);

		undoMove.setText(messages.getString("undo"));
		undoMove.setAlignmentX(Component.CENTER_ALIGNMENT);
		undoMove.setMinimumSize(new Dimension(100, 50));
		undoMove.setPreferredSize(new Dimension(100, 50));
		undoMove.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		undoMove.setEnabled(false);

		endTurn.setText(messages.getString("end_turn"));
		endTurn.setAlignmentX(Component.CENTER_ALIGNMENT);
		endTurn.setMinimumSize(new Dimension(100, 50));
		endTurn.setPreferredSize(new Dimension(100, 50));
		endTurn.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		endTurn.setEnabled(false);

		// Create containers to hold the components
		JPanel sideButtons = new JPanel();
		boardPanel = new JPanel(null);
		panel = new JPanel(null);

		// Specify LayoutManagers
		boardPanel.setSize(600, 600);
		boardPanel.setLocation(220, 5);
		sideButtons.setSize(100, 600);
		sideButtons.setLocation(50, 0);

		// Setup Board Squares	
		boardWallGaps = new JLabel[8][8];
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				boardWallGaps[x][y] = new JLabel();
				boardWallGaps[x][y].setSize(62, 62);
				boardWallGaps[x][y].setLocation(31+(x * 62), 31+(y * 62));
				boardWallGaps[x][y].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						wallGapClicked(e);
					}
					public void mouseExited(MouseEvent e) {
					    hideMouseHover(e);
					}
					public void mouseEntered(MouseEvent e) {
					    showMouseHover(e);
					}
				});
				boardPanel.add(boardWallGaps[x][y]);
			}
		}
		
		boardLocations = new JLabel[17][17];
		
		for (int x = 1; x < 18; x++) {
			for (int y = 1; y < 18; y++) {
				//checks if x is even and y is not even, then sets it as Gap Object
				if ((x % 2 == 0) && (y % 2 != 0)){
					boardLocations[x-1][y-1] = new JLabel();
					boardLocations[x-1][y-1].setSize(8, 62);
					boardLocations[x-1][y-1].setLocation(((x/2) * 62)-4, ((y/2) * 62));
					boardLocations[x-1][y-1].addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							squareClicked(e);
						}
					});
					boardPanel.add(boardLocations[x-1][y-1]);
				}
				//checks if x is not even and y is even, then sets it as Gap Object
				if ((x % 2 != 0) && (y % 2 == 0)){
					boardLocations[x-1][y-1] = new JLabel();
					boardLocations[x-1][y-1].setSize(62, 8);
					boardLocations[x-1][y-1].setLocation(((x/2) * 62), ((y/2) * 62)-4);
					boardLocations[x-1][y-1].addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							squareClicked(e);
						}
					});
					boardPanel.add(boardLocations[x-1][y-1]);
				}
			}
		}
		
		for (int x = 1; x < 18; x++) {
			for (int y = 1; y < 18; y++) {
				//checks if x is not even and y is not even, then sets it as Square Object
				if ((x % 2 != 0) && (y % 2 != 0)){
					boardLocations[x-1][y-1] = new JLabel();
					boardLocations[x-1][y-1].setIcon(new ImageIcon(squareIMG));
					boardLocations[x-1][y-1].setSize(62, 62);
					boardLocations[x-1][y-1].setLocation((x/2) * 62, (y/2) * 62);
					boardLocations[x-1][y-1].addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							squareClicked(e);
						}
					});
					boardPanel.add(boardLocations[x-1][y-1]);
				} 
			}
		}
		
		// Add components to containers
		sideButtons.add(playersTurnLabel);
		sideButtons.add(hWall);
		sideButtons.add(vWall);
		sideButtons.add(movePawn);
		sideButtons.add(undoMove);
		sideButtons.add(endTurn);
		sideButtons.add(exitButton);
		panel.add(sideButtons);
		panel.add(boardPanel);

		// Events
		endTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				endPlayersTurn();
			}
		});
		movePawn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				movePlayerSelected();
			}
		});
		vWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vWallSelected();
			}
		});
		hWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hWallSelected();
			}
		});
		undoMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undoPlayersMove();
			}
		});
		
		panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
            	if(Character.toUpperCase(e.getKeyChar()) == controls.getUp().charAt(0)){
            	}
            }
        });

		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showOptionDialog(null, 
						messages.getString("give_up_confirm_message"), 
						messages.getString("give_up_title"), 
				        JOptionPane.YES_NO_OPTION, 
				        JOptionPane.INFORMATION_MESSAGE, 
				        null, 
				        new String[]{messages.getString("yes"), messages.getString("no")},
				        "default");
				
				if (confirmed == 0) {
					panel.setVisible(false);
					MainMenuView mm = new MainMenuView();
					mm.addToJFrame();
					mm.setVisible();
				}
			}
		});
		
		updateBoardDisplay();
		activateState();
		updateButtons();
	}
	
	public void setWallGapsVisible(boolean visible){
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				boardWallGaps[x][y].setVisible(visible);
			}
		}
	}
	
	public Coordinate getCoordFromString(){
		boolean change = false;
		int i = 5;
		String x = "";
		String y = "";
		while (turnTaken.charAt(i) != ' '){
			if (turnTaken.charAt(i) == ','){
				change = true;
			} else if (Character.isDigit(turnTaken.charAt(i))){
				if (change == false){
					x += turnTaken.substring(i,i+1);
				} else {
					y += turnTaken.substring(i,i+1);
				}
			} 
			i++;
		}
		return new Coordinate(Integer.parseInt(x),Integer.parseInt(y));
	}
	
	public void undoPlayersMove(){
		Coordinate coord;
		switch(turnTaken.substring(0, 4)){
		case "Pawn":
			coord = getCoordFromString();
			quoridor.movePlayersPawn(coord);
			currentState = GameState.MovingPlayer;
		break;
		case "Vall":
			coord = getCoordFromString();
			quoridor.removePlayerWall(coord);
			currentState = GameState.PlacingVWall;
		break;
		case "Hall":
			coord = getCoordFromString();
			quoridor.removePlayerWall(coord);
			currentState = GameState.PlacingHWall;
		break;
		}
		turnTaken = null;
		updateBoardDisplay();
		activateState();
		updateButtons();
	}
	
	public void hWallSelected(){
		currentState = GameState.PlacingHWall;
		updateBoardDisplay();
		activateState();
	}
	
	public void vWallSelected(){
		currentState = GameState.PlacingVWall;
		updateBoardDisplay();
		activateState();
	}
	
	public void movePlayerSelected(){
		currentState = GameState.MovingPlayer;
		updateBoardDisplay();
		activateState();
	}

	public void endPlayersTurn() {
		quoridor.endPlayersTurn();
		currentState = GameState.MovingPlayer;
		turnTaken = null;
		updateBoardDisplay();
		activateState();
		updateButtons();
	}

	public void updateButtons() {
		if (currentState == GameState.GameEnded){
			vWall.setEnabled(false);
			hWall.setEnabled(false);
			movePawn.setEnabled(false);
			undoMove.setEnabled(false);
			endTurn.setEnabled(false);
		} else if (currentState == GameState.TurnTaken) {
			vWall.setEnabled(false);
			hWall.setEnabled(false);
			movePawn.setEnabled(false);
			undoMove.setEnabled(true);
			endTurn.setEnabled(true);
		} else {
			vWall.setEnabled(true);
			hWall.setEnabled(true);
			movePawn.setEnabled(true);
			undoMove.setEnabled(false);
			endTurn.setEnabled(false);
		}
	}

	public void activateState() {
		switch (currentState.toString()) {
		case "MovingPlayer":
			setWallGapsVisible(false);
			validMoves = quoridor.getValidMoves();
			for (int i = 0; i < validMoves.size(); i++) {
				boardLocations[validMoves.get(i).getX()][validMoves.get(i).getY()].setIcon(new ImageIcon(squareHighlightedIMG));
			}
			break;
		case "PlacingVWall":
			setWallGapsVisible(true);
			break;
		case "PlacingHWall":
			setWallGapsVisible(true);
			break;
		case "TurnTaken":
			setWallGapsVisible(false);
			break;
		}
		
	}
	

	public Coordinate getSquareCoordinates(MouseEvent e) {
		for (int x = 0; x < 17; x++) {
			for (int y = 0; y < 17; y++) {
				if (e.getComponent() == boardLocations[x][y]) {
					return new Coordinate(x, y);
				}
			}
		}
		return null;
	}
	
	public Coordinate getWallGapCoordinates(MouseEvent e) {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (e.getComponent() == boardWallGaps[x][y]) {
					return new Coordinate(x, y);
				}
			}
		}
		return null;
	}
	
	public void wallGapClicked(MouseEvent e) {
		Coordinate gapCoord = getWallGapCoordinates(e);
		Coordinate boardCoord = convertVGtoMG(gapCoord);
		switch (currentState.toString()) {
		case "PlacingHWall":
			if (quoridor.addPlayerWall(boardCoord, true) == "Wall Placed"){
				currentState = GameState.TurnTaken;
				turnTaken = "Hall "+boardCoord.toString();
				updateBoardDisplay();
				activateState();
				updateButtons();
			}
			break;
		case "PlacingVWall":
			if (quoridor.addPlayerWall(boardCoord, false) == "Wall Placed"){
				currentState = GameState.TurnTaken;
				turnTaken = "Vall "+boardCoord.toString();
				updateBoardDisplay();
				activateState();
				updateButtons();
			}
			break;
		}
	}
	
	public void showMouseHover(MouseEvent e){
		Coordinate gapCoord = getWallGapCoordinates(e);
		Coordinate boardCoord = convertVGtoMG(gapCoord);
		switch (currentState.toString()) {
		case "PlacingHWall":
			if (quoridor.checkFreeWallGap(boardCoord, true)){
				boardLocations[boardCoord.getWall("east").getX()][boardCoord.getY()].setIcon(new ImageIcon(hoverHWallIMG));
				boardLocations[boardCoord.getWall("west").getX()][boardCoord.getY()].setIcon(new ImageIcon(hoverHWallIMG));
			}
			break;
		case "PlacingVWall":
			if (quoridor.checkFreeWallGap(boardCoord, false)){
				boardLocations[boardCoord.getX()][boardCoord.getWall("north").getY()].setIcon(new ImageIcon(hoverVWallIMG));
				boardLocations[boardCoord.getX()][boardCoord.getWall("south").getY()].setIcon(new ImageIcon(hoverVWallIMG));
			}
			break;
		}
	}
	
	public void hideMouseHover(MouseEvent e){
		Coordinate gapCoord = getWallGapCoordinates(e);
		Coordinate boardCoord = convertVGtoMG(gapCoord);
		switch (currentState.toString()) {
		case "PlacingHWall":
			if (quoridor.checkFreeWallGap(boardCoord, true)){
				boardLocations[boardCoord.getWall("east").getX()][boardCoord.getY()].setIcon(null);
				boardLocations[boardCoord.getWall("west").getX()][boardCoord.getY()].setIcon(null);
			}
			break;
		case "PlacingVWall":
			if (quoridor.checkFreeWallGap(boardCoord, false)){
				boardLocations[boardCoord.getX()][boardCoord.getWall("north").getY()].setIcon(null);
				boardLocations[boardCoord.getX()][boardCoord.getWall("south").getY()].setIcon(null);
			}
			break;
		}
	}
	
	public Coordinate convertVGtoMG(Coordinate coord){
		return new Coordinate((coord.getX()*2)+1, (coord.getY()*2)+1);
	}

	public void squareClicked(MouseEvent e) {
		Coordinate squareCoord = getSquareCoordinates(e);
		switch (currentState.toString()) {
		case "MovingPlayer":
			if (turnTaken == null){
				for (int i = 0; i < validMoves.size(); i++) {
					if (squareCoord.compare(validMoves.get(i)) == true){
						currentState = GameState.TurnTaken;
						turnTaken = "Pawn "+quoridor.getPlayers()[quoridor.getPlayersTurn()].getPawnLocation().toString();
						quoridor.movePlayersPawn(squareCoord);
						checkEndGame();
						activateState();
						updateBoardDisplay();
						updateButtons();
						break;
					}
				}
			}
			break;
		}
	}
	
	public void checkEndGame(){
		int playersTurn = quoridor.getPlayersTurn();
		switch (playersTurn){
		case 0:
			if (quoridor.getPlayers()[playersTurn].getPawnLocation().getY() == 0){
				currentState = GameState.GameEnded;
			}
		break;
		case 1:
			if (quoridor.getPlayers()[playersTurn].getPawnLocation().getY() == 16){
				currentState = GameState.GameEnded;
			}
		break;
		case 2:
			if (quoridor.getPlayers()[playersTurn].getPawnLocation().getX() == 16){
				currentState = GameState.GameEnded;
			}
		break;
		case 3:
			if (quoridor.getPlayers()[playersTurn].getPawnLocation().getX() == 0){
				currentState = GameState.GameEnded;
			}
		break;
		}
	}

	public void updateBoardDisplay() {
		playersTurnLabel.setText("Players Turn : "+(quoridor.getPlayersTurn()+1));
		Player[] players = quoridor.getPlayers();
		int i = 0;
		for (int x = 0; x < 17; x++) {
			for (int y = 0; y < 17; y++) {
				if (quoridor.checkFreeSquare(new Coordinate(x,y))){
					boardLocations[x][y].setIcon(new ImageIcon(squareIMG));
				}
				if (quoridor.checkFreeGap(new Coordinate(x,y))){
					boardLocations[x][y].setIcon(null);
				}
			}
		}
		for (Player player : players) {
			i++;
			if (player.getPawnLocation() != null) {
				Coordinate pawnLocation = player.getPawnLocation();
				boardLocations[pawnLocation.getX()][pawnLocation.getY()].setIcon(new ImageIcon(getPlayersImage(i)));
				for (Wall wall : player.getWallsPlaced()) {
					if (wall.isHorizontal() == true){
						boardLocations[wall.getPosition().getWall("west").getX()][wall.getPosition().getY()].setIcon(new ImageIcon(hWallIMG));
						boardLocations[wall.getPosition().getWall("east").getX()][wall.getPosition().getY()].setIcon(new ImageIcon(hWallIMG));
					} else {
						boardLocations[wall.getPosition().getX()][wall.getPosition().getWall("north").getY()].setIcon(new ImageIcon(vWallIMG));
						boardLocations[wall.getPosition().getX()][wall.getPosition().getWall("south").getY()].setIcon(new ImageIcon(vWallIMG));
					}
				}
			}
		}
	}

	public Image getPlayersImage(int player) {
		switch (quoridor.getPlayers()[player - 1].getPawn().getColour()) {
		case "red":
			return redPawnIMG;
		case "blue":
			return bluePawnIMG;
		case "green":
			return greenPawnIMG;
		case "yellow":
			return yellowPawnIMG;
		}
		return null;
	}

	/**
	 * Makes the panel visible
	 */
	public void setVisible() {
		panel.setVisible(true);
	}

	/**
	 * Add the panel to the GUI's main JFrame, positioned at CENTER
	 */
	public void addToJFrame() {
		GUI.getJFrame().add(panel, BorderLayout.CENTER);
	}

	/**
	 * This method returns the main view panel
	 * 
	 * @return JPanel panel
	 */
	public JPanel getJPanel() {
		return panel;
	}
}
