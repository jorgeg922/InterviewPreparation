package hackerrank;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MaxIndexReachable {
	public static int maxIndex = -1;
	private static Map<String, Integer> memo = new HashMap<>();
	
	public static void main(String[] args) {
		int steps = 4;
		int bad = 6;
		int ans = getMaxIndex(steps,bad);
		System.out.println(ans);
	}
	
	public static int getMaxIndex(int steps, int badIndex) {
		int index = 0;
		int jump = 1;
		int step = 0;
		
		findMax(index, jump, step, steps, badIndex);	
		
		return maxIndex;
		
	}
	
	public static void findMax(int index, int jump, int step, int steps, int badIndex) {
		if(step > steps) {
			return;
		}
		
		String key = index + "," + jump + "," + step;
        if (memo.containsKey(key)) {
            return;
        }
        
		maxIndex = Math.max(maxIndex, index);
		
		int nextIndex = index + jump;
		
		if(nextIndex != badIndex) {
			findMax(nextIndex, jump+1, step+1, steps, badIndex);
		}
		
		findMax(index, jump+1, step+1, steps, badIndex);
		memo.put(key, maxIndex);
	}
}
