package GUI;

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
public class Options implements viewFrame {
	private JPanel panel; 
	
	public Options() {
		final int blankSpace = 200;  // Blank border at the edges of the panel
		
		// Create the components
		ImageIcon headerImage = new ImageIcon("images/options.png");
		JLabel header = new JLabel(headerImage);
		
		JButton backButton = new JButton();
		JButton keyBindingsButton = new JButton();
		
		JLabel resolutonLabel = new JLabel("Resolution");
		JSlider resolutionSlider = new JSlider(JSlider.HORIZONTAL, 1, 3, GUI.getSizeVaraible());
		
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
		
		resolutionSlider.setMajorTickSpacing(1);
		resolutionSlider.setMinorTickSpacing(1);
		resolutionSlider.setPaintTicks(true);
		resolutionSlider.setPaintLabels(true);
		
		// Create containers to hold the components
		JPanel optionsPanel = new JPanel();
		panel = new JPanel();
		
		// Specify LayoutManagers
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		panel.setLayout(new BorderLayout());
		
		optionsPanel.setBorder(new EmptyBorder(0, blankSpace, blankSpace/2, blankSpace));
		
		// Add components to containers
		optionsPanel.add(resolutonLabel);
		optionsPanel.add(resolutionSlider);
		optionsPanel.add(keyBindingsButton);
		optionsPanel.add(backButton);
		panel.add(header, BorderLayout.NORTH);
		panel.add(optionsPanel, BorderLayout.SOUTH);
		
		// Events
		
		
		
		keyBindingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				KeyBindings kb = new KeyBindings();
				kb.addToJFrame();
				kb.setVisible();
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
