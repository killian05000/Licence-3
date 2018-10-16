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
    
    //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
  //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
  //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
  //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
  //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
  //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    
    
    public Graph<Label> transpose() {
        Graph<Label> result = new Graph<>(cardinal);
        for (LinkedList<Edge> out_v : incidency) {
            for (Edge e : out_v) {
                result.addArc(e.destination,e.source,e.label);
            }
        }
        return result;
    }

    /**
     *
     * @param source
     * @param visited
     * @param result
     */
    public void innerDfs(int source,
                         ArrayList<Boolean> visited,
                         LinkedList<Integer> result)
    {
        if (visited.get(source)) return;
        visited.set(source,true);
        for (Edge out : incidency.get(source)) {
        	System.out.println("lance DFS sur "+out.destination);
            innerDfs(out.destination,visited,result);
        }
        result.addLast(source);
        return;
    }

    /**
     *
     * @param vertices
     * @return
     */
    public LinkedList<LinkedList<Integer>>
    fullDfsFromList(LinkedList<Integer> vertices)
    {
        ArrayList<Boolean> visited = new ArrayList<>(cardinal);
        LinkedList<LinkedList<Integer>> result = new LinkedList<>();
        for (int i = 0; i < cardinal; i++) {
            visited.add(i,false);
        }
        
        interpretStronglyConnectedComponentFromList(result);

        for (Integer source : vertices) {
            if (visited.get(source)) continue;
            LinkedList<Integer> partial = new LinkedList<>();
            System.out.println("LANCEMENT du DFS sur "+source);
            innerDfs(source,visited,partial);
            result.addLast(partial);
        }
        interpretStronglyConnectedComponentFromList(result);
        return result;

    }

    /**
     *
     * @return
     */
    public LinkedList<LinkedList<Integer>> fullDfs() {
        LinkedList<Integer> vertices = new LinkedList<>();
        for (int i = 0; i < cardinal; i++) {
            vertices.addLast(i);
        }
        return fullDfsFromList(vertices);
    }

    /**
     *
     * @param backward
     * @return
     */
    public LinkedList<LinkedList<Integer>>
    stronglyConnectedComponent(Graph<Label> backward)
    {
        LinkedList<LinkedList<Integer>> firstOrdering = fullDfs();
        LinkedList<Integer> flattening = new LinkedList<>();
        for (LinkedList<Integer> vertices : firstOrdering) {
            for (Integer vertex : vertices) {
                flattening.addFirst(vertex);
            }
        }
        return backward.fullDfsFromList(flattening);
    }

    /**
     *
     * @return
     */
    public LinkedList<LinkedList<Integer>> stronglyConnectedComponent() {
        return this.stronglyConnectedComponent(this.transpose());
}
    
    //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
  //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
  //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
  //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
  //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
  //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    
    
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
        
    /* Initialize Lists needed for the DFS (depth first search)
       this function mainly supervise the DFS function
       but also retrieve from the DFS a list that will be used to
       determine strongly connected components */
    public void searchStronglyConnectedComponents()
    {
    	LinkedList<Integer> vertices = new LinkedList<Integer>();
    	for (int i = 0; i < cardinal; i++)
            vertices.addLast(i);
    	
    	 ArrayList<Boolean> visited = new ArrayList<>(cardinal);
         LinkedList<LinkedList<Integer>> result = new LinkedList<>();
         for (int i = 0; i < cardinal; i++) {
             visited.add(i,false);
         }
         
         interpretStronglyConnectedComponentFromList(result);

         for (Integer source : vertices) {
             if (visited.get(source)) continue;
             LinkedList<Integer> partial = new LinkedList<>();
             System.out.println("LANCEMENT du DFS sur "+source);
             innerDfs(source,visited,partial);
             result.addLast(partial);
         }
         interpretStronglyConnectedComponentFromList(result);
         //return result; 
    }
    
    /* main function proceeding to a 
       depth first search (parcours en profondeur) */
    public void depthFirstSearch(int source,
            ArrayList<Boolean> visited,
            LinkedList<Integer> result)
	{
	if (visited.get(source)) return;
	visited.set(source,true);
	for (Edge out : incidency.get(source)) {
	System.out.println("lance DFS sur "+out.destination);
	innerDfs(out.destination,visited,result);
	}
	result.addLast(source);
	return;
	}
    
    /* display strongly connected components
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
    	//System.out.println("Règle de conversion : si nb < "+cardinal/2+" nb-"+cardinal/2+" et si nb > "+cardinal/2+" nb-"+(cardinal/2-1));
    	for(int i=0; i<list.size(); i++)
    	{
    		for(int j=0; j<list.get(i).size(); j++)
    		{
    			int testedValue = list.get(i).get(j);
	    		for(int k=0; k<list.get(i).size(); k++)
	    		{
	    			//System.out.println("test de "+testedValue+":->"+reconvert(testedValue)+" et "+list.get(i).get(k)+"->"+reconvert(list.get(i).get(k)));
	    			if(reconvert(testedValue)==-reconvert(list.get(i).get(k)))
	    			{
	    				//System.out.println("IIIIIIIIICCCCCCCCCCCCIIIIIIIIIIIIIIIIIIIIII");
	    				return false;
	    			}
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
