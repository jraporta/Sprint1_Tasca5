package n1exercici2;

import java.util.ArrayList;
import java.util.List;

public class TreeDrawer {
	
	private static final char[] TREE_CHARS = {' ', '\u2502', '\u251C', '\u2514'}; //{' ','│','├','└'}
	private List<Byte> treeChars;
	
	public TreeDrawer() {
		this.treeChars = new ArrayList<Byte>();
	}
	
	public boolean isEmpty() {
		return this.treeChars.isEmpty();
	}
	
	public void setLastItem() {
		treeChars.remove(treeChars.size() - 1);
		treeChars.add((byte) 3);
	}

	public void goDeeper() {
		treeChars.add((byte) 2);
	}
	
	public void goShallower() {
		treeChars.remove(treeChars.size()-1);
	}
	
	public void next() {
		for(int i = 0; i < treeChars.size() - 1; i++) {
			if (treeChars.get(i) == 3) {
				treeChars.remove(i);
				treeChars.add(i, (byte) 0);
			}else if(treeChars.get(i) == 2) {
				treeChars.remove(i);
				treeChars.add(i, (byte) 1);
			}
		}
	}
	
	@Override
	public String toString() {
		String s = "";
		for (byte b: treeChars) {
			s += TreeDrawer.TREE_CHARS[b] + " ";
		}
		return s;
	}
	
	
	

}
