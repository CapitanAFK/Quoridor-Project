package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Model.Board;

/**
 * This class creates the panel which is displayed when the player plays the game.
 */
public class GameView implements ViewPanel {
	private JPanel panel;
	
	/**
	 * This constructor creates and object of class Board and an appropriate panel which is to be added to the main JFrame
	 * THE COLOURS ARE TEMPORARY AND WILL BE REPLACED BY SPRITES LATER
	 */
	public GameView(){
		//setup Images
		Image img = null;
		try {
			img = ImageIO.read(new File("Images/Square.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Image resizedImage =  img.getScaledInstance(62, 62, BufferedImage.TYPE_INT_ARGB);
		
		final int blankSpace = 20;  // Blank border at the edges of the panel
		
		// Create the components
		JButton exitButton = new JButton();
		JButton vWall = new JButton();
		JButton hWall = new JButton();
		
		JLabel square = null;
		
		// Set the properties of the components	
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
		
		hWall.setText("Hor. Wall");
		hWall.setAlignmentX(Component.CENTER_ALIGNMENT);
		hWall.setMinimumSize(new Dimension(100, 50));
		hWall.setPreferredSize(new Dimension(100, 50));
		hWall.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		// Create containers to hold the components
		JPanel sideButtons = new JPanel();
		JPanel boardLayout = new JPanel();
		panel = new JPanel();
		
		// Specify LayoutManagers
		boardLayout.setPreferredSize(new Dimension(600, 600));
		boardLayout.setLayout(new GridLayout(9,9,-50,0));
		boardLayout.setLocation(200, 0);
		sideButtons.setPreferredSize(new Dimension(200, 600));
		sideButtons.setLocation(0, 0);
		panel.setLayout(new BorderLayout());
		
		//Setup Board
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				square = new JLabel();
				square.setIcon(new ImageIcon(resizedImage));
				square.setPreferredSize(new Dimension(62,62));
				square.setHorizontalAlignment(JLabel.RIGHT);
				boardLayout.add(square);
			}
		}
		
		// Add components to containers
		sideButtons.add(hWall);
		sideButtons.add(vWall);
		sideButtons.add(exitButton);
		panel.add(sideButtons, BorderLayout.WEST);
		panel.add(boardLayout, BorderLayout.EAST);
		
		panel.setBorder(new EmptyBorder(blankSpace, blankSpace, blankSpace, blankSpace));
		
		// Events
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				MainMenuView mm = new MainMenuView();
				mm.addToJFrame();
				mm.setVisible();
			}
		});
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
	 * @return JPanel panel
	 */
	public JPanel getJPanel() {
		return panel;
	}	
}
