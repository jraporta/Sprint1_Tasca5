package n1exercici1;


import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Directori extends Element{
	
	private List<Element> elements;
	private int elementsNoAccessibles;

	public Directori(String nom, Path path) {
		super(nom, path);
		if (!Files.isDirectory(path)){
			throw new UncheckedIOException(new IOException("path does not point to a directory"));
		}
		this.elements = new ArrayList<Element>();
		this.elementsNoAccessibles = 0;
	}

	public Directori(Path path) {
		this(path.getFileName().toString(),path);
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElement(Element element) {
		this.elements.add(element);
	}

	public void actualitzaContingut() {
		super.checkIsStillThere();
		this.elements.clear();
		this.elementsNoAccessibles = 0;
		try {
			for(Iterator<Path> paths = Files.newDirectoryStream(getPath()).iterator(); paths.hasNext(); ) {
				Path p = paths.next();
				if(Files.exists(p)){
					if(Files.isDirectory(p)) {
						this.setElement(new Directori(p));
					}else {
						this.setElement(new Fitxer(p));
					}
				}else {
					this.elementsNoAccessibles++;
				}
			}
		} catch (IOException e) {
			throw new UncheckedIOException(String.format(
					"Error ''impossible'' al provar d'explorar els continguts de la carpeta %s", super.getPath()),e);
		}
	}

	public Object mostraContingut() {
		String resposta = String.format("%s\\", super.getPath());
		Collections.sort(this.elements);
		for (int i = 0; i < this.elements.size() - 1; i++) {
			resposta += String.format("%n %s %s", "\u251C", this.elements.get(i).getNom());
		}
		resposta += String.format("%n %s %s","\u2514", this.elements.get(this.elements.size() - 1).getNom());
		resposta += this.elementsNoAccessibles > 0 ? 
				String.format("%n  + %d elements \"inexistents\"", this.elementsNoAccessibles) : "";
		return resposta;
	}
	
	

}
