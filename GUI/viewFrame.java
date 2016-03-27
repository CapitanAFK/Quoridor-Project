package GUI;

import javax.swing.*;

/**
 * This interface provides a template for the views/frames of each window in the GUI.
 */
public interface viewFrame {

	// Abstract method declaration
	public JPanel getJPanel();
	public void setVisible();
	public void addToJFrame();
	
}
