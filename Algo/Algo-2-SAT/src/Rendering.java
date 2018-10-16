import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public abstract class Rendering {
	private static int Nbi; // Number of items
	
	public static void main(String[] Args) throws FileNotFoundException
	{
		//file List: unsatisfiable formula && file ListF: satisfiable formula
		ArrayList<Graph<String>> GList = ReadFile("/home/killian/eclipse-workspace/Algo-2-SAT/src/ListTest");
		
		// Display() = describe the graph with words and number (readable)
		//System.out.println("Graphe non transposé issu du ficher : \n"+GList.get(0).display());
		//System.out.println("Graphe transposé issu du ficher : \n"+GList.get(1).display());
		// toString() = display neighboring vertices (raw)
		//System.out.println(GList.get(0).toString());
		//System.out.println(GList.get(1).toString());
		
		GList.get(1).searchStronglyConnectedComponents();
		
		System.out.println("\n\n\n");
		
		System.out.println("Composantes fortement connexes : " + GList.get(0).stronglyConnectedComponent());
	}
	
	//Proceed to the files's reading and store its data	
	public static ArrayList<Graph<String>> ReadFile(String fp) throws FileNotFoundException
	{
		System.out.println("--- Lecture du fichier et création du graphe --- \n");
		File filePath = new File(fp);
		Scanner scanner = new Scanner (filePath);		
				
		Nbi = scanner.nextInt();
		Graph<String> G = new Graph<String>(Nbi*2);
		Graph<String> Gt = new Graph<String>(Nbi*2);
		ArrayList<Graph<String>> GList = new ArrayList<Graph<String>>(); 
		GList.add(G); GList.add(Gt);
		
		
		int[] tab = new int[2];
		
		while (scanner.hasNextInt())
		{
	            tab[0]=scanner.nextInt();
	            tab[1]=scanner.nextInt();
	            buildGraph(G,Gt,tab);
		}
		
		scanner.close();
		return GList;
	}
	
	//Create a Graph and a transposed Graph based on the file's data
	public static void buildGraph(Graph<String> G, Graph<String> Gt, int[] tab)
	{		
		int[] l1 = new int[2];
		int[] l2 = new int[2];
		
		l1[0]=-tab[0]; l1[1]=tab[1];
		l2[0]=-tab[1]; l2[1]=tab[0];
		
		G.addArc(transform(l1[1]), transform(l1[0]), "");
		G.addArc(transform(l2[1]), transform(l2[0]), "");
		
		Gt.addArc(transform(l1[0]), transform(l1[1]), "");
		Gt.addArc(transform(l2[0]), transform(l2[1]), "");
	}
	
	//Transform the file's data (vertices name) to increase
	//the graph's readability 
	public static int transform(int a)
	{
		if(a < 0)
			a+=Nbi;
		else
			a+=Nbi-1;
		
		return a;
	}	
}
