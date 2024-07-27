package n3exercici1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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
	
	public boolean isReadableFile() {
		boolean b = false;
		if (Files.isReadable(super.getPath())) {
			b = true;
		}
		return b;
	}

	public boolean isReadableTxtFile() {
		boolean b = false;
		if (this.isReadableFile()) {
			try {
				if(Files.probeContentType(super.getPath()).equalsIgnoreCase("text/plain")) {
					b = true;
				}
			} catch (IOException e) {
				System.out.printf("Error checking if the type of the file: %s", e.getMessage());
			}
		}
		return b;
	}

	public boolean printTxtFileContent() {
		boolean success = false;
		try(BufferedReader reader = Files.newBufferedReader(super.getPath())){
			String s = "Showing content of file:";
			do {
				System.out.println(s);
				s = reader.readLine();
			}
			while(s != null);
			success = true;
		}catch (Exception e) {
			System.err.println("Error reading from the file: " + e.getMessage());
		}
		return success;
	}
	
	public Path encrypt(String password, String output) {
		Path outputPath = Path.of(output).normalize();
	
		try {
			Crypto.encryptFile(super.getPath(), outputPath, password);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
				| IOException e) {
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
