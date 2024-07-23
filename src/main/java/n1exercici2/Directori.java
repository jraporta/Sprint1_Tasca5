package n1exercici2;


import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Directori extends Element{
	
	private List<Element> elements;
	private int elementsNoAccessibles;
	private enum Option{
		ALL, TYPE 
	}

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

	public String mostraContingut(Directori.Option... option) {
		String resposta = "";
		resposta += Element.depth == 0 ? String.format("%s%s\\",(" ").repeat(depth), super.getPath()) : "";
		Collections.sort(this.elements);
		for (int i = 0; i < this.elements.size(); i++) {
			resposta += String.format("%n %s%s%s", ("|").repeat(depth), i < this.elements.size() - 1? "\u251C" : "\u2514", this.elements.get(i).getNom());
			if (Arrays.asList(option).contains(Directori.Option.TYPE)) {
				resposta += this.elements.get(i) instanceof Directori ? " (D)" : " (F)";
			}
			if (Arrays.asList(option).contains(Directori.Option.ALL) &&
					this.elements.get(i) instanceof Directori) {
				resposta += ((Directori) this.elements.get(i)).mostraTotContingut();
			}
		}
		resposta += this.elementsNoAccessibles > 0 ? 
				String.format("%n  + %d elements \"inexistents\"", this.elementsNoAccessibles) : "";
		return resposta;
	}
	
	public 	String mostraTotContingut() {
		Element.depth ++;
		actualitzaContingut();
		String s = mostraContingut(Directori.Option.ALL, Directori.Option.TYPE);
		Element.depth --;
		return s;
	}

}
