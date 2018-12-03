package src;

public class CompleteGraph
{

	Graph graph;
	
	public CompleteGraph(int order)
	{
		this.graph = new Graph(order);
		for(int i = 0; i < order; i++)
			for (int j = i+1; j < order; j++)
				graph.addEdge(new Edge(i,j,0));
		
	}	
	
	
}
