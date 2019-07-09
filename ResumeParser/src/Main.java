import java.io.IOException;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

public class Main {
	// Run the program
	public static void main(String[] args) throws InvalidPasswordException, IOException {
		Reader reader = new Reader();
		reader.run();
	}

}
