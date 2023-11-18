package router;

import java.util.HashMap;

public class Trie {
	String name;
	int value;
	HashMap<String,Trie> children;
	public Trie(String name) {
		this.name = name;
		value = -1;
		children = new HashMap<>();
	}
}
