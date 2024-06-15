package codesignal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
/*
 * Problem: You have a bus that can go to all stops in a designated route. 
 * If you need to go to a stop not within the route, you need to make a 
 * "transfer" to another route, this second route must have a stop that
 *  also belongs to the route you are coming from (a connection).
For example, in possible routes [1,2,7],[3,6,7] if you want to go
 to stop 6 the number of transfers is 1 because the bus can go
  1,7,6 where the 7 served as a connection between to two routes
   to get to stop 6. The bus must begin at stop with value 1. 
   Write the java code to get the minimum numbers of transfers
    to reach a destination from stop 1
 * 
 */
public class Uber {
	public static void main(String[] args) {
		int[][] routes = new int[][] { { 1, 2, 7 }, { 3, 6, 7 } };
		int ans = minRoutes(routes,1,6);
		System.out.println(ans);
		routes = new int[][] { { 1, 2, 7 }, { 3, 6, 7 }, {5,6}};
		ans = minRoutes(routes,1,5);
		System.out.println(ans);
	}

	public static int minRoutes(int[][] routes, int start, int destination) {
		if (start == destination)
			return 0;

		// Build the graph: stop -> list of buses (routes) that visit this stop
		Map<Integer, List<Integer>> stopToRoutes = new HashMap<>();
		for (int i = 0; i < routes.length; i++) {
			for (int stop : routes[i]) {
				stopToRoutes.computeIfAbsent(stop, k -> new ArrayList<>()).add(i);
			}
		}

		// Queue for BFS: array of [current stop, current transfers]
		Queue<int[]> queue = new LinkedList<>();
		for(int routeForOne : stopToRoutes.get(start)) {
			queue.add(new int[] {routeForOne, 0});//routes for start
		}
		

		// Visited stops
		Set<Integer> visitedStops = new HashSet<>();
		visitedStops.add(start);

		// Visited routes
		Set<Integer> visitedRoutes = new HashSet<>();
		
		while (!queue.isEmpty()) {
			int[] current = queue.poll();
			int currentRoute = current[0];
			int currentTransfers = current[1];
			
			for(int stop : routes[currentRoute]) {
				if(stop == destination) {
					return currentTransfers;
				}
				if(!visitedStops.contains(stop)) {
					visitedStops.add(stop);
					for(int route : stopToRoutes.get(stop)) {
						if(!visitedRoutes.contains(route)) {
							queue.add(new int[] {route, currentTransfers + 1});
							visitedRoutes.add(route);
						}
					}
				}
			}
		}
		return -1; // Destination not reachable
	}
}
