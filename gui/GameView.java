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

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.Board;
import model.Coordinate;
import model.Player;
import model.Wall;

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
	private JLabel[][] boardSquares;

	public static enum GameState {
		PlacingVWall, PlacingHWall, MovingPlayer
	};

	private GameState currentState;
	private int startingPlayer;
	private boolean turnTaken;
	private ArrayList<Coordinate> validMoves;
	private Image squareIMG = null;
	private Image squareHighlightedIMG = null;
	private Image redPawnIMG = null;
	private Image greenPawnIMG = null;
	private Image bluePawnIMG = null;
	private Image yellowPawnIMG = null;

	/**
	 * This constructor creates and object of class Board and an appropriate
	 * panel which is to be added to the main JFrame THE COLOURS ARE TEMPORARY
	 * AND WILL BE REPLACED BY SPRITES LATER
	 */
	public GameView(Rules rules) {
		// Initialise Game
		controls = new Controls();
		quoridor = new Quoridor(rules);
		quoridor.beginGame();
		startingPlayer = quoridor.getPlayersTurn();
		turnTaken = false;
		currentState = GameState.MovingPlayer;
		// setup Images
		try {
			squareIMG = ImageIO.read(new File("Images/Square.png"));
			squareHighlightedIMG = ImageIO.read(new File(
					"Images/SquareHighlight.png"));
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
		playersTurnLabel = new JLabel("Players Turn : ");
		
		exitButton.setText("Give Up");
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.setMinimumSize(new Dimension(100, 50));
		exitButton.setPreferredSize(new Dimension(100, 50));
		exitButton.setMaximumSize(new Dimension(100, 50));

		vWall.setText("Ver. Wall");
		vWall.setAlignmentX(Component.CENTER_ALIGNMENT);
		vWall.setMinimumSize(new Dimension(100, 50));
		vWall.setPreferredSize(new Dimension(100, 50));
		vWall.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		vWall.setEnabled(false);

		hWall.setText("Hor. Wall");
		hWall.setAlignmentX(Component.CENTER_ALIGNMENT);
		hWall.setMinimumSize(new Dimension(100, 50));
		hWall.setPreferredSize(new Dimension(100, 50));
		hWall.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		hWall.setEnabled(false);

		movePawn.setText("Move Pawn");
		movePawn.setAlignmentX(Component.CENTER_ALIGNMENT);
		movePawn.setMinimumSize(new Dimension(100, 50));
		movePawn.setPreferredSize(new Dimension(100, 50));
		movePawn.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		movePawn.setEnabled(false);

		undoMove.setText("Undo Move");
		undoMove.setAlignmentX(Component.CENTER_ALIGNMENT);
		undoMove.setMinimumSize(new Dimension(100, 50));
		undoMove.setPreferredSize(new Dimension(100, 50));
		undoMove.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		undoMove.setEnabled(false);

		endTurn.setText("End Turn");
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
		boardPanel.setLocation(220, 0);
		sideButtons.setSize(100, 600);
		sideButtons.setLocation(50, 0);

		// Setup Board Squares
		boardSquares = new JLabel[9][9];

		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				boardSquares[x][y] = new JLabel();
				boardSquares[x][y].setIcon(new ImageIcon(squareIMG));
				boardSquares[x][y].setSize(62, 62);
				boardSquares[x][y].setLocation(x * 62, y * 62);
				boardSquares[x][y].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						squareClicked(e);
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						squareEntered(e);

					}

					@Override
					public void mouseExited(MouseEvent e) {
						squareExited(e);
					}
				});
				boardPanel.add(boardSquares[x][y]);
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
				int confirmed = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to quit the game?",
						"Exit Game?", JOptionPane.YES_NO_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {
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

	public void endPlayersTurn() {
		quoridor.endPlayersTurn();
		currentState = GameState.MovingPlayer;
		turnTaken = false;
		activateState();
		updateButtons();
	}

	public void updateButtons() {
		if (turnTaken == false) {
			vWall.setEnabled(true);
			hWall.setEnabled(true);
			movePawn.setEnabled(true);
			undoMove.setEnabled(false);
			endTurn.setEnabled(false);
		} else {
			vWall.setEnabled(false);
			hWall.setEnabled(false);
			movePawn.setEnabled(false);
			undoMove.setEnabled(true);
			endTurn.setEnabled(true);
		}
	}

	public void activateState() {
		switch (currentState.toString()) {
		case "MovingPlayer":
			if (turnTaken == false){
				validMoves = quoridor.getValidMoves();
				for (int i = 0; i < validMoves.size(); i++) {
					boardSquares[validMoves.get(i).getX()][validMoves.get(i).getY()].setIcon(new ImageIcon(squareHighlightedIMG));
				}
			}
			break;
		}
	}
	

	public Coordinate getSquareCoordinates(MouseEvent e) {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (e.getComponent() == boardSquares[x][y]) {
					return new Coordinate(x, y);
				}
			}
		}
		return null;
	}

	public void squareExited(MouseEvent e) {
	}

	public void squareEntered(MouseEvent e) {
	}

	public void squareClicked(MouseEvent e) {
		Coordinate squareCoord = getSquareCoordinates(e);
		switch (currentState.toString()) {
		case "MovingPlayer":
			if (turnTaken == false){
				for (int i = 0; i < validMoves.size(); i++) {
					if (squareCoord.compare(validMoves.get(i)) == true){
						quoridor.movePlayersPawn(translateVCToMC(squareCoord));
						turnTaken = true;
						updateBoardDisplay();
						activateState();
						updateButtons();
						break;
					}
				}
			}
			break;
		}
		
	}

	public void updateBoardDisplay() {
		playersTurnLabel.setText("Players Turn : "+(quoridor.getPlayersTurn()+1));
		Player[] players = quoridor.getPlayers();
		int i = 0;
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				boardSquares[x][y].setIcon(new ImageIcon(squareIMG));
			}
		}
		for (Player player : players) {
			i++;
			if (player.getPawnLocation() != null) {
				Coordinate pawnLocation = translateMCToVC(player.getPawnLocation());
				boardSquares[pawnLocation.getX()][pawnLocation.getY()].setIcon(new ImageIcon(getPlayersImage(i)));
			}
		}
		Wall[] walls = quoridor.getWalls();
		for (Wall wall : walls) {
			if (wall != null) {

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

	public Coordinate translateVCToMC(Coordinate coord) {
		return new Coordinate(coord.getX() * 2, coord.getY() * 2);
	}
	
	public Coordinate translateMCToVC(Coordinate coord) {
		return new Coordinate(coord.getX() / 2, coord.getY() / 2);
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
