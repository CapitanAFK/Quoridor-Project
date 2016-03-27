package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * This class creates a JPanel which will display a new to allow the user to setup a new game.
 */
public class NewGame implements viewFrame {
	private JPanel panel;
	
	public NewGame() {
		final int blankSpace = 200;  // Blank border at the edges of the panel
		//Options for the JComboBoxes
		String[] gameModes = {"Game Mode 1", "Game Mode 2", "Game Mode 3"};
		String[] timeLimits = {"Time Limit 1", "Time Limit 2", "Time Limit 3"};
		String[] wallLimits = {"Wall Limit 1", "Wall Limit 2", "Wall Limit 3"};
		
		// Create the components
		JComboBox gameMode = new JComboBox(gameModes);
		JLabel gameModeLabel = new JLabel("Game Mode: ");
		
		JComboBox timeLimit = new JComboBox(timeLimits);
		JLabel timeLimitLabel = new JLabel("Time Limit: ");
		
		JComboBox wallLimit = new JComboBox(wallLimits);
		JLabel wallLimitLabel = new JLabel("Wall Limit: ");
		
		JButton startGameButton = new JButton();
		JButton backButton = new JButton();
		
		ImageIcon headerImage = new ImageIcon("images/newGame.png");
		JLabel header = new JLabel(headerImage);
		
		JLabel blankLabel = new JLabel(" ");
		
		// Set the properties of the components	
		gameMode.setSelectedIndex(0);
		timeLimit.setSelectedIndex(0);
		wallLimit.setSelectedIndex(0);
		
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
		newGamePanel.add(timeLimitLabel);
		newGamePanel.add(timeLimit);
		newGamePanel.add(blankLabel);
		newGamePanel.add(wallLimitLabel);
		newGamePanel.add(wallLimit);
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
				GameView gv = new GameView();
				gv.addToJFrame();
				gv.setVisible();
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				MainMenu mm = new MainMenu();
				mm.addToJFrame();
				mm.setVisible();
			}
		});
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
