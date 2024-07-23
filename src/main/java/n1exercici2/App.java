package n1exercici2;

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
		
		System.out.println("Directory Explorer v0.2");
		
		directori = new Directori(path.getFileName().toString(), path);
//		directori.actualitzaContingut();
//		System.out.printf("%s", directori.mostraContingut());
		System.out.println(mostraTotContingut(directori));
	}
	
	
	private static String mostraTotContingut(Directori directori) {
		Element.resetDepth();
		return directori.mostraTotContingut();
	}


	public static Path llegeixPath(String arg) {
		Path path = null;
		if (arg != null){
			try {
				path = Path.of(arg).toRealPath();
				if (!Files.isDirectory(path)) {
					path = null;
				}
			} catch (IOException e) {
				path = null;
			}
		}
		return path;
	}
	
	

}
