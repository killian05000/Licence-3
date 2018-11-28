package src;

import java.util.ArrayList;
import java.util.Random;


public class ErdosRenyi // Simple Graphs
{

	private final static Random gen = new Random();
	Graph graph;
	private int order;
	private double edgeProbability;
	
	// For a maximum expectedAverageDegree, the graph is automatically a complete one (and therefore connected)
	// For a lesser value the graph may not be complete or connected.
	public ErdosRenyi(int order, float expectedAverageDegree)
	{
		this.edgeProbability = Math.max(1.5, expectedAverageDegree) / (order-1);
		this.order = order;
		while (!isConnected()) genGraph(); // Generate graphs until it gets a connected one		
	}
	
	// Check if the graph is connected or split
	private boolean isConnected()
	{
		if (graph == null) return false;
		ArrayList<Arc> tree = BreadthFirstSearch.generateTree(graph, 0);
		return tree.size() == order - 1;
		
	}
	
	//Generate a random graph without any loops
	private void genGraph()
	{
		graph = new Graph(order);
		for (int i = 0; i < order; i++)
			for (int j = i+1; j < order; j++)
			{
				if (gen.nextDouble() < edgeProbability)
					graph.addEdge(new Edge(i,j,0));
			}
	}
}
