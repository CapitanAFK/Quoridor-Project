package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class creates the panel which displays the options menu
 */
public class OptionsView implements ViewPanel {
	private JPanel panel; 
	
	public OptionsView() {
		final int blankSpace = 200;  // Blank border at the edges of the panel
		//Options for the JComboBoxes
		String[] languages = {"English"};
		
		// Create the components
		ImageIcon headerImage = new ImageIcon("images/options.png");
		JLabel header = new JLabel(headerImage);
		
		JButton backButton = new JButton();
		JButton keyBindingsButton = new JButton();
		
		JComboBox language = new JComboBox(languages);
		JLabel languageLabel = new JLabel("Language: ");
		
		// Set the properties of the components	
		backButton.setText("Back");
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.setMinimumSize(new Dimension(75, 50));
		backButton.setPreferredSize(new Dimension(75, 50));
		backButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		keyBindingsButton.setText("Key Bindings");
		keyBindingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		keyBindingsButton.setMinimumSize(new Dimension(75, 50));
		keyBindingsButton.setPreferredSize(new Dimension(75, 50));
		keyBindingsButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		//TEMP WAY OF CHANGING LANGUAGE WILL CHANGE TO LOCALISATION FILES IN THE FUTURE
		// Change Language
		//if(){
		//	backButton.setText("Wroc");
		//	keyBindingsButton.setText("Przyciski");
		//	languageLabel = new JLabel("Jezyk");
		//}else {
			//Do nothing for now
		//}
		
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
