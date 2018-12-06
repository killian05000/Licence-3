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
	
	public ArrayList<LinkedList<Edge>> adjacency; // Undirected graph Edges's list
	public ArrayList<LinkedList<Arc>> inAdjacency; // Directed graph incoming Arcs list
	public ArrayList<LinkedList<Arc>> outAdjacency; // Directed graph outcoming Arcs list
	
	// Check if the Node number passed as argument is present in the graph
	public boolean isVertex(int index)
	{
//		for(LinkedList Edge : adjacency)
//			return (Edge.contains(index));
//		
//		for(LinkedList IncomingArc : inAdjacency)
//			return (IncomingArc.contains(index));
		
		return index < adjacency.size() && index >=0 && adjacency.get(index) != null;
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
//		adjacency = makeList(upperBound); // which size ?
//		for(int i=0; i< upperBound; i++)
//			for (int j=0; j<upperBound; i++)
//			{
//				if(adjacency.get(0).get(0).getSource()==i && adjacency.get(0).get(0).getDest()==j)
//					continue;
//				else
//				{
//					adjacency.get(0).get(0).setSource(i);
//					adjacency.get(0).get(0).setDest(j);
//				}					
//			}				
		order = upperBound;
		adjacency = makeList(upperBound);
		inAdjacency = makeList(upperBound);
		outAdjacency = makeList(upperBound);
	}
	
	public void addVertex(int indexVertex)
	{
		ensureVertex(indexVertex);
	}
	
	public void ensureVertex(int indexVertex) 
	{
	    if(!isVertex(indexVertex))
	    {
	    	this.order += 1;
			adjacency.ensureCapacity(adjacency.size()+1);
			inAdjacency.ensureCapacity(inAdjacency.size()+1);
			outAdjacency.ensureCapacity(outAdjacency.size()+1);
			adjacency.set(indexVertex, new LinkedList<Edge>());
			inAdjacency.set(indexVertex, new LinkedList<Arc>());
			outAdjacency.set(indexVertex, new LinkedList<Arc>());
	    }
	}
	
	public void addArc(Arc arc)  // ???????????
	{
		inAdjacency.get(arc.getDest()).add(arc);
	    outAdjacency.get(arc.getSource()).add(arc);
	}
	
	public void addEdge(Edge e)
	{
		adjacency.get(e.getSource()).add(e);
		adjacency.get(e.getDest()).add(e);
	}

	@Override
	public Iterator<Edge> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public LinkedList<Arc> outNeighbours(int vertex) 
	{
//		LinkedList<Edge> edgeList = new LinkedList<>();		
//		edgeList = adjacency.get(vertex);
		
		LinkedList<Arc> list = new LinkedList<>();
		list = outAdjacency.get(vertex);

		return list;
	}
	
}
