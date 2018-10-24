
public class Main 
{
	public static void main(String[] args)
	{
		SpellChecker sc = new SpellChecker();
		System.out.println("cost : "+sc.levenshteinDistance("killian", "knilian"));
	}
}
