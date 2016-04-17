package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Gameplay.Quoridor;
import Gameplay.Rules;

/**
 * This class creates a JPanel which will display a new to allow the user to setup a new game.
 */
public class NewGameView implements ViewPanel {
	private JPanel panel;
	
	public NewGameView() {	
		
		final int blankSpace = 200;  // Blank border at the edges of the panel
		
		//Options for the JComboBoxes
		String[] gameModes = {"2 Player Game", "4 Player Game", "Tournament Mode", "Challenge Mode"};
		
		// Create the components
		final JComboBox gameMode = new JComboBox(gameModes);
		JLabel gameModeLabel = new JLabel("Game Mode: ");
		
		JButton startGameButton = new JButton();
		JButton backButton = new JButton();
		
		ImageIcon headerImage = new ImageIcon("images/newGame.png");
		JLabel header = new JLabel(headerImage);
		
		JLabel blankLabel = new JLabel(" ");
		
		// Set the properties of the components	
		gameMode.setSelectedIndex(0);
		
		startGameButton.setText("Start");
		startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startGameButton.setMinimumSize(new Dimension(75, 50));
		startGameButton.setPreferredSize(new Dimension(75, 50));
		startGameButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		backButton.setText("Back");
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
		buttonPanel.add(startGameButton);
		buttonPanel.add(backButton);
		panel.add(header, BorderLayout.NORTH);
		panel.add(newGamePanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		// Events
		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				Rules rules = setupRules(gameMode.getSelectedItem().toString());
				GameView gv = new GameView(rules);
				gv.addToJFrame();
				gv.setVisible();
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
	
	public Rules setupRules(String selectedItem){
		System.out.println(selectedItem);
		Rules theRules = null;
		switch(selectedItem){
		case "2 Player Game":
			theRules = new Rules(10,2,new String[]{"red", "blue"});
			break;
		case "4 Player Game":
			theRules = new Rules(5,4,new String[]{"red", "blue", "green", "yellow"});
			break;
		case "Tournament Mode":
			theRules = new Rules(10,2,new String[]{"red", "blue"});
			break;
		case "Challenge Mode":
			theRules = new Rules(10,2,new String[]{"red", "blue"});
			break;
		}
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
