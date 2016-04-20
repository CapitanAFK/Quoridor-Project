package gui;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;

/**
 * This class creates a GUI which allows the user to interact with the game.
 * It works by creating objects of appropriate classes and places them in the JFrame.
 */
public class GUI {
	private static JFrame frame;
	private Language currentLanguage;

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
