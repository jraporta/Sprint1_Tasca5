package n1exercici2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
	
	public static enum Option{
		ALL("-a", "-A") ,
		TYPE("-t", "-T"),
		DATE("-d", "-D");
		
		private final List<String>flags;
		
		Option(String...flags){
			this.flags = Arrays.asList(flags);
		}
		
		public static Option fromFlag(String flag) {
			for (Option option : values()) {
				if (option.flags.contains(flag)) {
					return option;
				}
			}
			return null;
		}
		
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
			Option option = Option.fromFlag(arg);
			if (option != null) {
				options.add(option);
			}	
		}
	}


	private static void mostraTotContingut(Path path) {
		Element.resetTreeDrawer();
		Directori directori = new Directori(path.getFileName().toString(), path);
		directori.showContent(options.toArray(new Option[0]));
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
