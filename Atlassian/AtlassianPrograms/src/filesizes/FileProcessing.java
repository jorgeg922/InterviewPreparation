package filesizes;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class FileProcessing {
	long totalSize;
	HashMap<String,Long> fileSizes = new HashMap<>();
	HashMap<String,Long> collectionSizes = new HashMap<>();
	public FileProcessing() {
		totalSize = 0;
		
	}
	
	public void addFile(String filename, long size, List<String> collections) {
		totalSize += size;
		if(collections == null) {
			return;
		}

		for(String collection : collections) {
			long currentCollectionSize = 0;
			if(collectionSizes.containsKey(collection)) {
				currentCollectionSize = collectionSizes.get(collection);
			}
			
			currentCollectionSize += size;
			collectionSizes.put(collection, currentCollectionSize);
		}	
	}
	
	public long getTotalSize() {
		return totalSize;
	}
	public String[] topKCollections(int k) {
		PriorityQueue<String> pq = new PriorityQueue<>(new Comparator<>() {
			public int compare(String a, String b) {
				if(collectionSizes.get(a).equals(collectionSizes.get(b))) {
					if(a.compareTo(b) < 0) {
						return 1;
					}else {
						return -1;
					}
				}
				return (int) (collectionSizes.get(a) - collectionSizes.get(b));
			}
		});
		
		for(String collection : collectionSizes.keySet()) {
			pq.add(collection);
			if(pq.size() > k) {
				pq.poll();
			}
		}
		
		String[] ans = new String[pq.size()];
		int index = 0;
		while(!pq.isEmpty()) {
			ans[index++] = pq.poll();
		}
		return ans;
	}
	
	public static void main(String[] args) {
		FileProcessing fp = new FileProcessing();
		fp.addFile("file1.txt", 100, null);
		fp.addFile("file2.txt", 200, Arrays.asList("collection1"));
		fp.addFile("file3.txt", 200, Arrays.asList("collection1"));
		fp.addFile("file4.txt", 400, Arrays.asList("collection2"));
		fp.addFile("file5.txt", 100, null);
		fp.addFile("file6.txt", 200, Arrays.asList("collection3"));
		fp.addFile("file7.txt", 200, Arrays.asList("collection3"));
		fp.addFile("file8.txt", 200, Arrays.asList("a"));
		fp.addFile("file9.txt", 200, Arrays.asList("a"));
		
		System.out.println(fp.getTotalSize());
		String[] topCollections = fp.topKCollections(2);
		for(String collection : topCollections) {
			System.out.println(collection);
		}
		
	}
}
