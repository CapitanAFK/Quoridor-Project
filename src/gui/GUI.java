package gui;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;

/**
 * This class creates a GUI which allows the user to interact with the game.
 * By default the main menu screen opens when the game launches
 * 
 * @author COMP7
 * @version v1.0, 26/04/2016
 */
public class GUI {
	private static JFrame frame;
	private Language currentLanguage;

	/**
	 * Constructor for the GUI class
	 * It initialises a JFrame and an object of class MainMenuView is passed to it
	 */
	public GUI() {	
		// Set the current language
		currentLanguage = new Language();
		ResourceBundle messages = currentLanguage.getMessages();
		
		// Create containers to hold the components
		frame = new JFrame(messages.getString("project_title"));
		
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

	/**
	 * Main method which creates a single objects of class GUI
	 * 
	 * @param args
	 */
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
