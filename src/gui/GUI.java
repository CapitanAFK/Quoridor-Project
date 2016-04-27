package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import sun.applet.Main;

/**
 * This class creates a GUI which allows the user to interact with the game.
 * By default the main menu screen opens when the game launches
 * 
 * @author COMP7
 * @version v1.0, 26/04/2016
 */
public class GUI {
	private static JFrame frame;
	private Language currentLanguage;
	private JLabel muteLabel;
	private Clip music;
	private FloatControl gainControl;
	private Image muteIMG;
	private Image quietIMG;
	private Image mediumIMG;
	private Image loudIMG;
	private int volume = 5;

	/**
	 * Constructor for the GUI class
	 * It initialises a JFrame and an object of class MainMenuView is passed to it
	 */
	public GUI(){	
		// Set the current language
		currentLanguage = new Language();
		ResourceBundle messages = currentLanguage.getMessages();

		// Create containers to hold the components
		frame = new JFrame(messages.getString("project_title"));

		// Set the default operations and characteristics
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		frame.setMinimumSize(new Dimension(800, 600));
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		// Initialise the Main Menu class
		MainMenuView menu = new MainMenuView();
		
		try {
			muteIMG = ImageIO.read(new File("Images/music/mute.png"));
			quietIMG = ImageIO.read(new File("Images/music/quiet.png"));
			mediumIMG = ImageIO.read(new File("Images/music/medium.png"));
			loudIMG = ImageIO.read(new File("Images/music/loud.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		muteIMG = muteIMG.getScaledInstance(30, 30,
				BufferedImage.TYPE_INT_ARGB);
		quietIMG = quietIMG.getScaledInstance(30, 30,
				BufferedImage.TYPE_INT_ARGB);
		mediumIMG = mediumIMG.getScaledInstance(30, 30,
				BufferedImage.TYPE_INT_ARGB);
		loudIMG = loudIMG.getScaledInstance(30, 30,
				BufferedImage.TYPE_INT_ARGB);
		JPanel musicPanel;
		musicPanel = new JPanel(null);
		musicPanel.setLocation(10, 10);
		musicPanel.setSize(30, 30);
		muteLabel = new JLabel();
		muteLabel.setLocation(0, 0);
		muteLabel.setSize(30, 30);
		muteLabel.setIcon(new ImageIcon(muteIMG));
		muteLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeVolume();
			}
		});
		musicPanel.add(muteLabel);
		
		try {
			loadMusic();
		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Add components to containers
		frame.add(musicPanel);
		frame.add(menu.getJPanel(), BorderLayout.CENTER);

		// Pack and make the frame visible
		frame.pack();
		frame.setVisible(true);
	}
	
	public void loadMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("music/musics.wav"));
        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        music = (Clip) AudioSystem.getLine(info);
        music.open(audioStream);
        gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
        music.loop(music.LOOP_CONTINUOUSLY);
        changeVolume();
	}
	
	public void changeVolume(){
		volume -= 10;
		if (volume == -35){
			volume = 5;
		}
		gainControl.setValue(volume);
		switch (volume) {
		case -25:
			muteLabel.setIcon(new ImageIcon(muteIMG));
			music.stop();
			break;
		case -15:
			muteLabel.setIcon(new ImageIcon(quietIMG));	
			break;
		case -5:
			muteLabel.setIcon(new ImageIcon(mediumIMG));
			break;
		case 5:
			muteLabel.setIcon(new ImageIcon(loudIMG));
			music.loop(music.LOOP_CONTINUOUSLY);
			break;
		}
	}


	/**
	 * Main method which creates a single objects of class GUI
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GUI gui = new GUI();
	}

	/**
	 * This method returns the main JFrame
	 * 
	 * @return JFrame frame
	 */
	public static JFrame getJFrame() {
		return frame;
	}
}
