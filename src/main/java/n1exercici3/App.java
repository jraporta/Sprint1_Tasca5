package n1exercici3;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.charset.StandardCharsets;

public class App {
	
	private static final String SAVE_FILE = "./output.txt";
	public static enum Option{
		ALL ,TYPE, DATE, TO_FILE
	}
	private static List<Option> options = new ArrayList<Option>();

	public static void main(String[] args) {
		Path path = null;
		
				
		if(args.length == 0) {
			System.out.println("expected: path of a directory");
			return;
		}
		
		path =	llegeixPath(args[0]);
		
		if(path == null) {
			System.out.printf("%s is not a valid directory path", args[0]);
			return;
		}
		readOptions(Arrays.copyOfRange(args, 1, args.length));
		mostraTotContingut(path);
	}
	
	
	private static void readOptions(String[] args) {
		for(String arg : args) {
			switch (arg) {
			case "-d": case "-D":
				options.add(App.Option.DATE);
				break;
			case "-t": case "-T":
				options.add(App.Option.TYPE);
				break;
			case "-a": case "-A":
				options.add(App.Option.ALL);
				break;
			case "-f": case "-F":
				options.add(App.Option.TO_FILE);
				break;
			}	
		}
	}


	private static void mostraTotContingut(Path path) {
		Element.resetTreeDrawer();
		Directori directori = new Directori(path.getFileName().toString(), path);
		if (options.contains(App.Option.TO_FILE)) {
			try {
				Files.deleteIfExists(Path.of(SAVE_FILE).normalize());
				Path outputPath = Files.createFile(Path.of(SAVE_FILE).normalize());
				System.out.printf("Writing to %s...", outputPath);
				System.setOut(new PrintStream(Files.newOutputStream(outputPath,StandardOpenOption.APPEND),
						true, StandardCharsets.UTF_8.name()));
				directori.showContent(options.toArray(new Option[0]));
			} catch (IOException e) {
				System.setOut(System.out);
				System.out.printf("Error while attempting to write to the file. %s", e.getMessage());
			}
		}else {
			directori.showContent(options.toArray(new Option[0]));
		}
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
