package src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Graph implements Iterable<Edge>
{
    // classe de graphe non orientés permettant de manipuler
    // en même temps des arcs (orientés)
    // pour pouvoir stocker un arbre couvrant, en plus du graphe
    
	int order;
	int edgeCardinality;
	
	ArrayList<LinkedList<Edge>> adjacency; // Undirected graph Edges's list
	ArrayList<LinkedList<Arc>> inAdjacency; // Directed graph incoming Arcs list
	ArrayList<LinkedList<Arc>> outAdjacency; // Directed graph outcoming Arcs list
	
	// Check if the Node number passed as argument is present in the graph
	public boolean isVertex(int index)
	{
		for(LinkedList Edge : adjacency)
			return (Edge.contains(index));
		
		for(LinkedList IncomingArc : inAdjacency)
			return (IncomingArc.contains(index));
		
		for(LinkedList OutcomingArc : outAdjacency) // necessary?
			return (OutcomingArc.contains(index));				
			
		return false;
	}
	
	// Create a List and set its values to null
	public <T> ArrayList<LinkedList<T>> makeList(int size)
	{
		ArrayList<LinkedList<T>> res = new ArrayList<>(size);
		for(int i = 0; i <= size; i++)
		{
			res.add(null);			
		}
		
		return res;
	}
	
	
	public Graph(int upperBound)
	{
		adjacency = makeList(upperBound); // which size ?
		for(int i=0; i< upperBound; i++)
			for (int j=0; j<upperBound; i++)
			{
				if(adjacency.get(0).get(0).getSource()==i && adjacency.get(0).get(0).getDest()==j)
					continue;
				else
				{
					adjacency.get(0).get(0).setSource(i);
					adjacency.get(0).get(0).setDest(j);
				}					
			}				
	    // à remplir
	}
	
	public void addVertex(int indexVertex)
	{
		this.order += 1;
	    // à remplir
	}
	
	public void ensureVertex(int indexVertex) {
	    // à remplir
	}	
	
	public void addArc(Arc arc)  // ???????????
	{
	    inAdjacency.get(inAdjacency.size()).add(arc);
	    outAdjacency.get(outAdjacency.size()).add(arc);
	    // à remplir
	}
	
	public void addEdge(Edge e)
	{
		adjacency.get(adjacency.size()).add(e);
	}

	@Override
	public Iterator<Edge> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object outNeighbours(int vertex) 
	{
//		ArrayList<Arc> list = new ArrayList<>();
//		
//		for(LinkedList Edges : adjacency)
//		{
//			for(Edge ed : Edges )
//				if (ed.getSource()==vertex)
//					list.add(ed.getDest());
//		}
		// TODO Auto-generated method stub
		return null;
	}
	
}
