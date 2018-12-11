package src;

import java.util.ArrayList;

public class Prim 
{
	Graph graph;
	ArrayList<Edge> Tree;
	ArrayList<Integer> connectedVertices;
	int maxWeight=2;
	
	public Prim(Graph g)
	{
		this.graph = g;
		this.Tree = new ArrayList<>();
		this.connectedVertices = new ArrayList<>();
		ArrayList<Integer> stash = new ArrayList<>();
		//System.out.println("Test : "+graph.adjacency.size());
		for(int k=0; k<graph.order; k++)
		{
			for(Edge e : graph.adjacency.get(k))
			{
				if (stash.contains(e.getDest()))
					continue;
				
				double n =(double)(int)(Math.random() *maxWeight);
				e.weight = n; 
			}
			stash.add(k);
		}
	}
	
	public void getMST(int root) // Minimal Spanning Tree
	{
		boolean MSTOver = false;				
		double minWeight=maxWeight;
		Edge minEdge = new Edge(0,0,0.0);
		
		connectedVertices.add(root);
		while(!MSTOver) 
		{
			for(int vertex : connectedVertices)
			{
				for(Edge e : graph.adjacency.get(vertex))
				{
					if(!Tree.contains(e) || connectedVertices.contains(e.getDest()) || e.getWeight()>=minWeight) 
						continue;
				 
					// If an edge got a inferior weight, we store its destination				
					minWeight=e.getWeight();
					minEdge=e;
				}
			}
			
			Tree.add(minEdge);	
			connectedVertices.add(minEdge.getDest());
			minWeight=maxWeight;
			
			if(connectedVertices.size()==graph.order)
				MSTOver = true;
		}
	}
	
	public ArrayList<Arc> convertEdgeToArc()
	{
		ArrayList<Arc> arcTree = new ArrayList<>();
		for(Edge e : Tree)
			arcTree.add(new Arc(e, true));
		
		return arcTree;
	}
	
	public static ArrayList<Arc> generateTree(Graph g, int root)
	{
		Prim algo = new Prim(g);
		algo.getMST(root);
		ArrayList<Arc> arcTree = algo.convertEdgeToArc();
		return arcTree;		
	}

}
