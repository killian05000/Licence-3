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
		double launchTime = System.nanoTime();
		File file = new File(filePath);
		Scanner scanner = new Scanner(file);
		
		map = new HashMap<String, ArrayList<String>>();
		
		while(scanner.hasNextLine())
		{
			String _word = scanner.nextLine();
			buildMap(_word,map);
		}
		
		System.out.println("Taille du dico trigramme : "+map.size());
		scanner.close();
		System.out.println("Temps total de la création du dictionnaire : "+(System.nanoTime()-launchTime)/1000000000);
	}
	
	/* create the Trigramme dictionary */
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
	
	/* Check if the word passed in argument is present in the dictionary */
	public boolean isCorrect(String word)
	{
		String Trigrame = word.substring(1,4);	
		
		if (!map.containsKey(Trigrame))
			return false;
		else
		{
			if(map.get(Trigrame).contains(word))
				return true;
			else
			{
				spellChecker(word);
				return false;
			}
		}		
	}
	
	/* Call spellChecker upon each words of the file and display
	 * the time cost of this function. Can also display the corrections
	 * word by word.
	 */
	public void correctFile(String filePath) throws FileNotFoundException
	{
		File file = new File(filePath);
		Scanner scanner = new Scanner(file);

		ArrayList<String> words = new ArrayList<String>();
		
		while(scanner.hasNextLine())
		{
			String _word = scanner.nextLine();
			words.add(_word);
		}
		
		/* The following code block is gonna correct the File a several amount
		 * of times so we can get an average of the actual execution time cost.
		 */
		
		int count = 20; // number of function launches
		double total = 0.0;

		for (int i = 0; i < count; ++i) 
		{
			double launchTime = System.nanoTime();

			for (String element : words)
			{
				spellChecker(element);
				//System.out.println(element+" = "+spellChecker(element)); // display the words correction one by one
			}

			total += System.nanoTime() - launchTime;
		}

		System.out.println("Temps total en moyenne de la correction du fichier : " + total / count / 1000000000);
		scanner.close();
	}
	
	/* Return 5 similar words to the word passed in argument */
	public List<String> spellChecker(String _word)
	{
		HashMap<String, Integer> TriMap = new HashMap<String, Integer>();
		String word = "<"+_word+">";
		
		for (int i=0; i<=word.length()-3; i++)
		{
			String Trigrame = word.substring(i,i+3);
			if(map.containsKey(Trigrame))
			{
				for(int k=0; k<map.get(Trigrame).size(); k++) 
				{
					String word2 = map.get(Trigrame).get(k);					
					if(TriMap.containsKey(word2))
						TriMap.put(word2,TriMap.get(word2)+1);
					else
						TriMap.put(word2,1);
				}
			}				
		} 
		
		/* The following part is gonna sort the map <Word, Trig. in common> decreasingly
		 * so we can keep the 100 first words which have the most trigrammes in common
		 * with the word passed in the function's argument.
		 */
		
		List<String> triList = mapSortDescending(TriMap);
		List<String> bestTriWords = new ArrayList<>(triList.size());
		int counter =0;
		for(String w : triList)
		{
		  if(counter<100)
		  {
		    bestTriWords.add(w);
		    counter++;
		  }
		  else
			  break;
		}
		
		/* Here we are gonna proceed to a calculation of the levenshtein distance upon
		 * the map obtained previously and sort the resulting map increasingly. Then the
		 * first values of the map are the words with the lowest levenshtein distance.
		 */
		
		HashMap<String, Integer> levenshteinDWords = new HashMap<String, Integer>();
		
		for(String w : bestTriWords)
		{
			int dist = levenshteinDistance(_word, w);
			levenshteinDWords.put(w, dist);
		}
		
		List<String> levenshteinDList = mapSortAscending(levenshteinDWords);
		
		// Finally we only retrieve 5 words with the lowest levenshtein distance
		
		List<String> closestWords = new ArrayList<>(levenshteinDList.size());
		counter=0;
		
		for(String w : levenshteinDList)
		{
		  if(counter<5)
		  {
		    closestWords.add(w);
		    counter++;
		  }
		  else
			  break;
		}
		//System.out.println(closestWords);
		return closestWords;
	}
	
	/* Calcul and return the levenshtein distance between 2 words, this distance
	 * is the number of operations required to go from one word to the
	 * other.
	 */
	public int levenshteinDistance(String a, String b)
	{
		int m[][] = new int[a.length()+1][b.length()+1];
		
		for (int i=0; i<m.length; i++)
			m[i][0]=i;
		for (int i=0; i<m[0].length; i++)
			m[0][i]=i;	
		
		for (int i=1; i<m.length; i++)
		{
			for (int k=1; k<m[0].length; k++)
			{
				int db = m[i-1][k-1]; // diagonal box
				int lb = m[i][k-1]; // left box
				int tb = m[i-1][k]; // top box
								
				if(a.charAt(i-1) == b.charAt(k-1))
					m[i][k]=min(db, lb+1, tb+1);
	
				else			
					m[i][k]=min(db+1, lb+1, tb+1);
			}
		}
		
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
	
	/* sort the key of a map<String, Int> increasingly and return a list*/
	public static List<String> mapSortAscending( HashMap<String, Integer> map )
	{
		   List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>( map.entrySet() );
		   
		   Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
		   {
		      public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
		      {
		          return (o1.getValue()).compareTo( o2.getValue());
		      }
		   });

		   List<String> l = new ArrayList<>(map.size());

			for (Map.Entry<String, Integer> entry : list) {
				l.add(entry.getKey());
			}
			return l;
	}
	
	/* sort the key of a map<String, Int> decreasingly and return a list */
	public static List<String> mapSortDescending( HashMap<String, Integer> map )
	{
		   List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>( map.entrySet() );
		   
		   Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
		   {
		      public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
		      {
		          return (o2.getValue()).compareTo( o1.getValue());
		      }
		   });

		   List<String> l = new ArrayList<>(map.size());

			for (Map.Entry<String, Integer> entry : list) {
				l.add(entry.getKey());
			}
			return l;
	}
	
}
