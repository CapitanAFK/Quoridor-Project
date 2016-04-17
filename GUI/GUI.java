package GUI;

import java.awt.*;
import javax.swing.*;

/**
 * This class creates a GUI which allows the user to interact with the game.
 * It works by creating objects of appropriate classes and places them in the JFrame.
 */
public class GUI {
	private static JFrame frame;

	/**
	 * A TEMPORARY main method used to check the UI during construction
	 * This method will later be removed
	 */
	public GUI() {		
		// Create containers to hold the components
		frame = new JFrame("Project's Title COMP7");
		
		// Set the default operations and characteristics
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		frame.setMinimumSize(new Dimension(800, 600));
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		// Initialise the Main Menu class
		MainMenuView menu = new MainMenuView();
		
		// Add components to containers
		frame.add(menu.getJPanel(), BorderLayout.CENTER);
		
		// Pack and make the frame visible
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		GUI gui = new GUI();
	}
	
	/**
	 * This method returns the main JFrame
	 * 
	 * @return JFrame frame
	 */
	public static JFrame getJFrame() {
		return frame;
	}
}
