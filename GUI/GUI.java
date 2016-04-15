package gui;

import java.awt.*;
import javax.swing.*;

/**
 * This class creates a GUI which allows the user to interact with the game.
 * It works by creating objects of appropriate classes and places them in the JFrame.
 */
public class GUI {
	private static JFrame frame;
	// sizeVaraible is used to handle the resolution
	private static int sizeVaraible = 1;

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
		
		frame.setMinimumSize(new Dimension(800 * sizeVaraible, 600 * sizeVaraible));
		frame.setPreferredSize(new Dimension(800 * sizeVaraible, 600 * sizeVaraible));
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
	 * This method returns a value for sizeVaraible
	 * 
	 * @return int sizeVaraible
	 */
	public static int getSizeVaraible() {
		return sizeVaraible;
	}
	
	/**
	 * This method is used to set the size variable to i
	 * @param int i
	 */
	public void setSizeVaraible(int i) {
		sizeVaraible = i;
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
