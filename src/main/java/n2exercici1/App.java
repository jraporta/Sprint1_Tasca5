package n2exercici1;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.nio.charset.StandardCharsets;

public class App {
	
	//Move to properties file
	private static final String DEFAULT_SAVE_FILE = "./output.txt";
	private static final String PROPERTIES_FILE = "app.properties";
	
	private static Properties appProperties = new Properties();
	public static enum PropKeys{
		SHOW_TREE ,SHOW_TYPE, SHOW_DATE, SAVE_TO_FILE, SAVE_FILE
	}
	private static List<PropKeys> options = new ArrayList<PropKeys>();

	public static void main(String[] args) {
		loadProperties();
		
		
		
		Path path = null;
		
		if(args.length == 0) {
			System.out.println("expected: path of a directory");
			return;
		}
		
		switch (args[0]) {
		case "read":
			path =	toPath(args[1]);
			if (!Fitxer.printTxtFileContent(path)) {
				System.out.printf("%s is not a valid directory path", args[0]); 
			}
			break;
		case "last":
			readOptions(Arrays.copyOfRange(args, 1, args.length));
			printDirectoryContent(Directori.unserialize());
			break;
		default:
			path =	toPath(args[0]);
			if(path == null || !Files.isDirectory(path)) {
				System.out.printf("%s is not a valid directory path", args[0]);
			}else {
				readOptions(Arrays.copyOfRange(args, 1, args.length));
				Directori directori = new Directori(path.getFileName().toString(), path);
				printDirectoryContent(directori);
				directori.serialize();
			}
		}
	}
	
	
	private static void loadProperties() {
		Path appConfigPath = Path.of(PROPERTIES_FILE).normalize();
		try {
			appProperties.load(Files.newInputStream(appConfigPath));
		} catch (IOException e) {
			System.err.printf("%s doesn't exist, creating it from scratch", PROPERTIES_FILE);
			getDefaultAppProperties();
		}	
	}


	private static void getDefaultAppProperties() {
		appProperties.put(PropKeys.SHOW_TYPE, false);
		appProperties.put(PropKeys.SHOW_TREE, false);
		appProperties.put(PropKeys.SHOW_DATE, false);
		appProperties.put(PropKeys.SAVE_FILE, DEFAULT_SAVE_FILE);
	}


	private static void readOptions(String[] args) {
		for(String arg : args) {
			switch (arg) {
			case "-d": case "-D":
				options.add(App.PropKeys.SHOW_DATE);
				break;
			case "-t": case "-T":
				options.add(App.PropKeys.SHOW_TYPE);
				break;
			case "-a": case "-A":
				options.add(App.PropKeys.SHOW_TREE);
				break;
			case "-f": case "-F":
				options.add(App.PropKeys.SAVE_TO_FILE);
				break;
			}	
		}
	}


	private static void printDirectoryContent(Directori directori) {
		Element.resetTreeDrawer();
		if (options.contains(App.PropKeys.SAVE_TO_FILE)) {
			try {
				Files.deleteIfExists(Path.of(DEFAULT_SAVE_FILE).normalize());
				Path outputPath = Files.createFile(Path.of(DEFAULT_SAVE_FILE).normalize());
				System.out.printf("Writing to %s...", outputPath);
				System.setOut(new PrintStream(Files.newOutputStream(outputPath,StandardOpenOption.APPEND),
						true, StandardCharsets.UTF_8.name()));
				directori.showContent(options.toArray(new PropKeys[0]));
			} catch (IOException e) {
				System.setOut(System.out);
				System.out.printf("Error while attempting to write to the file. %s", e.getMessage());
			}
		}else {
			directori.showContent(options.toArray(new PropKeys[0]));
		}
	}


	public static Path toPath(String arg) {
		Path path = null;
		if (arg != null){
			try {
				path = Path.of(arg).toRealPath();
			} catch (IOException e) {
				path = null;
			}
		}
		return path;
	}

}
