package gui;

import gameplay.Controls;
import gameplay.Quoridor;
import gameplay.Rules;
import gameplay.Statistics;

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
 * This class is responsible for creating a JPanel which displays a number of buttons and labels to provide a GUI for the user.
 * The class also displays the board, pawns, walls and valid moves which the player can make.
 * 
 * @author COMP7
 * @version v1.0, 26/04/2016
 */
public class GameView implements ViewPanel {
	private Controls controls;
	private JPanel panel;
	private JPanel boardPanel;
	private JLabel playersTurnLabel;
	private JLabel playerWallsLabel;
	private JButton exitButton;
	private JButton vWall;
	private JButton hWall;
	private JButton removeWall;
	private JButton movePawn;
	private JButton undoMove;
	private JButton endTurn;
	private Quoridor quoridor;
	private JLabel[][] boardLocations;
	private JLabel[][] boardWallGaps;

	public static enum GameState {
		BotsTurn, PlacingVWall, PlacingHWall, RemovingWall, MovingPlayer, TurnTaken, GameEnded
	};

	private GameState currentState;
	private String turnTaken;
	private ArrayList<Coordinate> validMoves;
	private Image squareIMG = null;
	private Image squareHighlightedIMG = null;
	private Image squareTurnIMG = null;
	private Image squareWinIMG = null;
	private Image blueVWallIMG = null;
	private Image greenVWallIMG = null;
	private Image redVWallIMG = null;
	private Image yellowVWallIMG = null;
	private Image blueHWallIMG = null;
	private Image greenHWallIMG = null;
	private Image redHWallIMG = null;
	private Image yellowHWallIMG = null;
	private Image hoverVWallIMG = null;
	private Image hoverHWallIMG = null;
	private Image redPawnIMG = null;
	private Image greenPawnIMG = null;
	private Image bluePawnIMG = null;
	private Image yellowPawnIMG = null;

	// Set the current language
	private Language currentLanguage = new Language();
	private ResourceBundle messages = currentLanguage.getMessages();
	
	private Statistics stats;

