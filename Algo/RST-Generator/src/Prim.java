package src;

import java.util.ArrayList;

public class Prim 
{
	Graph graph;
	ArrayList<Edge> Tree;
	ArrayList<Integer> connectedVertices;
	ArrayList<Edge> connectedEdges;
	
	public Prim(Graph g)
	{
		this.graph = g;
		this.connectedVertices = new ArrayList<>();
		this.connectedEdges = new ArrayList<>();
		ArrayList<Integer> stash = null;
		for(int k=0; k<graph.order; k++)
		{
			for(Edge e : graph.adjacency.get(k))
			{
				if (stash.contains(e.getDest()))
					continue;
				
				int n =(int)(Math.random() *2);
				e.weight = n; 
			}
			stash.add(k);
		}
	}
	
	public void getMST(int root) // Minimal Spanning Tree
	{
		ArrayList<Integer> stash = null;
		
		int min= graph.order;
		for(Edge e : graph.adjacency.get(root))
		{
			if(e.getWeight()<graph.order) // If an edge got a inferior weight, we store its destination 
				min=e.getDest();				
		}
	}
	
	public static ArrayList<Arc> generateTree(Graph g, int root)
	{
		Prim algo = new Prim(g);
		algo.getMST(root);
		return null;		
	}

}
