package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class creates the panel which displays the options menu
 */
public class OptionsView implements ViewPanel {
	private JPanel panel; 
	private Language currentLanguage;
	
	private JComboBox language;
	
	public OptionsView() {
		// Set the current language
		currentLanguage = new Language();
		ResourceBundle messages = currentLanguage.getMessages();
		
		final int blankSpace = 200;  // Blank border at the edges of the panel
		
		//Options for the JComboBoxes
		String[] languages = {"English", "Polski", "Zulu"};
		
		// Create the components
		ImageIcon headerImage = new ImageIcon(messages.getString("options_title"));
		JLabel header = new JLabel(headerImage);
		
		JButton backButton = new JButton();
		JButton keyBindingsButton = new JButton();
		
		language = new JComboBox(languages);
		updateComboBoxView();
		JLabel languageLabel = new JLabel(messages.getString("language"));
		
		// Set the properties of the components	
		backButton.setText(messages.getString("back"));
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.setMinimumSize(new Dimension(75, 50));
		backButton.setPreferredSize(new Dimension(75, 50));
		backButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		keyBindingsButton.setText(messages.getString("key_bindings"));
		keyBindingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		keyBindingsButton.setMinimumSize(new Dimension(75, 50));
		keyBindingsButton.setPreferredSize(new Dimension(75, 50));
		keyBindingsButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		// Create containers to hold the components
		JPanel optionsPanel = new JPanel();
		panel = new JPanel();
		JPanel twoWidePanel = new JPanel();
		
		// Specify LayoutManagers
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		panel.setLayout(new BorderLayout());
		
		GridLayout newGameLayout = new GridLayout(0, 2);
		twoWidePanel.setLayout(newGameLayout);
		
		optionsPanel.setBorder(new EmptyBorder(0, blankSpace, blankSpace/2, blankSpace));
		
		// Add components to containers
		twoWidePanel.add(languageLabel);
		twoWidePanel.add(language);
		optionsPanel.add(twoWidePanel);
		optionsPanel.add(keyBindingsButton);
		optionsPanel.add(backButton);
		panel.add(header, BorderLayout.NORTH);
		panel.add(optionsPanel, BorderLayout.SOUTH);
		
		// Events
		keyBindingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				KeyBindingsView kb = new KeyBindingsView();
				kb.addToJFrame();
				kb.setVisible();
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
		
		language.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrintWriter writer;
				try {
					writer = new PrintWriter("textFiles/currentLanguage.txt");
					if(language.getSelectedItem() == "English"){
						writer.print("en");
					} else if(language.getSelectedItem() == "Polski"){
						writer.print("pl");
					} else if(language.getSelectedItem() == "Zulu"){
						writer.print("zu");
					}
					writer.close();
				} catch (FileNotFoundException e1) {
					System.out.println("No file found.");
				}
				updateComboBoxView();
			}
		});
	}
	
	public void updateComboBoxView(){
		// Set the combo box to display current language
		String tmpLng = currentLanguage.getLanguage();
		if(tmpLng.equals("en")){
			language.setSelectedItem("English");
		} else if(tmpLng.equals("pl")) {
			language.setSelectedItem("Polski");
		} else if(tmpLng.equals("zu")) {
			language.setSelectedItem("Zulu");
		}
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
