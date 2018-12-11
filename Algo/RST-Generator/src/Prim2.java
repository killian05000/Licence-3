package src;


import java.util.ArrayList;
import java.util.BitSet; 
import java.util.LinkedList; 
import java.util.Random;

public class Prim2 {

		Graph graph; 
		LinkedList<Arc> frontierv2;
		ArrayList<Arc> tree; 
		BitSet reached;
		int i = 0;
		
		private void push(int vertex) 
		{ 
			//System.out.println("New");
			for (Arc arc : graph.outAdjacency.get(vertex)) 
			{
				//System.out.println(arc.support.weight);
				sort(arc);
			}
			
			i++;
			/*System.out.println("Result");
			for(Arc arc : frontierv2)
			{
				System.out.println(arc.support.weight);
			}*/
			
			/*if(i == 4)
			{
				System.exit(0);
			}*/
			
		}
		
		private void sort(Arc arc)
		{

			if(frontierv2.isEmpty())
			{
				frontierv2.add(arc);
			}
			else 
			{
				for(int i = 0; i < frontierv2.size();i++)
				{  
					if(arc.support.weight <= frontierv2.get(i).support.weight)
					{
						frontierv2.add(i, arc);
						break;
					}
									
				}
			}
		}
		
		private void explore(Arc nextArc) 
		{ 
			if (reached.get(nextArc.getDest())) return; 
			reached.set(nextArc.getDest()); 
			tree.add(nextArc); 
			push(nextArc.getDest()); 
		} 
		
		private void primeAlgo(int startingVertex) 
		{ 
			reached.set(startingVertex); 
			push(startingVertex);
			while (!frontierv2.isEmpty()) { explore(frontierv2.removeFirst()); } 
			if(frontierv2.isEmpty()) System.out.println("VIDE");
		}
		
		private Prim2 (Graph graph) 
		{ 
			this.graph = graph; 
			this.frontierv2 = new LinkedList<Arc>();
			this.tree = new ArrayList<>(); 
			this.reached = new BitSet(graph.order); 
		}
		
		private void randEdge()
		{
			ArrayList<Integer> save = new ArrayList<Integer>();
			for(int i = 0; i < this.graph.adjacency.size(); i++)
			{
				for(int p = 0; p < this.graph.adjacency.get(i).size();p++)
				{	
					
					if(save.contains(this.graph.adjacency.get(i).get(p).getDest()))
						continue;
					
					Random rand = new Random();
					double randDouble = (double)rand.nextInt(2);
					
					this.graph.adjacency.get(i).get(p).weight = randDouble;
				
				}
				save.add(i);
			}
		}

		public static ArrayList<Arc> generateTree(Graph graph, int root) 
		{ 
			Prim2 algo = new Prim2(graph);
			algo.randEdge();
			algo.primeAlgo(root); 
			
			for(Arc arc : algo.tree) System.out.println(arc.support.weight);
			
			System.out.println("tree size graph : " + algo.tree.size());
			
			System.out.println("Order graph : " + algo.graph.order);
			
			System.out.println("Size adjency graph : " + algo.graph.adjacency.size());
			
			return algo.tree; 
		}

}



