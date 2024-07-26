package n3exercici1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

import utilitats.Entrada;

import java.nio.charset.StandardCharsets;

public class App {
	
	private static final String DEFAULT_SAVE_FILE = "./output.txt";
	private static final String PROPERTIES_FILE = "app.properties";
	
	private static Properties appProperties = new Properties();
	public static enum PropKeys{
		SHOW_TREE ,SHOW_TYPE, SHOW_DATE, SAVE_TO_FILE, SAVE_FILE
	}

	
	
	public static void main(String[] args) {
		
		if(args.length == 0) {
			System.out.println("Argument expected. Type '-h' for help");
		}else {
			switch (args[0]) {
			case "dir":
				if (args.length > 1) {
					printDirectoryContentAndSerialize(args[1]);
				}else {
					printLastDirectoryContent();
				}
				break;
			case "read":
				readTxtFile(args[1]);
				break;
			case "config":
				changeProperties();
				break;
			case "-h":
				System.out.printf("Valid arguments are:%n -'read' followed by a path"
						+ "%n -'dir' optionally followed by a path.%n -'config'");
				break;
			default:
				System.out.printf("Not a valid argument. Type '-h' for help");
			}
		}
	}


	private static void printDirectoryContentAndSerialize(String string) {
		Path path =	toValidPath(string);
		if(path == null || !Files.isDirectory(path)) {
			System.out.printf("%s is not a valid directory path", string);
		}else {
			Directori directori = new Directori(path.getFileName().toString(), path);
			printDirectoryContent(directori);
			directori.serialize();
		}
	}
	
	private static void printDirectoryContent(Directori directori) {
		loadProperties();
		Element.resetTreeDrawer();
		if (propIsTrue(PropKeys.SAVE_TO_FILE)) {
			printDirectoryContentToFile(directori);
		}else {
			directori.showContent();
		}
	}
	
	private static void printLastDirectoryContent() {
		Directori directori = Directori.unserialize();
		if (directori == null) {
			System.out.println("No saved data to show, input a valid directory path");
		}else {
			printDirectoryContent(directori);
		}
	}
	
	private static void printDirectoryContentToFile(Directori directori) {
		Path outputPath = null;
		try {
			outputPath = Path.of(appProperties.getProperty(PropKeys.SAVE_FILE.toString())).normalize();
		} catch (Exception e) {
			System.out.println("Error retrieving the path. Trying with the default output path...");
			outputPath = Path.of(appProperties.getProperty(PropKeys.SAVE_FILE.toString())).normalize();
		}
		try {
			Files.deleteIfExists(outputPath);
			Files.createFile(outputPath);
			try (OutputStream outputStream = Files.newOutputStream(outputPath,StandardOpenOption.APPEND); 
					PrintStream output = new PrintStream(outputStream, true, StandardCharsets.UTF_8.name())){
				System.out.printf("Writing to %s...", outputPath);
				System.setOut(output);
				directori.showContent();
			} catch (IOException e) {
				System.out.printf("Error while attempting to write to the file. %s", e.getMessage());
			} finally {
				System.setOut(System.out);	
			}
		} catch (IOException e) {
			System.out.println("Error creating the destination file: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void readTxtFile(String string) {
		Path path =	toValidPath(string);
		if (!Fitxer.printTxtFileContent(path)) {
			System.out.printf("%s is not a valid directory path", string); 
		}		
	}
	
	
	public static Path toValidPath(String arg) {
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


	private static void loadProperties() {
		Path appConfigPath = Path.of(PROPERTIES_FILE).normalize();
		try (InputStream input = Files.newInputStream(appConfigPath)){
			appProperties.load(input);
			loadDefaultlIfAbsentAppProperties();
		} catch (IOException e) {
			System.out.printf("%s doesn't exist, loading default properties...%n", PROPERTIES_FILE);
			System.err.println(e.getMessage());
			loadDefaultlIfAbsentAppProperties();
			saveProperties();
		}	
	}
	
	private static void saveProperties() {
		Path appConfigPath = Path.of(PROPERTIES_FILE).normalize();
		try (OutputStream output = Files.newOutputStream(appConfigPath, StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING)){
			appProperties.store(output, "Configuration properties of the App");
		} catch (IOException e) {
			System.err.printf("Error, it wasn't possible to create the %s file%n", PROPERTIES_FILE);
			System.err.print(e.getMessage());
		}
	}

	private static void loadDefaultlIfAbsentAppProperties() {
		appProperties.putIfAbsent(PropKeys.SHOW_TREE.name(), "true");
		appProperties.putIfAbsent(PropKeys.SHOW_TYPE.name(), "true");
		appProperties.putIfAbsent(PropKeys.SHOW_DATE.name(), "true");
		appProperties.putIfAbsent(PropKeys.SAVE_TO_FILE.name(), "false");
		appProperties.putIfAbsent(PropKeys.SAVE_FILE.name(), DEFAULT_SAVE_FILE);
	}

	private static void changeProperties() {
		PropKeys propKey = null;
		loadProperties();
		switch(menu()) {
		case 1:
			propKey = PropKeys.SHOW_TREE;
			toggleProperty(propKey);
			break;
		case 2:
			propKey = PropKeys.SHOW_TYPE;
			toggleProperty(propKey);
			break;
		case 3:
			propKey = PropKeys.SHOW_DATE;
			toggleProperty(propKey);
			break;
		case 4:
			propKey = PropKeys.SAVE_TO_FILE;
			toggleProperty(propKey);
			break;
		case 5:
			propKey = PropKeys.SAVE_FILE;
			String newPath = Entrada.llegirString("Type the new file path\n");
			appProperties.setProperty(propKey.toString(), newPath);
			break;
		default:
			System.out.println("Not a valid input");
		}
		if (propKey != null) {
			System.out.printf("%s has been set to %s%n", propKey.toString(),
					appProperties.getProperty(propKey.toString()));
			saveProperties();
		}
	}
	
	private static int menu() {
		final String MENU = "Select the option to be changed:"
				+ "\n1. Show the directory tree."
				+ "\n2. Show the elements type."
				+ "\n3. Show the last modification date."
				+ "\n4. Output the data to a file."
				+ "\n5. Change the output file.\n";
		return Entrada.llegirInt(MENU);
	}
	
	private static void toggleProperty(PropKeys propKey) {
		if(propIsTrue(propKey)){
			appProperties.setProperty(propKey.toString(), "false");
		}else {
			appProperties.setProperty(propKey.toString(), "true");
		}
	}
	
	static boolean propIsTrue(PropKeys propKey) {
		return appProperties.getProperty(propKey.toString()).equalsIgnoreCase("true");
	}

	

}
