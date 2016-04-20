package gui;

import gameplay.Quoridor;
import gameplay.Rules;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * This class creates a JPanel which will display a new to allow the user to setup a new game.
 */
public class NewGameView implements ViewPanel {
	private JPanel panel;
	private final JComboBox gameMode;
	private final JComboBox gameRules;
	private Language currentLanguage;
	
	
	public NewGameView() {	
		// Set the current language
		currentLanguage = new Language();
		ResourceBundle messages = currentLanguage.getMessages();
		
		final int blankSpace = 200;  // Blank border at the edges of the panel
		
		//Options for the JComboBoxes
		String[] gamePlayersStrings = {messages.getString("2_player_game"), messages.getString("4_player_game")};
		String[] gameRulesStrings = {messages.getString("normal_rules"), messages.getString("challenge_rules")};
		
		// Create the components
		gameMode = new JComboBox(gamePlayersStrings);
		gameRules = new JComboBox(gameRulesStrings);
		JLabel gameModeLabel = new JLabel(messages.getString("game_mode"));
		JLabel gameRulesLabel = new JLabel(messages.getString("game_rules"));
		
		JButton startGameButton = new JButton();
		JButton backButton = new JButton();
		
		ImageIcon headerImage = new ImageIcon(messages.getString("new_game_title"));
		JLabel header = new JLabel(headerImage);
		
		JLabel blankLabel = new JLabel(" ");
		
		// Set the properties of the components	
		gameMode.setSelectedIndex(0);
		gameRules.setSelectedIndex(0);
		
		startGameButton.setText(messages.getString("start"));
		startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startGameButton.setMinimumSize(new Dimension(75, 50));
		startGameButton.setPreferredSize(new Dimension(75, 50));
		startGameButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		backButton.setText(messages.getString("back"));
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.setMinimumSize(new Dimension(75, 50));
		backButton.setPreferredSize(new Dimension(75, 50));
		backButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		// Create containers to hold the components
		JPanel buttonPanel = new JPanel();
		JPanel newGamePanel = new JPanel();
		panel = new JPanel();
		
		//Specify LayoutManagers
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		panel.setLayout(new BorderLayout());
		
		GridLayout newGameLayout = new GridLayout(0, 2);
		newGamePanel.setLayout(newGameLayout);
		
		newGamePanel.setBorder(new EmptyBorder(0, blankSpace, blankSpace/4, blankSpace));
		buttonPanel.setBorder(new EmptyBorder(0, blankSpace, blankSpace/2, blankSpace));
		
		// Add components to containers
		newGamePanel.add(gameModeLabel);
		newGamePanel.add(gameMode);
		newGamePanel.add(blankLabel);
		newGamePanel.add(gameRulesLabel);
		newGamePanel.add(gameRules);
		newGamePanel.add(blankLabel);
		buttonPanel.add(startGameButton);
		buttonPanel.add(backButton);
		panel.add(header, BorderLayout.NORTH);
		panel.add(newGamePanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		// Events
		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				Rules rules = setupRules();
				GameView gv = new GameView(rules);
				gv.addToJFrame();
				gv.setVisible();
				gv.getJPanel().setFocusable(true);
				gv.getJPanel().requestFocusInWindow();
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				MainMenuView mm = new MainMenuView();
				mm.addToJFrame();
				mm.setVisible();
			}
		});
	}
	
	public Rules setupRules(){
		Rules theRules = null;
		int playerCount = 0;
		int wallCount = 0;
		String[] colours = null;
		switch(gameMode.getSelectedItem().toString()){
		case "2":
			playerCount = 2;
			wallCount = 10;
			colours = new String[]{"red", "blue"};
			break;
		case "4":
			playerCount = 4;
			wallCount = 5;
			colours = new String[]{"red", "blue", "green", "yellow"};
			break;
		}
		theRules = new Rules(wallCount, playerCount, colours, gameRules.getSelectedItem().toString());
		return theRules;
	}
	
	/**
	 * Make the panel visible
	 */
	public void setVisible(){
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
