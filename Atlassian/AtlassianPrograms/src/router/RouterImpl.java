package router;

import java.util.LinkedList;
import java.util.Queue;

public class RouterImpl  implements Router{
	Trie root;
	public RouterImpl() {
		root = new Trie("");
	}
	
	public boolean withRoute(String path, int value) {
		String[] folders = path.split("/");
		if(folders.length < 1) {
			return false;
		}
		
		Trie currNode = root;
		for(int i=1; i<folders.length-1; i++) {
			String currFolder = folders[i];
			if(!currNode.children.containsKey(currFolder)) {
				return false;
			}
			currNode = currNode.children.get(currFolder);
		}
		
		String lastFolder = folders[folders.length-1];
		if(currNode.children.containsKey(lastFolder)) {
			return false;
		}
		Trie newFolder = new Trie(lastFolder);
		newFolder.value = value;
		currNode.children.put(lastFolder, newFolder);
		return true;
		
	}
	
	public int route(String path) {
		if(path.charAt(0) != '/') {
			return -1;
		}
		
		String[] folders = path.split("/");
		Trie currNode = root;
		
		Queue<Trie> q = new LinkedList<>();
		q.add(currNode);
		int folderIndex = 1;
		
		while(folderIndex < folders.length && !q.isEmpty()) {
			Trie curr = q.poll();
			String folder = folders[folderIndex];
			
			if(folder.equals("*")) {
				for(Trie child : curr.children.values()) {
					q.add(child);
				}
				folderIndex++;
			}else {
				if(curr.children.containsKey(folder)) {
					q.add(curr.children.get(folder));
					folderIndex++;
				}else if(q.isEmpty()) {
					return -1;
				}
			}	
		}
		
		String lastFolder = folders[folderIndex-1];
		while(!q.isEmpty()) {
			Trie node = q.poll();
			if(node.name.equals(lastFolder)) {
				return node.value;
			}
		}
		return -1;
		
		 
	}
	
	public static void main(String[] args) {
		RouterImpl fs = new RouterImpl();
		System.out.println(fs.withRoute("/a/b",1));
		System.out.println(fs.withRoute("/a",2));
		System.out.println(fs.withRoute("/a/b",3));
		System.out.println(fs.withRoute("/a/b/c",4));
		System.out.println(fs.withRoute("/a/b/c/",5));
		System.out.println(fs.withRoute("/a/b/x/",6));
		System.out.println(fs.withRoute("/a/b/x/d",7));
		System.out.println(fs.withRoute("/a/b/z",3));
		System.out.println(fs.route("/a"));
		System.out.println(fs.route("/a/b"));
		System.out.println(fs.route("/a/b/c/d"));
		System.out.println(fs.route("/a/b/x"));
		System.out.println(fs.route("a/a/b"));
		System.out.println(fs.route("/a/b/*/d"));
		
	}
}
