import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Dictionary
{
	private HashMap<String, ArrayList<String>> map;
	
	public Dictionary(String filePath) throws FileNotFoundException
	{
		File file = new File(filePath);
		Scanner scanner = new Scanner(file);
		
		map = new HashMap<String, ArrayList<String>>();
		
		while(scanner.hasNextLine())
		{
			String _word = scanner.nextLine();
			buildMap(_word,map);
		}
		
		buildMap("Turbo_gnocchi",map);
		System.out.println("Map.get(rmx) = "+map.get("rmx"));
		System.out.println("Taille de la map : "+map.size());
		scanner.close();
	}
	
	public void buildMap(String _word, HashMap<String,ArrayList<String>> map)
	{
		String word = "<"+_word+">";
		for (int i=0; i<=word.length()-3; i++)
		{
			String Trigrame = word.substring(i,i+3); 
			if (map.containsKey(Trigrame))
				map.get(Trigrame).add(_word);
			else
			{
				ArrayList<String> list = new ArrayList<String>();
				list.add(_word);
				map.put(Trigrame, list);
			}
		}
	}
	
	public boolean search(String word)
	{
		String Trigrame = word.substring(1,4);	
		
		if (!map.containsKey(Trigrame))
			return false;
		else
		{
			if(map.get(Trigrame).contains(word))
				return true;
			else
				return false;
		}			
	}
	
	public ArrayList<String> spellChecker(String _word)
	{
		ArrayList<String> list = new ArrayList<String>();
		int it=0;
		String word = "<"+_word+">";
		for (int i=0; i<=word.length()-3; i++)
		{
			String Trigrame = word.substring(i,i+3);
			if(map.containsKey(Trigrame) && list.size() <5)
			{
				for(int k=0; k<map.get(Trigrame).size(); k++)
				{
					it++;
					if((levenshteinDistance(map.get(Trigrame).get(k), _word) <= 2) && (list.size() <5) && (!list.contains(map.get(Trigrame).get(k))))
					{
						//System.out.print(map.get(Trigrame).get(k)+" et "+ _word+" one une distance de "+(levenshteinDistance(map.get(Trigrame).get(k), _word)));
						list.add(map.get(Trigrame).get(k));
					}
				}
			}				
		} 
		System.out.println("nb d'iterations : "+it);
		return list;
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
