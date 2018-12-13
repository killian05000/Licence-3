package src;

import java.util.ArrayList;
import java.util.Random;
import java.util.LinkedList;

public class drunkWizard 
{
	Graph graph;
	ArrayList<Edge> Tree;
	ArrayList<Integer> vertexConnected;
	double progressBar;
	
	public void genRandTree(int root)
	{
		boolean treeComplete=false;
		
		int currentVertex = root;
		vertexConnected.add(root);
		while(!treeComplete)
		{
			//System.out.println("CURRENT VERTEX : "+currentVertex);
			
			while(vertexIsFullyConnected(currentVertex))
			{				
				int n = (int)(Math.random() *vertexConnected.size());				
				currentVertex=vertexConnected.get(n);
				
				//System.out.println("VERTEX CHANGED ! ["+currentVertex+"]");			
			}
			
			int n = (int)(Math.random() *graph.adjacency.get(currentVertex).size());
			int destV = graph.outAdjacency.get(currentVertex).get(n).getDest(); 
			//System.out.println("NEXT VERTEX : "+destV);					

			
			if(!Tree.contains(graph.adjacency.get(currentVertex).get(n)))
			{
				Tree.add(graph.adjacency.get(currentVertex).get(n));
				//System.out.println("Tree Edge : ["+graph.adjacency.get(currentVertex).get(n).getSource()+" -> "+graph.adjacency.get(currentVertex).get(n).getDest()+"]");
			}
			
			if(!vertexConnected.contains(destV))
			{
				vertexConnected.add(destV);
				//System.out.println("Vertex "+currentVertex+" connected");
//				progressBar = (double)vertexConnected.size()/graph.order;
//				progressBar=progressBar*10000;
//				progressBar=(int)progressBar;
//				System.out.println("Creating Tree ["+progressBar/100+"%]");
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
	
	public drunkWizard(Graph g)
	{
		this.graph = g;
		this.Tree = new ArrayList<>();
		this.vertexConnected = new ArrayList<>();		
	}
	
	public static ArrayList<Edge> generateTree(Graph g, int root)
	{
		drunkWizard algo = new drunkWizard(g);
		algo.genRandTree(root);

		return algo.Tree;
	}
}

