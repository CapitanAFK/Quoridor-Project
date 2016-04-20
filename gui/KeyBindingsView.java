package gui;

import gameplay.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

/**
 * This class creates the panel which displays the key bindings and allows the
 * user to change them
 */
public class KeyBindingsView implements ViewPanel {
	private JPanel panel;
	private String keyToChange;
	private Controls controls;
	
	private int keyCode;
	private char key;
	public String keyPressed;
	
	private Language currentLanguage;
	
	//Buttons
	private final JButton backButton = new JButton();
	private final JButton upButton = new JButton();
	private final JButton downButton = new JButton();
	private final JButton leftButton = new JButton();
	private final JButton rightButton = new JButton();
	private final JButton veritcalButton = new JButton();
	private final JButton horizontalButton = new JButton();
	private final JButton undoButton = new JButton();
	private final JButton endTurnButton = new JButton();
	
	public KeyBindingsView() {
		// Set the current language
		currentLanguage = new Language();
		ResourceBundle messages = currentLanguage.getMessages();
		
		// Set the controls
		controls = new Controls();
		keyToChange = null;

		final int blankSpace = 200; // Blank border at the edges of the panel

		// Create the components
		ImageIcon headerImage = new ImageIcon(messages.getString("key_bindings_title"));
		JLabel header = new JLabel(headerImage);

		JLabel upLabel = new JLabel(messages.getString("move_up"));
		JLabel downLabel = new JLabel(messages.getString("move_down"));
		JLabel leftabel = new JLabel(messages.getString("move_left"));
		JLabel rightLabel = new JLabel(messages.getString("move_right"));
		JLabel verticalLabel = new JLabel(messages.getString("place_ver_wall"));
		JLabel horizontalLabel = new JLabel(messages.getString("place_hor_wall"));
		JLabel undoLabel = new JLabel(messages.getString("undo_kb"));
		JLabel endTurnLabel = new JLabel(messages.getString("end_turn_kb"));

		// Set the properties of the components
		backButton.setText(messages.getString("back"));
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.setMinimumSize(new Dimension(75, 50));
		backButton.setPreferredSize(new Dimension(75, 50));
		backButton.setMaximumSize(new Dimension(Short.MAX_VALUE,
				Short.MAX_VALUE));

		upButton.setText(controls.getUp());
		downButton.setText(controls.getDown());
		leftButton.setText(controls.getLeft());
		rightButton.setText(controls.getRight());
		veritcalButton.setText(controls.getVerticalWall());
		horizontalButton.setText(controls.getHorizontalWall());
		undoButton.setText(controls.getUndo());
		endTurnButton.setText(controls.getEndTurn());

		// Create containers to hold the components
		JPanel buttonPanel = new JPanel();
		JPanel controlPanel = new JPanel();
		panel = new JPanel();

		// Specify LayoutManagers
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		GridLayout controlLayout = new GridLayout(0, 2);
		controlPanel.setLayout(controlLayout);
		panel.setLayout(new BorderLayout());
		controlPanel.setBorder(new EmptyBorder(0, blankSpace, blankSpace / 4,
				blankSpace));
		buttonPanel.setBorder(new EmptyBorder(0, blankSpace, blankSpace / 2,
				blankSpace));

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
		controlPanel.add(undoLabel);
		controlPanel.add(undoButton);
		controlPanel.add(endTurnLabel);
		controlPanel.add(endTurnButton);
		buttonPanel.add(backButton);
		panel.add(header, BorderLayout.NORTH);
		panel.add(controlPanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);

		// Events
		leftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				// Send which key is meant to be changed
				keyToChange = "left";
				
		        // Change the key
				checkKeyStroke();
			}
			
		});
		
		rightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Send which key is meant to be changed
				keyToChange = "right";
				
				// Change the key
				checkKeyStroke();
			}
			
		});
		
		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Send which key is meant to be changed
				keyToChange = "up";
				
				// Change the key
				checkKeyStroke();
			}
			
		});
		
		downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Send which key is meant to be changed
				keyToChange = "down";
					
				// Change the key
				checkKeyStroke();
			}
			
		});
		
		veritcalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Send which key is meant to be changed
				keyToChange = "ver";
					
				// Change the key
				checkKeyStroke();
			}
			
		});
		
		horizontalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Send which key is meant to be changed
				keyToChange = "hor";

				// Change the key
				checkKeyStroke();
			}
			
		});
		
		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Send which key is meant to be changed
				keyToChange = "undo";

				// Change the key
				checkKeyStroke();
			}
			
		});
		
		endTurnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Send which key is meant to be changed
				keyToChange = "endTurn";

				// Change the key
				checkKeyStroke();
			}
			
		});

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				OptionsView opt = new OptionsView();
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

	public void checkKeyStroke(){
    	KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
					switch(e.getID()){
					case KeyEvent.KEY_PRESSED:
						keyCode = e.getKeyCode();
						key = (char)keyCode;
	                   	keyPressed = "" + key;
	                   	PrintWriter writer;	                   	
	                   	
	                   	if(keyToChange == "left"){
							try {
								writer = new PrintWriter("textFiles/controls/left.txt");
								writer.print(keyPressed);
								writer.close();
								controls.setKeys("textFiles/controls/left.txt", "left");
								leftButton.setText(controls.getLeft());
							} catch (FileNotFoundException ex) {
								System.out.println("File not found.");
							}
	                   	} else if(keyToChange == "right"){
							try {
								writer = new PrintWriter("textFiles/controls/right.txt");
								writer.print(keyPressed);
								writer.close();
								controls.setKeys("textFiles/controls/right.txt", "right");
								rightButton.setText(controls.getRight());
							} catch (FileNotFoundException ex) {
								System.out.println("File not found.");
							}
	                   	} else if(keyToChange == "up"){
							try {
								writer = new PrintWriter("textFiles/controls/up.txt");
								writer.print(keyPressed);
								writer.close();
								controls.setKeys("textFiles/controls/up.txt", "up");
								upButton.setText(controls.getUp());
							} catch (FileNotFoundException ex) {
								System.out.println("File not found.");
							}
	                   	} else if(keyToChange == "down"){
							try {
								writer = new PrintWriter("textFiles/controls/down.txt");
								writer.print(keyPressed);
								writer.close();
								controls.setKeys("textFiles/controls/down.txt", "down");
								downButton.setText(controls.getDown());
							} catch (FileNotFoundException ex) {
								System.out.println("File not found.");
							}
	                   	} else if(keyToChange == "ver"){
							try {
								writer = new PrintWriter("textFiles/controls/ver.txt");
								writer.print(keyPressed);
								writer.close();
								controls.setKeys("textFiles/controls/ver.txt", "ver");
								veritcalButton.setText(controls.getVerticalWall());
							} catch (FileNotFoundException ex) {
								System.out.println("File not found.");
							}
	                   	} else if(keyToChange == "hor"){
							try {
								writer = new PrintWriter("textFiles/controls/hor.txt");
								writer.print(keyPressed);
								writer.close();
								controls.setKeys("textFiles/controls/hor.txt", "hor");
								horizontalButton.setText(controls.getHorizontalWall());
							} catch (FileNotFoundException ex) {
								System.out.println("File not found.");
							}
	                   	} else if(keyToChange == "undo"){
							try {
								writer = new PrintWriter("textFiles/controls/undo.txt");
								writer.print(keyPressed);
								writer.close();
								controls.setKeys("textFiles/controls/undo.txt", "undo");
								undoButton.setText(controls.getUndo());
							} catch (FileNotFoundException ex) {
								System.out.println("File not found.");
							}
	                   	} else if(keyToChange == "endTurn"){
							try {
								writer = new PrintWriter("textFiles/controls/endTurn.txt");
								writer.print(keyPressed);
								writer.close();
								controls.setKeys("textFiles/controls/endTurn.txt", "endTurn");
								endTurnButton.setText(controls.getEndTurn());
							} catch (FileNotFoundException ex) {
								System.out.println("File not found.");
							}
	                   	} 
				}
				return false;
			}
    	 
    	});
    }	
}