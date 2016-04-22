package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


/**
 * This class creates the panel which holds the main menu.
 */
public class MainMenuView implements ViewPanel {
	private JPanel panel;
	private Language currentLanguage;
	
	public MainMenuView() {
		// Set the current language
		currentLanguage = new Language();
		ResourceBundle messages = currentLanguage.getMessages();
		
		final int blankSpace = 200;  // Blank border at the edges of the panel
		
		// Create the components
		ImageIcon logoImage = new ImageIcon(messages.getString("main_menu_title"));
		JLabel logo = new JLabel(logoImage);
		JButton newGameButton = new JButton();
		JButton optionsButton = new JButton();
		JButton exitButton = new JButton();

		// Set the properties of the components	
		newGameButton.setText(messages.getString("new_game"));
		newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		newGameButton.setMinimumSize(new Dimension(75, 50));
		newGameButton.setPreferredSize(new Dimension(75, 50));
		newGameButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		optionsButton.setText(messages.getString("options"));
		optionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		optionsButton.setMinimumSize(new Dimension(75, 50));
		optionsButton.setPreferredSize(new Dimension(75, 50));
		optionsButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		exitButton.setText(messages.getString("exit"));
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.setMinimumSize(new Dimension(75, 50));
		exitButton.setPreferredSize(new Dimension(75, 50));
		exitButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));		
		
		// Create containers to hold the components
		panel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		//Specify LayoutManagers
		panel.setLayout(new BorderLayout());
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		buttonPanel.setBorder(new EmptyBorder(0, blankSpace, blankSpace/2, blankSpace));
		
		// Add components to containers
		buttonPanel.add(newGameButton);
		buttonPanel.add(optionsButton);
		buttonPanel.add(exitButton);
		panel.add(logo, BorderLayout.NORTH);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		
		// Events
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				NewGameView ng = new NewGameView();
				ng.addToJFrame();
				ng.setVisible();
			}
		});
		
		optionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				OptionsView opt = new OptionsView();
				opt.addToJFrame();
				opt.setVisible();
			}
		});
		
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	/**
	 * This method returns the JPanel
	 * 
	 * @return JPanel panel
	 */
	public JPanel getJPanel() {
		return panel;
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
}