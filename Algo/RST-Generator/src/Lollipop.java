package src;

import java.util.ArrayList;
import java.util.Collections;


public class Lollipop {

	Graph graph;
	
	// Build an undirected graph with random Edges. 
	public Lollipop(int order) 
	{
		graph = new Graph(order);
		ArrayList<Integer> permutation = new ArrayList<>(order);
		
		for (int i = 0; i < order; i++)
			permutation.add(i);
		
		Collections.shuffle(permutation);
		int t = order / 3;
		
		for (int i = 0; i < t; i++)
			graph.addEdge(new Edge(permutation.get(i),permutation.get(i+1),0));
		
		// Why this double loop ? The previous one is already doing the job
		for (int i = t; i < order; i++)
			for (int j = i+1; j < order; j++)
				graph.addEdge(new Edge(permutation.get(i), permutation.get(j), 0));
	}
	
	
}