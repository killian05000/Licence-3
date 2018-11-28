package src;

public class Edge implements Comparable<Edge> {

	protected int source;
	protected int dest;
	double weight;
	
	// create an Edge between 2 nodes
	public Edge(int source, int dest, double weight)
	{
		this.source = source;
		this.dest = dest;
		this.weight = weight;
	}
	
	//compare the weight of 2 Edges
	public int compareTo(Edge e) 
	{
		if (this.weight == e.weight) return 0;
		if (this.weight < e.weight) return -1;
		return 1;
	}
	
	// return the source of the Edge if the valued passed in argument is the Edge's destination
	// else, return the destination (?).
	public int oppositeExtremity(int vertex)
	{
		return (dest == vertex ? source : dest);
	}
	
	public int getSource()
	{
		return this.source;
	}
	
	public int getDest() {
		return this.dest;
	}
	
	public void setSource(int _source)
	{
		this.source = _source;
	}
	
	public void setDest(int _dest)
	{
		this.dest = _dest;
	}
	
}
