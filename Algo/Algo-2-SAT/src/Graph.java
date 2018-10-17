import java.util.ArrayList;
import java.util.LinkedList;
public class Graph<Label> {
    private class Edge { //Arcs
        public int source;
        public int destination;
        public Label label;
        public Edge(int from, int to, Label label) {
            this.source = from;
            this.destination = to;
            this.label = label;
        }
    }
    private int cardinal;
    private ArrayList<LinkedList<Edge>> incidency;
    public Graph(int size) { //
        cardinal = size;
        incidency = new ArrayList<LinkedList<Edge>>(size+1);
        for (int i = 0;i<cardinal;i++) {
            incidency.add(i, new LinkedList<Edge>());
        }
    }
    public int order() {
        return cardinal;
    }
    public void addArc(int source, int dest, Label label) {
        incidency.get(source).addLast(new Edge(source,dest,label));
    }
    
    
    // display neighboring vertices (raw form)
    @Override
    public String toString() {
        String result = new String("");
        result = result.concat(+cardinal + "\n");
        for (int i = 0; i<cardinal;i++) {
            for (Edge e : incidency.get(i)) {
                result = result.concat(e.source + " " + e.destination + " "
                        + e.label.toString() + "\n");
            }
        }
        return result;
    }
    
    // describe the graph with words and number (more readable)
    public String display() {
        String result = new String("");
        result = result.concat("Nombre de sommets : "+cardinal + "\n");
        for (int i = 0; i<cardinal;i++) {
            for (Edge e : incidency.get(i)) {
                result = result.concat("Il y a un arc du sommet "+e.source + " au sommet " + e.destination + " "
                        + e.label.toString() + "\n");
            }
        }
        return result;
    }
    
    /* Perform to a first DFS and store the strongly connected 
     * components retrieved. Revert this list, now starting by
     * the last element of the last SCC found and proceed to
     * a second DFS from it to retrieve different SCC.
     * Return those latter */
    
    public LinkedList<LinkedList<Integer>> getStronglyConnectedComponent(Graph<Label> Gt)
    {
        LinkedList<LinkedList<Integer>> firstOrdering = fullDFS();
        LinkedList<Integer> flattening = new LinkedList<>();        
        for (LinkedList<Integer> vertices : firstOrdering)
            for (Integer vertex : vertices)
                flattening.addFirst(vertex);

        return Gt.fullDFSFromList(flattening);
}
    
    /* Create a list of the graph's vertices ordered 
       in a crescent way and proceed to a DFS on it */
    public LinkedList<LinkedList<Integer>> fullDFS()
    {
    	LinkedList<Integer> vertices = new LinkedList<Integer>();
    	for (int i = 0; i < cardinal; i++)
            vertices.addLast(i);
    	
    	return fullDFSFromList(vertices);   	
    	
    }
    
    /* Initialize and create lists needed for a DFS procedure
     * and store the values obtained.
     * This function will launch a DFS on each vertices of the list 
     * passed in argument, and return a list where they are ordered
     * by their date of treatment */
    public LinkedList<LinkedList<Integer>> fullDFSFromList(LinkedList<Integer> vertices)
    {
    	ArrayList<Boolean> visited = new ArrayList<>(cardinal);
    	LinkedList<LinkedList<Integer>> result = new LinkedList<>();
    	for (int i = 0; i < cardinal; i++)
            visited.add(i, false);
    	
    	for(Integer source : vertices)
    	{
    		if (visited.get(source)) continue;
    		LinkedList<Integer> chain = new LinkedList<>();
    		depthFirstSearch(source,visited,chain);  
    		result.addLast(chain);    		
    	}   
    	
    	return result;
    }
    
    /* Recursive function that will perform a depth first search */
    public void depthFirstSearch(int source, ArrayList<Boolean> visited, LinkedList<Integer> chain)
    {
    	if (visited.get(source))
    		return;
    	
    	visited.set(source, true);
    	
    	for (Edge vertex : incidency.get(source)) 
			depthFirstSearch(vertex.destination,visited,chain);
    	
    	chain.addLast(source);
    	return;
    	
    }
    
    /* display strongly connected components
       from a list passed as an argument and
       indicate if the formula on which the 
       graph is based is satisfiable or if 
       it isn't. */
    public void interpretStronglyConnectedComponentFromList(LinkedList<LinkedList<Integer>> list)
    {
    	System.out.print("Composantes fortement connexes : [");
    	for(int i=0; i<list.size(); i++)
    	{
    		System.out.print("(");
    		for(int k=0; k<list.get(i).size(); k++)
    		{
    			System.out.print(list.get(i).get(k));
    			if(k!=list.get(i).size()-1)
    				System.out.print(",");
    		}
    		System.out.print(")");
    		if(i!=list.size()-1)
    			System.out.print(", ");
    	}
    	System.out.print("]"); 
    	System.out.println();
    	
    	if(isSatisfiable(list))
    		System.out.println("La formule est satisfiable");
    	else
    		System.out.println("La formule n'est pas satisfiable");
    		
    }
    
    /* Verify if a strongly connected component don't
       include a variable and its opposite.
       Used to verify is the formula is satisfiable */
    public Boolean isSatisfiable(LinkedList<LinkedList<Integer>> list)
    {
    	for(int i=0; i<list.size(); i++)
    	{
    		for(int j=0; j<list.get(i).size(); j++)
    		{
    			int testedValue = list.get(i).get(j);
	    		for(int k=0; k<list.get(i).size(); k++)
	    		{
	    			if(reconvert(testedValue)+reconvert(list.get(i).get(k))==0)
	    				return false;
	    		}
    		}
    	}
    	
    	return true;
    }
    
    /* Used to convert the number from 0 to cardinal-1
       to their original forms from the list */
    public int reconvert(int a)
    {
    	int Nbi = cardinal/2;
    	if(a < Nbi )
			a-=Nbi;
		else
			a-=Nbi-1;
    	
    	return a;
    }
    public interface ArcFunction<Label,K> {
        public K apply(int source, int dest, Label label, K accu);
    }
    public interface ArcConsumer<Label> {
        public void apply(int source, int dest, Label label);
    }
    public <K> K foldEdges(ArcFunction<Label,K> f, K init) {
        for (LinkedList<Edge> adj : this.incidency) {
            for (Edge e : adj) {
                init = f.apply(e.source, e.destination, e.label, init);
            }
        };
        return init;
    }
    public void iterEdges(ArcConsumer<Label> f) { //semble être pour changer sens des arcs
        for (LinkedList<Edge> adj : this.incidency) {
            for (Edge e : adj) {
                f.apply(e.source, e.destination, e.label);
            }
        }
    }
}