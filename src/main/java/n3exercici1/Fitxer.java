package n3exercici1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Fitxer extends Element {

	public Fitxer(String nom, Path path) {
		super(nom, path);
		if (!Files.exists(path) || Files.isDirectory(path)){
			throw new UncheckedIOException(new IOException("Error creating the creating the File object"));
		}
	}
	
	public Fitxer(Path path) {
		this(path.getFileName().toString(),path);
	}

	public boolean isReadableTxtFile() {
		if (Files.isReadable(super.getPath())) {
			//Files.getAttribute(getPath(), getLastModified());
		}
		return true;
	}

	public static boolean printTxtFileContent(Path path) {
		boolean success = false;
		try {
			if(Files.probeContentType(path).equalsIgnoreCase("text/plain")) {
				try(BufferedReader reader = Files.newBufferedReader(path)){
					String s = "Showing content of file:";
					do {
						System.out.println(s);
						s = reader.readLine();
					}
					while(s != null);
					success = true;
				}
			}
		} catch (Exception e) {
			System.err.println("Error trying to determine the file type: " + e.getMessage());
		}
		return success;
	}
	
	public Path encrypt(String password, String output) {
		Path outputPath = Path.of(output).normalize();
		try {
			Crypto.encryptFile(super.getPath(), outputPath, password);
		} catch (Exception e) {
			output = null;
			System.out.printf("Error encrypting the file: %s%n", e.getMessage());
		}
		return outputPath;
	}
	
	public Path decrypt(String password, String output) {
		Path outputPath = Path.of(output).normalize();
		try {
			Crypto.decryptFile(super.getPath(), outputPath, password);
		} catch (Exception e) {
			output = null;
			System.out.printf("Error decrypting the file: %s%n", e.getMessage());
		}
		return outputPath;
	}
	

}