	public GameView(Rules rules) {
		// Initialise Game
		controls = new Controls();
		stats = new Statistics(rules);
		
		quoridor = new Quoridor(rules);
		quoridor.beginGame();
		turnTaken = null;
		
		// Setup Images
		try {
			squareIMG = ImageIO.read(new File("Images/squares/Square.png"));
			squareHighlightedIMG = ImageIO.read(new File("Images/squares/SquareHighlight.png"));
			squareWinIMG = ImageIO.read(new File("Images/squares/SquareWin.png"));
			squareTurnIMG = ImageIO.read(new File("Images/squares/SquareTurn.png"));
			blueVWallIMG = ImageIO.read(new File("Images/walls/bluevwall.png"));
			greenVWallIMG = ImageIO.read(new File("Images/walls/greenvwall.png"));
			yellowVWallIMG = ImageIO.read(new File("Images/walls/yellowvwall.png"));
			redVWallIMG = ImageIO.read(new File("Images/walls/redvwall.png"));
			blueHWallIMG = ImageIO.read(new File("Images/walls/bluehwall.png"));
			greenHWallIMG = ImageIO.read(new File("Images/walls/greenhwall.png"));
			redHWallIMG = ImageIO.read(new File("Images/walls/redhwall.png"));
			yellowHWallIMG = ImageIO.read(new File("Images/walls/yellowhwall.png"));
			hoverVWallIMG = ImageIO.read(new File("Images/walls/hovervwall.png"));
			hoverHWallIMG = ImageIO.read(new File("Images/walls/hoverhwall.png"));
			redPawnIMG = ImageIO.read(new File("Images/pawns/RedPawn.png"));
			greenPawnIMG = ImageIO.read(new File("Images/pawns/GreenPawn.png"));
			bluePawnIMG = ImageIO.read(new File("Images/pawns/BluePawn.png"));
			yellowPawnIMG = ImageIO.read(new File("Images/pawns/YellowPawn.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		squareIMG = squareIMG.getScaledInstance(62, 62,
				BufferedImage.TYPE_INT_ARGB);
		squareHighlightedIMG = squareHighlightedIMG.getScaledInstance(62, 62,
				BufferedImage.TYPE_INT_ARGB);
		squareWinIMG = squareWinIMG.getScaledInstance(62, 62,
				BufferedImage.TYPE_INT_ARGB);
		squareTurnIMG = squareTurnIMG.getScaledInstance(62, 62,
				BufferedImage.TYPE_INT_ARGB);
		blueVWallIMG = blueVWallIMG.getScaledInstance(8, 62,
				BufferedImage.TYPE_INT_ARGB);
		greenVWallIMG = greenVWallIMG.getScaledInstance(8, 62,
				BufferedImage.TYPE_INT_ARGB);
		yellowVWallIMG = yellowVWallIMG.getScaledInstance(8, 62,
				BufferedImage.TYPE_INT_ARGB);
		redVWallIMG = redVWallIMG.getScaledInstance(8, 62,
				BufferedImage.TYPE_INT_ARGB);
		blueHWallIMG = blueHWallIMG.getScaledInstance(62, 8,
				BufferedImage.TYPE_INT_ARGB);
		greenHWallIMG = greenHWallIMG.getScaledInstance(62, 8,
				BufferedImage.TYPE_INT_ARGB);
		redHWallIMG = redHWallIMG.getScaledInstance(62, 8,
				BufferedImage.TYPE_INT_ARGB);
		yellowHWallIMG = yellowHWallIMG.getScaledInstance(62, 8,
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
		removeWall = new JButton();

		// Set the properties of the components
		playersTurnLabel = new JLabel(messages.getString("players_turn"));

		playerWallsLabel = new JLabel(messages.getString("players_walls"));

		exitButton.setText(messages.getString("give_up"));
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.setMinimumSize(new Dimension(150, 50));
		exitButton.setPreferredSize(new Dimension(150, 50));
		exitButton.setMaximumSize(new Dimension(150, 50));

		vWall.setText(messages.getString("ver_wall"));
		vWall.setAlignmentX(Component.CENTER_ALIGNMENT);
		vWall.setMinimumSize(new Dimension(150, 50));
		vWall.setPreferredSize(new Dimension(150, 50));
		vWall.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		vWall.setEnabled(false);

		hWall.setText(messages.getString("hor_wall"));
		hWall.setAlignmentX(Component.CENTER_ALIGNMENT);
		hWall.setMinimumSize(new Dimension(150, 50));
		hWall.setPreferredSize(new Dimension(150, 50));
		hWall.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		hWall.setEnabled(false);

		movePawn.setText(messages.getString("move_pawn"));
		movePawn.setAlignmentX(Component.CENTER_ALIGNMENT);
		movePawn.setMinimumSize(new Dimension(150, 50));
		movePawn.setPreferredSize(new Dimension(150, 50));
		movePawn.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		movePawn.setEnabled(false);

		undoMove.setText(messages.getString("undo"));
		undoMove.setAlignmentX(Component.CENTER_ALIGNMENT);
		undoMove.setMinimumSize(new Dimension(150, 50));
		undoMove.setPreferredSize(new Dimension(150, 50));
		undoMove.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		undoMove.setEnabled(false);

		endTurn.setText(messages.getString("end_turn"));
		endTurn.setAlignmentX(Component.CENTER_ALIGNMENT);
		endTurn.setMinimumSize(new Dimension(150, 50));
		endTurn.setPreferredSize(new Dimension(150, 50));
		endTurn.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		endTurn.setEnabled(false);
		
		removeWall.setText(messages.getString("remove_wall"));
		removeWall.setAlignmentX(Component.CENTER_ALIGNMENT);
		removeWall.setMinimumSize(new Dimension(150, 50));
		removeWall.setPreferredSize(new Dimension(150, 50));
		removeWall.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		removeWall.setEnabled(false);

		// Create containers to hold the components
		JPanel sideButtons = new JPanel();
		boardPanel = new JPanel(null);
		panel = new JPanel(null);

		panel.setBackground(new Color(99, 187, 214));
		boardPanel.setOpaque(false);
		sideButtons.setOpaque(false);
		
		// Specify LayoutManagers
		boardPanel.setSize(600, 600);
		boardPanel.setLocation(220, 5);
		sideButtons.setSize(150, 600);
		sideButtons.setLocation(50, 0);

		// Setup Board Squares
		boardWallGaps = new JLabel[8][8];
		
		// Loop through an array adding wall gaps to it as a JLabel
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				boardWallGaps[x][y] = new JLabel();
				boardWallGaps[x][y].setSize(62, 62);
				boardWallGaps[x][y].setLocation(31 + (x * 62), 31 + (y * 62));
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
				// Checks if x is even and y is not even, then sets it as Gap
				// Object
				if ((x % 2 == 0) && (y % 2 != 0)) {
					boardLocations[x - 1][y - 1] = new JLabel();
					boardLocations[x - 1][y - 1].setSize(8, 62);
					boardLocations[x - 1][y - 1].setLocation(
							((x / 2) * 62) - 4, ((y / 2) * 62));
					boardLocations[x - 1][y - 1]
							.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseClicked(MouseEvent e) {
									squareClicked(e);
								}
							});
					boardPanel.add(boardLocations[x - 1][y - 1]);
				}
				// Checks if x is not even and y is even, then sets it as Gap
				// Object
				if ((x % 2 != 0) && (y % 2 == 0)) {
					boardLocations[x - 1][y - 1] = new JLabel();
					boardLocations[x - 1][y - 1].setSize(62, 8);
					boardLocations[x - 1][y - 1].setLocation(((x / 2) * 62),
							((y / 2) * 62) - 4);
					boardLocations[x - 1][y - 1]
							.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseClicked(MouseEvent e) {
									squareClicked(e);
								}
							});
					boardPanel.add(boardLocations[x - 1][y - 1]);
				}
			}
		}

		for (int x = 1; x < 18; x++) {
			for (int y = 1; y < 18; y++) {
				// checks if x is not even and y is not even, then sets it as
				// Square Object
				if ((x % 2 != 0) && (y % 2 != 0)) {
					boardLocations[x - 1][y - 1] = new JLabel();
					boardLocations[x - 1][y - 1].setIcon(new ImageIcon(
							squareIMG));
					boardLocations[x - 1][y - 1].setSize(62, 62);
					boardLocations[x - 1][y - 1].setLocation((x / 2) * 62,
							(y / 2) * 62);
					boardLocations[x - 1][y - 1]
							.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseClicked(MouseEvent e) {
									squareClicked(e);
								}
							});
					boardPanel.add(boardLocations[x - 1][y - 1]);
				}
			}
		}

		// Add components to containers
		sideButtons.add(playersTurnLabel);
		sideButtons.add(hWall);
		sideButtons.add(vWall);
		if (quoridor.getRules().getGameRules() == messages.getString("challenge_rules")){
			sideButtons.add(removeWall);
		}
		sideButtons.add(movePawn);
		sideButtons.add(undoMove);
		sideButtons.add(endTurn);
		sideButtons.add(exitButton);
		sideButtons.add(playerWallsLabel);
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
		removeWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removePlayersWall();
			}
		});

		panel.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (currentState != GameState.GameEnded) {
					if (currentState != GameState.TurnTaken) {
						if (Character.toUpperCase(e.getKeyChar()) == controls
								.getUp().charAt(0)) {
							currentState = GameState.MovingPlayer;
							activateState();
							Coordinate squareCoord = quoridor.getPlayers()[quoridor
									.getPlayersTurn()].getPawnLocation()
									.getSquare("north");
							if (movePlayer(squareCoord) == false) {
								squareCoord = squareCoord.getSquare("north");
								movePlayer(squareCoord);
							}
						} else if (Character.toUpperCase(e.getKeyChar()) == controls
								.getDown().charAt(0)) {
							currentState = GameState.MovingPlayer;
							activateState();
							Coordinate squareCoord = quoridor.getPlayers()[quoridor
									.getPlayersTurn()].getPawnLocation()
									.getSquare("south");
							if (movePlayer(squareCoord) == false) {
								squareCoord = squareCoord.getSquare("south");
								movePlayer(squareCoord);
							}
						} else if (Character.toUpperCase(e.getKeyChar()) == controls
								.getLeft().charAt(0)) {
							currentState = GameState.MovingPlayer;
							activateState();
							Coordinate squareCoord = quoridor.getPlayers()[quoridor
									.getPlayersTurn()].getPawnLocation()
									.getSquare("west");
							if (movePlayer(squareCoord) == false) {
								squareCoord = squareCoord.getSquare("west");
								movePlayer(squareCoord);
							}
						} else if (Character.toUpperCase(e.getKeyChar()) == controls
								.getRight().charAt(0)) {
							currentState = GameState.MovingPlayer;
							activateState();
							Coordinate squareCoord = quoridor.getPlayers()[quoridor
									.getPlayersTurn()].getPawnLocation()
									.getSquare("east");
							if (movePlayer(squareCoord) == false) {
								squareCoord = squareCoord.getSquare("east");
								movePlayer(squareCoord);
							}
						} else if (Character.toUpperCase(e.getKeyChar()) == controls
								.getVerticalWall().charAt(0)) {
							vWallSelected();
						} else if (Character.toUpperCase(e.getKeyChar()) == controls
								.getHorizontalWall().charAt(0)) {
							hWallSelected();
						} else if (Character.toUpperCase(e.getKeyChar()) == controls
								.getRemoveWall().charAt(0)) {
							if (quoridor.getRules().getGameRules() == messages.getString("challenge_rules")){
								removePlayersWall();
							}
						}
					} else {
						if (Character.toUpperCase(e.getKeyChar()) == controls
								.getEndTurn().charAt(0)) {
							endPlayersTurn();
						} else if (Character.toUpperCase(e.getKeyChar()) == controls
								.getUndo().charAt(0)) {
							undoPlayersMove();
						}
					}
				}
			}
		});

		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showOptionDialog(
						null,
						messages.getString("give_up_confirm_message"),
						messages.getString("give_up_title"),
						JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE,
						null,
						new String[] { messages.getString("yes"),
								messages.getString("no") }, "default");

				if (confirmed == 0) {
					panel.setVisible(false);
					MainMenuView mm = new MainMenuView();
					mm.addToJFrame();
					mm.setVisible();
				}
			}
		});

		checkBotPlayer();
	}

	public void setWallGapsVisible(boolean visible) {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				boardWallGaps[x][y].setVisible(visible);
			}
		}
	}

	public Coordinate getCoordFromString() {
		boolean change = false;
		int i = 5;
		String x = "";
		String y = "";
		while (turnTaken.charAt(i) != ' ') {
			if (turnTaken.charAt(i) == ',') {
				change = true;
			} else if (Character.isDigit(turnTaken.charAt(i))) {
				if (change == false) {
					x += turnTaken.substring(i, i + 1);
				} else {
					y += turnTaken.substring(i, i + 1);
				}
			}
			i++;
		}
		return new Coordinate(Integer.parseInt(x), Integer.parseInt(y));
	}
	
	public int getPlayerFromString() {
		int player = 0;
		int i = 4;
		while (turnTaken.charAt(i) != 'W') {
			if (turnTaken.charAt(i) == 'p') {
				player = Character.getNumericValue(turnTaken.charAt(i+1));
				break;
			}
			i++;
		}
		return player;
	}
	
	public boolean getIsHorizontalFromString() {
		boolean isHorizontal = false;
		int i = 4;
		while (turnTaken.charAt(i) != 'W'){
			if (turnTaken.charAt(i) == 't') {
				isHorizontal = true;
				break;
			} else if (turnTaken.charAt(i) == 'f') {
				isHorizontal = false;
				break;
			}
			i++;
		}
		return isHorizontal;
	}

	public void undoPlayersMove() {
		if(turnTaken.length() != 0){
			Coordinate coord;
			switch (turnTaken.substring(0, 4)) {
			case "Pawn":
				coord = getCoordFromString();
				quoridor.movePlayersPawn(quoridor.getPlayersTurn(),coord);
				currentState = GameState.MovingPlayer;
				stats.decrementStepsTaken((quoridor.getPlayersTurn()));
				break;
			case "Vall":
				coord = getCoordFromString();
				quoridor.removePlayerWall(coord, true);
				currentState = GameState.PlacingVWall;
				stats.decrementWallsPlaced((quoridor.getPlayersTurn()));
				break;
			case "Hall":
				coord = getCoordFromString();
				quoridor.removePlayerWall(coord, true);
				currentState = GameState.PlacingHWall;
				stats.decrementWallsPlaced((quoridor.getPlayersTurn()));
				break;
			case "Rall":
				coord = getCoordFromString();
				int player = getPlayerFromString();
				boolean isHorizontal = getIsHorizontalFromString();
				quoridor.addPlayerWall(player, coord, isHorizontal);
				currentState = GameState.RemovingWall;
				stats.decrementWallsRemoved((quoridor.getPlayersTurn()));
				break;
			}
			stats.incrementUndosTaken(quoridor.getPlayersTurn());
			turnTaken = null;
			activateState();
			updateButtons();
		}
	}

	public void hWallSelected() {
		currentState = GameState.PlacingHWall;
		activateState();
	}

	public void vWallSelected() {
		currentState = GameState.PlacingVWall;
		activateState();
	}
	
	public void removePlayersWall(){
		currentState = GameState.RemovingWall;
		activateState();
	}

	public void movePlayerSelected() {
		currentState = GameState.MovingPlayer;
		activateState();
	}

	public void endPlayersTurn() {
		quoridor.endPlayersTurn();
		turnTaken = null;
		checkBotPlayer();
	}
	
	public void checkBotPlayer(){
		if (quoridor.checkBotsGo() == true){
			currentState = GameState.BotsTurn;
			quoridor.takeBotsTurn();
			if (!checkEndGame()){
				endPlayersTurn();
			}
			activateState();
			updateButtons();
		} else {
			currentState = GameState.MovingPlayer;
			activateState();
			updateButtons();
		}
	}

	public void updateButtons() {
		if (currentState == GameState.GameEnded) {
			vWall.setEnabled(false);
			hWall.setEnabled(false);
			removeWall.setEnabled(false);
			movePawn.setEnabled(false);
			undoMove.setEnabled(false);
			endTurn.setEnabled(false);
			exitButton.setEnabled(false);
		} else if (currentState == GameState.TurnTaken) {
			vWall.setEnabled(false);
			hWall.setEnabled(false);
			removeWall.setEnabled(false);
			movePawn.setEnabled(false);
			undoMove.setEnabled(true);
			endTurn.setEnabled(true);
			exitButton.setEnabled(true);
		} else if (currentState == GameState.BotsTurn) {
			vWall.setEnabled(false);
			hWall.setEnabled(false);
			removeWall.setEnabled(false);
			movePawn.setEnabled(false);
			undoMove.setEnabled(false);
			endTurn.setEnabled(false);
			exitButton.setEnabled(true);
		} else {
			vWall.setEnabled(true);
			hWall.setEnabled(true);
			removeWall.setEnabled(true);
			movePawn.setEnabled(true);
			undoMove.setEnabled(false);
			endTurn.setEnabled(false);
			exitButton.setEnabled(true);
		}
	}

	public void activateState() {
		updateBoardDisplay();
		switch (currentState.toString()) {
		case "MovingPlayer":
			setWallGapsVisible(false);
			validMoves = quoridor.getValidMoves();
			for (int i = 0; i < validMoves.size(); i++) {
				if (checkWinLocation(validMoves.get(i)) == false) {
					boardLocations[validMoves.get(i).getX()][validMoves.get(i)
							.getY()].setIcon(new ImageIcon(overlayImage(
							squareIMG, squareHighlightedIMG)));
				} else {
					boardLocations[validMoves.get(i).getX()][validMoves.get(i)
							.getY()].setIcon(new ImageIcon(overlayImage(
							squareWinIMG, squareHighlightedIMG)));
				}
			}
			break;
		case "PlacingVWall":
			setWallGapsVisible(true);
			break;
		case "PlacingHWall":
			setWallGapsVisible(true);
			break;
		case "RemovingWall":
			setWallGapsVisible(true);
			break;
		case "TurnTaken":
			setWallGapsVisible(false);
			break;
		case "BotsTurn":
			setWallGapsVisible(false);
			break;
		case "GameEnded":
			stats.setWinner(quoridor.getPlayers()[quoridor.getPlayersTurn()].getPlayerName());
			panel.setVisible(false);
			StatsView sv = new StatsView(stats);
			sv.addToJFrame();
			sv.setVisible();
			sv.getJPanel().setFocusable(true);
			sv.getJPanel().requestFocusInWindow();
			break;
		}
		getJPanel().requestFocus();
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
			if (quoridor.addPlayerWall(quoridor.getPlayersTurn(), boardCoord, true) == "Wall Placed") {
				currentState = GameState.TurnTaken;
				turnTaken = "Hall " + boardCoord.toString();
				activateState();
				updateButtons();
				stats.incrementWallsPlaced(quoridor.getPlayersTurn());
			}
			break;
		case "PlacingVWall":
			if (quoridor.addPlayerWall(quoridor.getPlayersTurn(), boardCoord, false) == "Wall Placed") {
				currentState = GameState.TurnTaken;
				turnTaken = "Vall " + boardCoord.toString();
				activateState();
				updateButtons();
				stats.incrementWallsPlaced(quoridor.getPlayersTurn());
			}
			break;
		case "RemovingWall":
			String outcome = quoridor.removePlayerWall(boardCoord, false);
			if (outcome.substring(5).equals("Wall Removed")){
				currentState = GameState.TurnTaken;
				turnTaken = "Rall " + boardCoord.toString()+outcome;
				activateState();
				updateButtons();
				stats.incrementWallsRemoved(quoridor.getPlayersTurn());
			}
			break;
		}
	}

	public void showMouseHover(MouseEvent e) {
		Coordinate gapCoord = getWallGapCoordinates(e);
		Coordinate boardCoord = convertVGtoMG(gapCoord);
		switch (currentState.toString()) {
		case "PlacingHWall":
			if (quoridor.checkFreeWallGap(boardCoord, true)) {
				boardLocations[boardCoord.getWall("east").getX()][boardCoord
						.getY()].setIcon(new ImageIcon(hoverHWallIMG));
				boardLocations[boardCoord.getWall("west").getX()][boardCoord
						.getY()].setIcon(new ImageIcon(hoverHWallIMG));
			}
			break;
		case "PlacingVWall":
			if (quoridor.checkFreeWallGap(boardCoord, false)) {
				boardLocations[boardCoord.getX()][boardCoord.getWall("north")
						.getY()].setIcon(new ImageIcon(hoverVWallIMG));
				boardLocations[boardCoord.getX()][boardCoord.getWall("south")
						.getY()].setIcon(new ImageIcon(hoverVWallIMG));
			}
			break;
		}
	}

	public void hideMouseHover(MouseEvent e) {
		Coordinate gapCoord = getWallGapCoordinates(e);
		Coordinate boardCoord = convertVGtoMG(gapCoord);
		switch (currentState.toString()) {
		case "PlacingHWall":
			if (quoridor.checkFreeWallGap(boardCoord, true)) {
				boardLocations[boardCoord.getWall("east").getX()][boardCoord
						.getY()].setIcon(null);
				boardLocations[boardCoord.getWall("west").getX()][boardCoord
						.getY()].setIcon(null);
			}
			break;
		case "PlacingVWall":
			if (quoridor.checkFreeWallGap(boardCoord, false)) {
				boardLocations[boardCoord.getX()][boardCoord.getWall("north")
						.getY()].setIcon(null);
				boardLocations[boardCoord.getX()][boardCoord.getWall("south")
						.getY()].setIcon(null);
			}
			break;
		}
	}

	public Coordinate convertVGtoMG(Coordinate coord) {
		return new Coordinate((coord.getX() * 2) + 1, (coord.getY() * 2) + 1);
	}

	public void squareClicked(MouseEvent e) {
		Coordinate squareCoord = getSquareCoordinates(e);
		movePlayer(squareCoord);
	}

	public boolean movePlayer(Coordinate squareCoord) {
		switch (currentState.toString()) {
		case "MovingPlayer":
			if (turnTaken == null) {
				for (int i = 0; i < validMoves.size(); i++) {
					if (squareCoord.compare(validMoves.get(i)) == true) {
						currentState = GameState.TurnTaken;
						turnTaken = "Pawn "
								+ quoridor.getPlayers()[quoridor
										.getPlayersTurn()].getPawnLocation()
										.toString();
						quoridor.movePlayersPawn(quoridor.getPlayersTurn(), squareCoord);
						checkEndGame();
						activateState();
						updateButtons();
						stats.incrementStepsTaken((quoridor.getPlayersTurn()));
						return true;
					}
				}
			}
			break;
		}
		return false;
	}

	public boolean checkEndGame() {
		if (quoridor.checkFinish(quoridor.getPlayersTurn(), quoridor
				.getPlayers()[quoridor.getPlayersTurn()].getPawnLocation()) == true) {
			currentState = GameState.GameEnded;
			panel.setVisible(false);
			return true;
		}
		return false;
	}

	public void updateBoardDisplay() {
		playersTurnLabel.setText(messages.getString("players_turn")
				+ (quoridor.getPlayersTurn() + 1));
		playerWallsLabel.setText(messages.getString("players_walls")
				+ (quoridor.getPlayersWallsLeft()));
		Player[] players = quoridor.getPlayers();
		for (int x = 0; x < 17; x++) {
			for (int y = 0; y < 17; y++) {
				if (quoridor.checkFreeSquare(new Coordinate(x, y))) {
					if (checkWinLocation(new Coordinate(x,y)) == true){
						boardLocations[x][y].setIcon(new ImageIcon(squareWinIMG));
					} else {
						boardLocations[x][y].setIcon(new ImageIcon(squareIMG));
					}
				}
				if (quoridor.checkFreeGap(new Coordinate(x, y))) {
					boardLocations[x][y].setIcon(null);
				}
			}
		}
		int i = 0;
		for (Player player : players) {
			if (player.getPawnLocation() != null) {
				Coordinate pawnLocation = player.getPawnLocation();
				if (i == quoridor.getPlayersTurn()) {
					boardLocations[pawnLocation.getX()][pawnLocation.getY()]
							.setIcon(new ImageIcon(overlayImage(squareTurnIMG,
									getPlayersImage(i))));
				} else {
					if (checkWinLocation(pawnLocation) == false) {
						boardLocations[pawnLocation.getX()][pawnLocation.getY()]
								.setIcon(new ImageIcon(overlayImage(squareIMG,
										getPlayersImage(i))));
					} else {
						boardLocations[pawnLocation.getX()][pawnLocation.getY()]
								.setIcon(new ImageIcon(overlayImage(
										squareWinIMG, getPlayersImage(i))));
					}
				}

				for (Wall wall : player.getWallsPlaced()) {
					if (wall.isHorizontal() == true) {
						boardLocations[wall.getPosition().getWall("west")
								.getX()][wall.getPosition().getY()]
								.setIcon(new ImageIcon(getPlayersWallImage(i,wall.isHorizontal())));
						boardLocations[wall.getPosition().getWall("east")
								.getX()][wall.getPosition().getY()]
								.setIcon(new ImageIcon(getPlayersWallImage(i,wall.isHorizontal())));
					} else {
						boardLocations[wall.getPosition().getX()][wall
								.getPosition().getWall("north").getY()]
								.setIcon(new ImageIcon(getPlayersWallImage(i,wall.isHorizontal())));
						boardLocations[wall.getPosition().getX()][wall
								.getPosition().getWall("south").getY()]
								.setIcon(new ImageIcon(getPlayersWallImage(i,wall.isHorizontal())));
					}
				}
			}
			i++;
		}
	}

	public boolean checkWinLocation(Coordinate coord) {
		ArrayList<ArrayList<Coordinate>> allWinLocations = getWinLocations();
		ArrayList<Coordinate> winLocations;
		winLocations = allWinLocations.get(quoridor.getPlayersTurn());
		for (int j = 0; j < winLocations.size(); j++) {
			if (winLocations.get(j).compare(coord)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<ArrayList<Coordinate>> getWinLocations() {
		ArrayList<ArrayList<Coordinate>> allCoords = new ArrayList<ArrayList<Coordinate>>();
		for (int i = 0; i < quoridor.getRules().MAX_PLAYERS; i++) {
			allCoords.add(new ArrayList<Coordinate>());
			if (quoridor.getRules().getGameRules() == messages
					.getString("normal_rules")) {
				switch (i) {
				case 0:
					for (int j = 0; j < 17; j++) {
						allCoords.get(i).add(new Coordinate(j, 0));
					}
					break;
				case 1:
					for (int j = 0; j < 17; j++) {
						allCoords.get(i).add(new Coordinate(j, 16));
					}
					break;
				case 2:
					for (int j = 0; j < 17; j++) {
						allCoords.get(i).add(new Coordinate(16, j));
					}
					break;
				case 3:
					for (int j = 0; j < 17; j++) {
						allCoords.get(i).add(new Coordinate(0, j));
					}
					break;
				}
			} else {
				switch (i) {
				case 0:
					allCoords.get(i).add(new Coordinate(0, 0));
					break;
				case 1:
					allCoords.get(i).add(new Coordinate(16, 16));
					break;
				case 2:
					allCoords.get(i).add(new Coordinate(16, 0));
					break;
				case 3:
					allCoords.get(i).add(new Coordinate(0, 16));
					break;
				}
			}
		}
		return allCoords;
	}

	public Image overlayImage(Image image1, Image image2) {
		BufferedImage combined = new BufferedImage(62, 62,
				BufferedImage.TYPE_INT_ARGB);
		// paint both images, preserving the alpha channels
		Graphics g = combined.getGraphics();
		g.drawImage(image1, 0, 0, null);
		g.drawImage(image2, 0, 0, null);
		return combined;
	}

	public Image getPlayersImage(int player) {
		String playerColour = quoridor.getPlayers()[player].getPawn().getColour();
		String redColour = messages.getString("colour_red");
		String blueColour = messages.getString("colour_blue");
		String greenColour = messages.getString("colour_green");
		String yellowColour = messages.getString("colour_yellow");
		if (playerColour.equals(redColour)) {
			return redPawnIMG;
		} else if (playerColour.equals(blueColour)) {
			return bluePawnIMG;
		} else if (playerColour.equals(greenColour)) {
			return greenPawnIMG;
		} else if (playerColour.equals(yellowColour)) {
			return yellowPawnIMG;
		}
		return null;
	}
	
	public Image getPlayersWallImage(int player, boolean isHorizontal) {
		String playerColour = quoridor.getPlayers()[player].getPawn().getColour();
		String redColour = messages.getString("colour_red");
		String blueColour = messages.getString("colour_blue");
		String greenColour = messages.getString("colour_green");
		String yellowColour = messages.getString("colour_yellow");
		if (isHorizontal){
			if (playerColour.equals(redColour)) {
				return redHWallIMG;
			} else if (playerColour.equals(blueColour)) {
				return blueHWallIMG;
			} else if (playerColour.equals(greenColour)) {
				return greenHWallIMG;
			} else if (playerColour.equals(yellowColour)) {
				return yellowHWallIMG;
			}
		} else {
			if (playerColour.equals(redColour)) {
				return redVWallIMG;
			} else if (playerColour.equals(blueColour)) {
				return blueVWallIMG;
			} else if (playerColour.equals(greenColour)) {
				return greenVWallIMG;
			} else if (playerColour.equals(yellowColour)) {
				return yellowVWallIMG;
			}
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
