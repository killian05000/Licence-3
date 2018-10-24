
public class SpellChecker
{
	public int levenshteinDistance(String a, String b)
	{
		int m[][] = new int[a.length()+2][b.length()+2];
		
		for (int i=1; i<m.length; i++)
		{
			m[i][1]=i-1;
			m[1][i]=i-1;
		}

		for (int i=2; i<m.length; i++)
		{
			for (int k=2; k<m[0].length; k++)
			{
				int db = m[i-1][k-1]; // diagonal box
				int lb = m[i][k-1]; // left box
				int tb = m[i-1][k]; // top box
				
				if(m[i][k] == m[i-1][k-1])
					m[i][k]=min(db, lb+1, tb+1);
				else				
					m[i][k]=min(db+1, lb+1, tb+1);
			}
		}
		
		return m[m.length-1][m[0].length-1];		
	}
	
	public int min(int a, int b, int c)
	{
		if (a<b && a<c)
			return a;		
		else if (b<a && b<c)
			return b;		
		else
			return c;		
	}
}
