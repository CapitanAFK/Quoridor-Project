package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Model.Board;

/**
 * This class creates the panel which is displayed when the player plays the game.
 */
public class GameView implements viewFrame {
	private JPanel panel;
	
	/**
	 * This constructor creates and object of class Board and an appropriate panel which is to be added to the main JFrame
	 * THE COLOURS ARE TEMPORARY AND WILL BE REPLACED BY SPRITES LATER
	 */
	public GameView(){
		final int blankSpace = 20;  // Blank border at the edges of the panel
		
		// Create the components
		JButton exitButton = new JButton();
		
		// Set the properties of the components	
		exitButton.setText("Give Up");
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.setMinimumSize(new Dimension(75, 50));
		exitButton.setPreferredSize(new Dimension(75, 50));
		exitButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		// Create containers to hold the components
		JPanel sideButtons = new JPanel();
		JPanel topButtons = new JPanel();
		panel = new JPanel();
		
		// Specify LayoutManagers
		sideButtons.setLayout(new BoxLayout(sideButtons, BoxLayout.Y_AXIS));
		topButtons.setLayout(new BoxLayout(topButtons, BoxLayout.X_AXIS));
		panel.setLayout(new BorderLayout());
		
		// Add components to containers
		sideButtons.add(exitButton);
		panel.add(sideButtons, BorderLayout.WEST);
		panel.add(topButtons, BorderLayout.NORTH);
		
		panel.setBorder(new EmptyBorder(blankSpace, blankSpace, blankSpace, blankSpace));
		
		// Events
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				MainMenu mm = new MainMenu();
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
