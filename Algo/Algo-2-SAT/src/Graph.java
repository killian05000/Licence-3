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
    
    /* initial function that will launch a DFS 
       which will aloud to find out if there is 
       any strongly connected component, and therefore
       if the 2 SAT formula from the file is satisfiable 
       or if it isn't */
    public void launchDFS()
    {
    	LinkedList<Integer> vertices = new LinkedList<Integer>();
    	for (int i = 0; i < cardinal; i++)
            vertices.addLast(i);
    	
    	createDepthFirstSearch(vertices);   	
    	
    }
    
    /* Initialize Lists needed for the DFS (depth first search)
       this function mainly supervise the DFS function
       but also retrieve from the DFS a list that will be used to
       determine strongly connected components */
    public void createDepthFirstSearch(LinkedList<Integer> vertices)
    {
    	ArrayList<Boolean> visited = new ArrayList<>(cardinal);
    	LinkedList<LinkedList<Integer>> result = new LinkedList<>();
    	for (int i = 0; i < cardinal; i++)
            visited.add(i, false);
    	
    	for(Integer source : vertices)
    	{
    		if (visited.get(source)) continue;
    		LinkedList<Integer> SCC = new LinkedList<>(); // StronglyConnectedComponent
    		depthFirstSearch(source,visited,SCC);  
    		result.addLast(SCC);    		
    	}   
    	
    	interpretStronglyConnectedComponentFromList(result);
    }
    
    /* main function proceeding to a 
       depth first search (parcours en profondeur) */
    public void depthFirstSearch(int source, ArrayList<Boolean> visited, LinkedList<Integer> SCC)
    {
    	if (visited.get(source))
    		return;
    	
    	visited.set(source, true);
    	
    	for (Edge vertex : incidency.get(source)) 
			depthFirstSearch(vertex.destination,visited,SCC);
    	
    	SCC.addLast(source);
    	return;
    	
    }
    
    /* interpret (display) strongly connected components
       from a list passed as an argument */
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
    	
    	if(isSatisfied(list))
    		System.out.println("La formule est satisfiable");
    	else
    		System.out.println("La formule n'est pas satisfiable");
    		
    }
    
    /* Verify if a strongly connected component don't include
     a variable and its opposite */
    public Boolean isSatisfied(LinkedList<LinkedList<Integer>> list)
    {
    	for(int i=0; i<list.size(); i++)
    	{
    		for(int j=0; j<list.get(i).size(); j++)
    		{
    			int testedValue = list.get(i).get(j);
	    		for(int k=0; k<list.get(i).size(); k++)
	    		{
	    			if(reconvert(testedValue)==-reconvert(list.get(i).get(k)))
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
