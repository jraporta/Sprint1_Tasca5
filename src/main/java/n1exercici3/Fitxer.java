package n1exercici3;

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
	

}
