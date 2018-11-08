import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Dictionary
{
	private HashMap<String, ArrayList<String>> map;
	
	public Dictionary(String filePath) throws FileNotFoundException
	{
		File file = new File(filePath);
		Scanner scanner = new Scanner(file);
		
		map = new HashMap<String, ArrayList<String>>();
		
		double launchTime =System.nanoTime();
		buildMap("Algorithmique", map);
		System.out.println("Trigramme Algorithmique : "+(System.nanoTime()-launchTime)/1000000000);

		
		while(scanner.hasNextLine())
		{
			String _word = scanner.nextLine();
			buildMap(_word,map);
		}
		
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
	
	public HashMap<String, Integer> TriInCommon(String _word)
	{
		HashMap<String, Integer> TriMap = new HashMap<String, Integer>();
		ArrayList<String> list = new ArrayList<String>();
		int it=0;
		String word = "<"+_word+">";
		for (int i=0; i<=word.length()-3; i++)
		{
			String Trigrame = word.substring(i,i+3);
			if(map.containsKey(Trigrame))
			{
				for(int k=0; k<map.get(Trigrame).size(); k++) 
				{
					it++;
					String word2 = map.get(Trigrame).get(k);
					if(TriMap.containsKey(word2))
					{
						TriMap.put(word2,TriMap.get(word2)+1);
					}
					else
						TriMap.put(word2,1);
				
					//System.out.print(map.get(Trigrame).get(k)+" et "+ _word+" one une distance de "+(levenshteinDistance(map.get(Trigrame).get(k), _word)));
					//list.add(map.get(Trigrame).get(k));
				}
			}				
		} 
		
		TriMap = mapSortDescending(TriMap);
		HashMap<String, Integer> bestWords = new HashMap<String, Integer>();
		int counter =0;
		for(Entry<String, Integer> mapEntry : TriMap.entrySet())
		{
		  if(counter<100)
		  {
		    bestWords.put(mapEntry.getKey(), mapEntry.getValue());
		    counter++;
		  }
		  else
			  break;
		}
		
		HashMap<String, Integer> ultimateWords = new HashMap<String, Integer>();
		
		for(Entry<String, Integer> mapEntry : bestWords.entrySet())
		{
			int dist = levenshteinDistance(_word, mapEntry.getKey());
			ultimateWords.put(mapEntry.getKey(), dist);
		}
		
		ultimateWords = mapSortAscending(ultimateWords);
		
		HashMap<String, Integer> lastWords = new HashMap<String, Integer>();
		counter=0;
		
		for(Entry<String, Integer> mapEntry : ultimateWords.entrySet())
		{
		  if(counter<5)
		  {
		    lastWords.put(mapEntry.getKey(), mapEntry.getValue());
		    counter++;
		  }
		  else
			  break;
		}
		
		lastWords = mapSortAscending(lastWords);
		
		// Trier les valeurs de TriMap dans l'ordre croissant
		// garder les 100 premiers et faire une levensthein sur les 100
		// retrier et prendre les 5 plus petites distances
		
		System.out.println("nb d'iterations : "+it);
		return lastWords;
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
	
	public static HashMap<String, Integer> mapSortAscending( HashMap<String, Integer> map )
	{
		   List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>( map.entrySet() );
		   
		   Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
		   {
		      public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
		      {
		          return (o1.getValue()).compareTo( o2.getValue());
		      }
		   });

		   HashMap<String, Integer> map_apres = new LinkedHashMap<String, Integer>();
		   for(Map.Entry<String, Integer> entry : list)
		     map_apres.put( entry.getKey(), entry.getValue() );
		   return map_apres;
	}
	
	public static HashMap<String, Integer> mapSortDescending( HashMap<String, Integer> map )
	{
		   List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>( map.entrySet() );
		   
		   Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
		   {
		      public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
		      {
		          return (o2.getValue()).compareTo( o1.getValue());
		      }
		   });

		   HashMap<String, Integer> map_apres = new LinkedHashMap<String, Integer>();
		   for(Map.Entry<String, Integer> entry : list)
		     map_apres.put( entry.getKey(), entry.getValue() );
		   return map_apres;
	}
	
}
