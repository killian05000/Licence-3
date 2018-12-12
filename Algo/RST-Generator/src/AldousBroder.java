package src;

import java.util.ArrayList;
import java.util.LinkedList;

public class AldousBroder 
{
	Graph graph;
	ArrayList<Edge> Tree;
	ArrayList<Integer> vertexConnected;
	double progressBar;
	
	public void genRandTree(int root)
	{
		LinkedList<Integer> stash = new LinkedList<>();
		boolean treeComplete=false;
		
		int currentVertex = root;
		//vertexConnected.add(root);
		while(!treeComplete)
		{
			//System.out.println("CURRENT VERTEX : "+currentVertex);
			
			while(vertexIsFullyConnected(currentVertex))
			{
//				if(stash.isEmpty())
//				{
//					System.out.println("--STASH EMPTY--");
//					break;
//				}
				
				int n = (int)(Math.random() *vertexConnected.size());				
				currentVertex=vertexConnected.get(n);
				
				//System.out.println("VERTEX CHANGED ! ["+currentVertex+"]");			
			}
			
			//System.out.println("CURRENT VERTEX AFTER VFC CHECK : "+currentVertex);
			
			int n = (int)(Math.random() *graph.adjacency.get(currentVertex).size());
			int destV = graph.adjacency.get(currentVertex).get(n).getDest(); 
			//System.out.println("NEXT VERTEX : "+destV);		

			for(Edge e : graph.adjacency.get(currentVertex))
			{					
				if(e.getDest()==destV || stash.contains(e.getDest()) || vertexConnected.contains(e.getDest()))
					continue;
				
				stash.add(e.getDest()); 
			}			

			
			if(!Tree.contains(graph.adjacency.get(currentVertex).get(n)))
			{
				Tree.add(graph.adjacency.get(currentVertex).get(n));
				//System.out.println("Tree Edge : ["+graph.adjacency.get(currentVertex).get(n).getSource()+" -> "+graph.adjacency.get(currentVertex).get(n).getDest()+"]");
			}
			
			if(!vertexConnected.contains(currentVertex))
			{
				vertexConnected.add(currentVertex);
				//System.out.println("Vertex "+currentVertex+" connected");
				progressBar = (double)vertexConnected.size()/graph.order;
				progressBar=progressBar*10000;
				progressBar=(int)progressBar;
				System.out.println("Creating Tree ["+progressBar/100+"%]");
			}
			
			currentVertex=destV;		
			
			if(vertexConnected.size()==graph.order)
				treeComplete=true;
		}
	}
	
	public boolean vertexIsFullyConnected(int currentVertex)
	{
		int full=0;
		for(Edge e : graph.adjacency.get(currentVertex))
		{
			if(vertexConnected.contains(e.getDest()))
				full++;
		}
		
		return full==graph.adjacency.get(currentVertex).size();
	}
	
	public AldousBroder(Graph g)
	{
		this.graph = g;
		this.Tree = new ArrayList<>();
		this.vertexConnected = new ArrayList<>();		
	}
	
	public static ArrayList<Edge> generateTree(Graph g, int root)
	{
		AldousBroder algo = new AldousBroder(g);
		algo.genRandTree(root);
		
		System.out.println("tree size graph : " + algo.Tree.size());		
		System.out.println("Order graph : " + algo.graph.order);		
		System.out.println("Size adjency graph : " + algo.graph.adjacency.size());
		System.out.println("Number of connected Vertices : "+algo.vertexConnected.size());

		return algo.Tree;
	}
}
