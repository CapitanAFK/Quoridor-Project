package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.*;

import gameplay.Statistics;

public class StatsView implements ViewPanel {
	private JPanel panel;
	private Language currentLanguage = new Language();
	ResourceBundle messages = currentLanguage.getMessages();

	public static void main(String[] args) {
		JFrame frame;

		// Create containers to hold the components
		frame = new JFrame("Title");

		// Set the default operations and characteristics
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		frame.setMinimumSize(new Dimension(800, 600));
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		// Initialise the Main Menu class
		StatsView menu = new StatsView();

		// Add components to containers
		frame.add(menu.getJPanel(), BorderLayout.CENTER);

		// Pack and make the frame visible
		frame.pack();
		frame.setVisible(true);

	}

	public StatsView() {
		// Create the components
		JButton mainMenuButton = new JButton();

		ImageIcon logoImage = new ImageIcon(messages.getString("stats_title"));
		JLabel logo = new JLabel(logoImage);

		// Set the properties of the components
		mainMenuButton.setText(messages.getString("back"));
		mainMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainMenuButton.setMinimumSize(new Dimension(75, 50));
		mainMenuButton.setPreferredSize(new Dimension(75, 50));
		mainMenuButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		// Create containers to hold the components
		panel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		JScrollPane scrollPane = new JScrollPane(getTable());
		getTable().setFillsViewportHeight(true);
		
		// Specify LayoutManagers
		panel.setLayout(new BorderLayout());
		buttonPanel.setSize(100, 100);

		// Add components to containers
		buttonPanel.add(mainMenuButton);
		panel.add(logo, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);

		// Events

	}

	public JTable getTable(){
		String[] columnNames = { messages.getString("name"), messages.getString("walls_placed"),
				messages.getString("steps_taken"), messages.getString("undos_taken"), messages.getString("walls_removed")};
				
		Object[][] data = {
				{"Player 1", new Integer(6), new Integer(6),new Integer(6),new Integer(6)},
				{"Player 2", new Integer(6), new Integer(6),new Integer(6),new Integer(6)},
				{"Player 3", new Integer(6), new Integer(6),new Integer(6),new Integer(6)},
				{"Player 4", new Integer(6), new Integer(6),new Integer(6),new Integer(6)}
		};
		
		JTable table = new JTable(data, columnNames);
		
		return table;
	}

	public JPanel getJPanel() {
		return panel;
	}

	public void setVisible() {
		panel.setVisible(true);
	}

	public void addToJFrame() {
		GUI.getJFrame().add(panel, BorderLayout.CENTER);
	}

}
