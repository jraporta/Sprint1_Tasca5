package n1exercici4;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Element implements Comparable<Element> {
	
	private String nom;
	private Path path;
	private FileTime lastModified;
	private static TreeDrawer treeDrawer;
	
	static {
		treeDrawer = null;
	}
	
	public Element(String nom, Path path) {
		if (!Files.exists(path)){
			throw new UncheckedIOException(new IOException(
					String.format("Path '%s' does not point to a directory or file", path)));
		}
		try {
			this.lastModified = Files.getLastModifiedTime(path);
		} catch (IOException e) {
			System.err.printf("Error when retrieving last modified date of %s%n%s", this.path, e.getMessage());
			this.lastModified = null;
		}
		this.nom = nom;
		this.path = path;
	}
	
	public Element(Path path) {
		this(path.getFileName().toString(),path);
	}
	
	public String getNom() {
		return nom;
	}
	public Path getPath() {
		return path;
	}
	public static TreeDrawer getTreeDrawer() {
		return treeDrawer;
	}
	public String getLastModified() {
		if (this.lastModified == null) {
			return "unknown";
		}
		return "(Last modified: " + DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss").
				format(LocalDateTime.ofInstant(lastModified.toInstant(), ZoneId.systemDefault())) + ")";
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setPath(Path path) {
		this.path = path;
	}

	public static void resetTreeDrawer() {
		Element.treeDrawer = new TreeDrawer();
	}
	
	protected void checkIsStillThere() throws UncheckedIOException{
		if(!Files.exists(path)) {
			throw new UncheckedIOException(new IOException(
					String.format("Error fatal, l'element \"%s\" no s'ha pogut trobar", this.path)));
		}
	}

	@Override
	public String toString() {
		return "Element [nom=" + nom + ", path=" + path + "]";
	}

	@Override
	public int compareTo(Element o) {
		return this.nom.compareTo(o.nom);
	}

	
	

}
