package GUI;

import java.awt.Graphics;

import javax.swing.*;

/**
 * This interface provides a template for the views/frames of each window in the GUI.
 */
public interface ViewPanel {

	// Abstract method declaration
	public JPanel getJPanel();
	public void setVisible();
	public void addToJFrame();	
}
