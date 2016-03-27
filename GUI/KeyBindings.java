package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * This class creates the panel which displays the key bindings and allows the user to change them
 */
public class KeyBindings implements viewFrame {
	private JPanel panel;
	
	public KeyBindings() {
		final int blankSpace = 200;  // Blank border at the edges of the panel
		Controls controls = new Controls();
		
		// Create the components
		ImageIcon headerImage = new ImageIcon("images/keyBindings.png");
		JLabel header = new JLabel(headerImage);
		
		JButton backButton = new JButton();
		JButton upButton = new JButton();
		JButton downButton = new JButton();
		JButton leftButton = new JButton();
		JButton rightButton = new JButton();
		JButton veritcalButton = new JButton();
		JButton horizontalButton = new JButton();
		
		JLabel upLabel = new JLabel("Move Up: ");
		JLabel downLabel = new JLabel("Move Down: ");
		JLabel leftabel = new JLabel("Move Left: ");
		JLabel rightLabel = new JLabel("Move Right: ");
		JLabel verticalLabel = new JLabel("Place a Vertical Wall: ");
		JLabel horizontalLabel = new JLabel("Place a Horizontal Wall: ");
		
		// Set the properties of the components	
		backButton.setText("Back");
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.setMinimumSize(new Dimension(75, 50));
		backButton.setPreferredSize(new Dimension(75, 50));
		backButton.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		
		upButton.setText(controls.getUp());
		downButton.setText(controls.getDown());
		leftButton.setText(controls.getLeft());
		rightButton.setText(controls.getRight());
		veritcalButton.setText(controls.getVertical());
		horizontalButton.setText(controls.getHorizontal());
		
		// Create containers to hold the components
		JPanel buttonPanel = new JPanel();
		JPanel controlPanel = new JPanel();
		panel = new JPanel();
		
		// Specify LayoutManagers
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		GridLayout controlLayout = new GridLayout(0, 2);
		controlPanel.setLayout(controlLayout);
		panel.setLayout(new BorderLayout());
		controlPanel.setBorder(new EmptyBorder(0, blankSpace, blankSpace/4, blankSpace));
		buttonPanel.setBorder(new EmptyBorder(0, blankSpace, blankSpace/2, blankSpace));
		
		// Add components to containers
		controlPanel.add(upLabel);
		controlPanel.add(upButton);
		controlPanel.add(downLabel);
		controlPanel.add(downButton);
		controlPanel.add(leftabel);
		controlPanel.add(leftButton);
		controlPanel.add(rightLabel);
		controlPanel.add(rightButton);
		controlPanel.add(verticalLabel);
		controlPanel.add(veritcalButton);
		controlPanel.add(horizontalLabel);
		controlPanel.add(horizontalButton);
		buttonPanel.add(backButton);
		panel.add(header, BorderLayout.NORTH);
		panel.add(controlPanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		// Events
		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showOptionDialog(null, 
				        "Press the key you wish to assign.", 
				        "Change Key Binding", 
				        JOptionPane.OK_CANCEL_OPTION, 
				        JOptionPane.INFORMATION_MESSAGE, 
				        null, 
				        new String[]{"Cancel"}, // Buttons
				        "default");
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				Options opt = new Options();
				opt.addToJFrame();
				opt.setVisible();
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