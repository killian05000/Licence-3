import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SpellChecker
{
	public SpellChecker(String filePath) throws FileNotFoundException
	{
		File file = new File(filePath);
		Scanner scanner = new Scanner(file);
		
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		
		while(scanner.hasNextLine())
		{
			String _word = scanner.nextLine();
			buildMap(_word,map);
		}
		
		buildMap("Turbo_gnocchi",map);
		System.out.println("Map.get(rmx) = "+map.get("rmx"));
		System.out.println("Taille de la map "+map.size());
	}
	
	public void buildMap(String _word, HashMap<String,ArrayList<String>> map)
	{
		String word = "<"+_word+">";
		for (int i=0; i<=word.length()-3; i++)
		{
			String Tri = word.substring(i,i+3); 
			if (map.containsKey(Tri))
				map.get(Tri).add(_word);
			else
			{
				ArrayList<String> list = new ArrayList<String>();
				list.add(_word);
				map.put(Tri, list);
			}
		}
	}
	
	public void search(String a)
	{
		
	}
	
	public int levenshteinDistance(String a, String b)
	{
		int m[][] = new int[a.length()+1][b.length()+1];
		
		for (int i=0; i<m.length; i++)
		{
			m[i][0]=i;
		}		
		for (int i=0; i<m[0].length; i++)
		{
			m[0][i]=i;
		}		
		
		System.out.println();

		for (int i=1; i<m.length; i++)
		{
			for (int k=1; k<m[0].length; k++)
			{
				int db = m[i-1][k-1]; // diagonal box
				int lb = m[i][k-1]; // left box
				int tb = m[i-1][k]; // top box
				
				//System.out.println("--> Comparaison "+a.charAt(i-1)+" et "+b.charAt(k-1));
				
				if(a.charAt(i-1) == b.charAt(k-1))
				{
					//System.out.println("min entre "+db+" "+(lb+1)+" "+(tb+1)+" = "+min(db, lb+1, tb+1));
					m[i][k]=min(db, lb+1, tb+1);
				}
				else				
				{
					//System.out.println("min entre "+(db+1)+" "+(lb+1)+" "+(tb+1)+" = "+min(db+1, lb+1, tb+1));
					m[i][k]=min(db+1, lb+1, tb+1);
				}
			}
			//System.out.println();
		}
//		
//		for(int i=0; i<m.length; i++)
//		{
//			for(int k=0; k<m[0].length; k++)
//			{
//				System.out.print(m[i][k]+"  ");
//			}
//			System.out.println();
//		}	
		return m[m.length-1][m[0].length-1];		
	}
	
	public int min(int a, int b, int c)
	{
		if (a<=b && a<=c)
			return a;		
		else if (b<=a && b<=c)
			return b;		
		else
			return c;		
	}
}
