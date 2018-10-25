import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class SpellChecker
{
	public SpellChecker(String filePath) throws FileNotFoundException
	{
		File file = new File(filePath);
		Scanner scanner = new Scanner(file);
		
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		
		int i=0;
		while(scanner.hasNextLine())
		{
			map.put(i, scanner.nextLine());
			i++;
		}
		
		System.out.println(map.get(0));
		System.out.println(map.get(i-1));
		
		System.out.println(map.size());
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
