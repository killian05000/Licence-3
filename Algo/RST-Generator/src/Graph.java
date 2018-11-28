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
	
	ArrayList<LinkedList<Edge>> adjacency; // Undirected graph Edges's List
	ArrayList<LinkedList<Arc>> inAdjacency; // Directed graph
	ArrayList<LinkedList<Arc>> outAdjacency;
	
	// Check if the Node number passed as argument is present in the graph
	public boolean isVertex(int index) {
		for(LinkedList Edge : adjacency)
			return (Edge.contains(index));
		
		for(LinkedList ArcIn : inAdjacency)
			return (ArcIn.contains(index));
		
		for(LinkedList ArcOut : outAdjacency)
			return (ArcOut.contains(index));				
			
		return false;
	}
	
	public <T> ArrayList<LinkedList<T>> makeList(int size) {
		ArrayList<LinkedList<T>> res = new ArrayList<>(size);
		for(int i = 0; i <= size; i++) {
			res.add(null);			
		}
		return res;
	}
	
	public Graph(int upperBound) {
	    // à remplir
	}
	
	public void addVertex(int indexVertex) {
	    // à remplir
	}
	
	public void ensureVertex(int indexVertex) {
	    // à remplir
	}	
	
	public void addArc(Arc arc) {
	    // à remplir
	}
	
	public void addEdge(Edge e) {
	    // à remplir
	}

	@Override
	public Iterator<Edge> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object outNeighbours(int sommet) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
