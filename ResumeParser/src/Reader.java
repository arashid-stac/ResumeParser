import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

public class Reader {
	// Select and read PDF
	public String openAndRead() throws InvalidPasswordException, IOException {
		String text = null;
		boolean isValidPath = false;
		do {
			try {
				String path = JOptionPane.showInputDialog("Input the full path" + "\n" + "Example: \n"
						+ "c:/[folder name]/[folder name]/.../[file name].pdf");

				PDDocument document = PDDocument.load(new File(path));

				PDFTextStripper stripper = new PDFTextStripper();

				text = stripper.getText(document);

				document.close();

				isValidPath = true;

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Invalid path!", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (NullPointerException e) {
				System.exit(0);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Invalid file! Please select a PDF file.", "Error", JOptionPane.ERROR_MESSAGE);

			}

		} while (isValidPath == false);

		return text;
	}

	// Input number of keywords to search for
	public int inputNumOfKeywords() {
		int keywordsInteger = 0;
		boolean isValidNumber = false;

		do {
			try {
				String numOfKeywords = JOptionPane.showInputDialog("How many keywords would you like to search for?");
				keywordsInteger = Integer.parseInt(numOfKeywords);

				if (keywordsInteger == 0) {
					JOptionPane.showMessageDialog(null, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					isValidNumber = true;
				}

			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} while (isValidNumber == false);
		return keywordsInteger;
	}

	// Input the keyword(s) to search for
	public HashSet<String> inputKeywords(int keywordsInteger) {
		int i = 0;
		HashSet<String> setOfKeywords = new HashSet<String>();
		do {
			String word = JOptionPane.showInputDialog((i + 1) + ") Input word.");
			setOfKeywords.add(word);
			if (word == null) {
				System.exit(0);
			} else if (word.trim().length() == 0) {
				JOptionPane.showMessageDialog(null, "Invalid input! Please enter a keyword to search for.", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				i++;
			}
		} while (i < keywordsInteger);
		return setOfKeywords;
	}

	// Search the PDF file for the keyword(s)
	public void iterateThroughText(String text, HashSet<String> setOfKeywords) {
		HashSet<String> wordsInText = new HashSet<String>();
		HashSet<String> wordsNotInText = new HashSet<String>();

		for (String word : setOfKeywords) {
			if (text.toLowerCase().contains(word.toLowerCase())) {
				wordsInText.add(word);
			} else {
				wordsNotInText.add(word);
			}
		}
		JOptionPane.showMessageDialog(null,
				"Keywords Found: " + wordsInText + "\n" + "Keywords Not Found: " + wordsNotInText);
	}

	// Do the above
	public void run() throws InvalidPasswordException, IOException {
		iterateThroughText(openAndRead(), inputKeywords(inputNumOfKeywords()));
	}
}
