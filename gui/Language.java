package gui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

// IMPORTANT
// ADDING A NEW LANGUAGE CHECKLIST
// 1. language_{}.{}.properties
// 2. setCountry(); add to the if statement
// 3. in OptionsView:
//	a. language.addActionListener(); add to the if statement
//  b. updateComboBoxView(); add to the if statement
//  c. add to languages array
// 4. Add images

/**
 * Class responsible for detecting the current language
 */
public class Language {
	protected String language;
	protected String country = "GB";
	protected Locale currentLocale;
	protected ResourceBundle messages;

	public Language() {
		setLanguage();
		setCountry();

		// Set the locale and messages
		currentLocale = new Locale(language, country);
		messages = ResourceBundle.getBundle("language", currentLocale);
	}

	public String getLanguage() {
		return language;
	}

	public ResourceBundle getMessages() {
		return messages;
	}

	public void setLanguage() {
		// This will reference one line at a time
		String line = null;

		// Read the language from the file
		try {
			// Read the file
			FileReader fr = new FileReader("textFiles/currentLanguage.txt");

			// Wrap FileReader in BufferedReader
			BufferedReader br = new BufferedReader(fr);

			// Set the language based on the file
			line = br.readLine();
			language = line;

			// Clear the line
			line = null;

			// Close file
			br.close();

		}

		catch (FileNotFoundException ex) {
			System.out.println("No file found.");
		}

		catch (IOException ex) {
			System.out.println("Error reading the file.");
		}
	}

	public void setCountry() {
		// Set the country based on the language	
		if (language == null) {
			language = "en";
			country = "GB";
		} else if (language.equals("en")) {
			country = "GB";
		} else if (language.equals("pl")) {
			country = "PL";
		} else if (language.equals("zu")) {
			country = "ZU";
		} else if (language.equals("af")) {
			country = "AF";
		} else if (language.equals("xh")) {
			country = "XH";
		}
	}

}