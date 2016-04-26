package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import gameplay.Statistics;

public class StatsView implements ViewPanel {
	private JPanel panel;
	private Language currentLanguage = new Language();
	private ResourceBundle messages = currentLanguage.getMessages();
	private Statistics stats;

	public StatsView(Statistics stats) {
		this.stats = stats;
		// Create the components
		JButton mainMenuButton = new JButton();
		JLabel winnerLabel = new JLabel(messages.getString("winner") + stats.getWinnerId(),SwingConstants.CENTER);

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
		JPanel statsPanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		JTable table = getTable();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new EmptyBorder(0,30,0,30));

		// Specify LayoutManagers
		panel.setLayout(new BorderLayout());
		statsPanel.setLayout(new BorderLayout());
		buttonPanel.setSize(100, 100);

		// Add components to containers
		buttonPanel.add(mainMenuButton);
		statsPanel.add(winnerLabel, BorderLayout.NORTH);
		statsPanel.add(scrollPane, BorderLayout.CENTER);
		panel.add(logo, BorderLayout.NORTH);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		panel.add(statsPanel);

		// Events

		mainMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				MainMenuView mm = new MainMenuView();
				mm.addToJFrame();
				mm.setVisible();
			}
		});

		updateHighScores();
	}

	public void updateHighScores(){
		boolean[] isBot = stats.getIsBot();
		String winnerName = stats.getWinnerId();
		String[] playerNames = stats.getPlayerNames();
		
		int winnerID = 0;
		for (int i = 0; i < playerNames.length; i++) {
			if (winnerName.equals(playerNames)){
				winnerID = i;
				break;
			}
		}
		boolean skipUpdate = false;
		if (isBot[winnerID] == true){
			skipUpdate = true;
		}
		
		if (skipUpdate == false){
			int wallsPlaced = stats.getWallsPlaced()[winnerID];
			int stepsTaken = stats.getStepsTaken()[winnerID];
			int undosTaken = stats.getUndosTaken()[winnerID];
			int wallsRemoved = stats.getWallsRemoved()[winnerID];
			int score = (wallsPlaced+stepsTaken+wallsRemoved)-undosTaken;
			String[] playerScore = new String[2];
			playerScore[0] = playerNames[winnerID];
			playerScore[1] = score+"";
			String[][] allScores = compareHighScores(playerScore);
			if (allScores != null){
				PrintWriter writer;	
				try {
					writer = new PrintWriter("textFiles/highScores.txt");
					for (int i = 0; i < allScores.length; i++){
						writer.println(allScores[i][0]);
						writer.println(allScores[i][1]);
					}
					writer.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public String[][] compareHighScores(String[] score){
		try {
			// Read the file
			FileReader fr = new FileReader("textFiles/highScores.txt");
			
			// Wrap FileReader in BufferedReader
			BufferedReader br = new BufferedReader(fr);	
			
			String[][] scores = new String[10][2];
			
			String line;
			int x = 0;
			while ((line = br.readLine()) != null ){
				scores[x][0] = line;
				scores[x][1] = br.readLine();
				x++;
			}
			boolean update = false;
			String[][] tempScores = new String[10][2];
			for (int i = 0; i < scores.length; i++) {
				tempScores[i] = scores[i];
				if (scores[i][0] != null){
					if (Integer.parseInt(scores[i][1]) < Integer.parseInt(score[1])){
						for (int j = i; j < scores.length-1; j++) {
							tempScores[j+1] = scores[j];
						}
						tempScores[i] = score;
						scores = tempScores;
						update = true;
						break;
					}
				}
			}
			if (update == true){
				return scores;
			} else {
				return null;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public JTable getTable(){
		String[] columnNames = { messages.getString("name"), messages.getString("walls_placed"),
				messages.getString("steps_taken"), messages.getString("undos_taken"), messages.getString("walls_removed")};

		String[] playerNames = stats.getPlayerNames();
		boolean[] isBot = stats.getIsBot();
		int playerCount = 0;
		for (int i = 0; i < playerNames.length; i++) {
			if (isBot[i] == false){
				playerCount++;
			}
		}
		int[] wallsPlaced = stats.getWallsPlaced();
		int[] stepsTaken = stats.getStepsTaken();
		int[] undosTaken = stats.getUndosTaken();
		int[] wallsRemoved = stats.getWallsRemoved();
		Object[][] data = new Object[playerCount][5];
		for (int i = 0; i < playerCount; i++) {
			if (isBot[i] == false){
				data[i][0] = playerNames[i];
				data[i][1] = wallsPlaced[i];
				data[i][2] = stepsTaken[i];
				data[i][3] = undosTaken[i];
				data[i][4] = wallsRemoved[i];
			}
		}

		JTable table = new JTable(data, columnNames);
		table.setEnabled(false);

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
