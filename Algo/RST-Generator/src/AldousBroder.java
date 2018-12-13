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
		//LinkedList<Integer> stash = new LinkedList<>();
		boolean treeComplete=false;
		
		int currentVertex = root;
		vertexConnected.add(root);
		while(!treeComplete)
		{
			//System.out.println("CURRENT VERTEX : "+currentVertex);			
			int n = (int)(Math.random() *graph.adjacency.get(currentVertex).size());
			int destV = graph.outAdjacency.get(currentVertex).get(n).getDest(); 
			//System.out.println("NEXT VERTEX : "+destV);		
			
			if(!vertexConnected.contains(destV))
			{
				vertexConnected.add(destV);
				Tree.add(graph.adjacency.get(currentVertex).get(n));
				//System.out.println("Vertex "+currentVertex+" connected");
				//System.out.println("Tree Edge : ["+graph.adjacency.get(currentVertex).get(n).getSource()+" -> "+graph.adjacency.get(currentVertex).get(n).getDest()+"]");
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

		return algo.Tree;
	}
}
