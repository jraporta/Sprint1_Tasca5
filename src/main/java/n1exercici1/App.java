package n1exercici1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class App {

	public static void main(String[] args) {
		Path path = null;
		Directori directori = null;
		
		if(args.length == 0) {
			System.out.println("expected: path of a directory");
			return;
		}
		
		path =	llegeixPath(args[0]);
		
		if(path == null) {
			System.out.printf("%s is not a valid directory path", args[0]);
			return;
		}
		
		directori = new Directori(path.getFileName().toString(), path);
		directori.actualitzaContingut();
		System.out.printf("%s", directori.mostraContingut());
	}
	
	
	public static Path llegeixPath(String arg) {
		Path path = null;
		try {
			path = Path.of(arg).toRealPath();
		} catch (IOException e) {
			path = null;
		}
		return Files.isDirectory(path) ? path : null;
	}

}
