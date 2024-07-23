package n1exercici2;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Element implements Comparable<Element> {
	
	private String nom;
	private Path path;
	protected static int depth;
	
	public Element(String nom, Path path) {
		if (!Files.exists(path)){
			throw new UncheckedIOException(new IOException(
					String.format("Path '%s' does not point to a directory or file", path)));
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
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	
	public static void resetDepth() {
		Element.depth = -1;
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
