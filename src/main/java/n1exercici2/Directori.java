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
	private int nonAccessibleElements;	

	public Directori(String nom, Path path) {
		super(nom, path);
		if (!Files.isDirectory(path)){
			throw new UncheckedIOException(new IOException("path does not point to a directory"));
		}
		this.elements = new ArrayList<Element>();
		this.nonAccessibleElements = 0;
	}

	public Directori(Path path) {
		this(path.getFileName().toString(),path);
	}

	public Element getElement(int i) {
		return elements.get(i);
	}

	public void setElement(Element element) {
		this.elements.add(element);
	}

	public void updateContent() {
		super.checkIsStillThere();
		this.elements.clear();
		this.nonAccessibleElements = 0;
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
					this.nonAccessibleElements++;
				}
			}
		} catch (IOException e) {
			throw new UncheckedIOException(String.format(
					"Error ''impossible'' al provar d'explorar els continguts de la carpeta %s", super.getPath()),e);
		}
	}

	public void showContent(App.Option... options) {
		updateContent();
		System.out.print(Element.getTreeDrawer().isEmpty() ? super.getPath() : "");
		if (! this.elements.isEmpty()) {
			Element.getTreeDrawer().goDeeper();
			Collections.sort(this.elements);
			for (int i = 0; i < this.elements.size(); i++) {
				if(i == this.elements.size() - 1) {
					Element.getTreeDrawer().setLastItem();
				}
				Element.getTreeDrawer().next();
				printElementfromDirectory(this.elements.get(i), options);
				if (Arrays.asList(options).contains(App.Option.ALL) &&
						this.elements.get(i) instanceof Directori) {
					((Directori) this.elements.get(i)).showContent(options);
				}
			}
			if(this.nonAccessibleElements > 0) {
				System.out.printf("%n%s+ %d \"non-existing\" elements:",
						Element.getTreeDrawer(), this.nonAccessibleElements);
			}	
			Element.getTreeDrawer().goShallower();
			this.elements.clear();
		}		
	}

	private void printElementfromDirectory(Element element, App.Option... options) {
		System.out.printf("%n%s%s", Element.getTreeDrawer(), element.getNom());
		if (Arrays.asList(options).contains(App.Option.TYPE)) {
			System.out.print(element instanceof Directori ? " (D)" : " (F)");
		}
		if (Arrays.asList(options).contains(App.Option.DATE)) {
			System.out.printf(" %s", element.getLastModified());
		}
	}
	
	

}
