import java.util.ArrayList;
import java.util.List;

public class BadFilter 
{
	public enum Type { ODD, LEQ, Gt}
	private Type type;
	private int value;
	
	public BadFilter()
	{
		List<Integer> list= new ArrayList<>();
		for(int i=0; i< 10; i++) 	
			list.add(i);
		List<Integer> result = filterOdd.apply(list);
		result = filterLeq.apply(result);
		
		for(int i: result)
			System.out.println(i);
	}
	public BadFilter(Type type) {this.type = type;}
	public BadFilter(Type type, int value)
	{
		this.type = type;
		this.value = value;
	}
	
	public List<Integer> apply(List<Integer> list)
	{
		List<Integer> result = new ArrayList<Integer>();
		switch(type)
		{
			case ODD: for(int i : list) if (i%2==1) result.add(i);
			break;
			case LEQ: for(int i : list) if (i<=value) result.add(i);
			break;
			case Gt : for(int i : list) if (i<value) result.add(i);
		}
		return result;
	}
	
	//Question 2: 
	//BadFilter filterGt = new BadFilter(BadFilter.Type.Gt,2);
	// return Type { ODD, LEQ, GT};
	
	// Le nbr de filtre est amené a grandir indefinilent, a chaque
	// filtre, on doit redecouvrir la classe pour modifier comme + haut
	//C'est une violation OCP
	// Open/Close Principle
	
	/* On doit pouvoir rajouter des fonctionalités (code ouvert à l'extension)
	 * tout en laissant les classes existantes stables fermées.
	 * Cette violation OCP a pour origine une violation SRP
	 * (Single Responsability Prinicple)
	 * La classe cumule autant de responsabilitées que de filtre*/
	
	/* */
	
}


