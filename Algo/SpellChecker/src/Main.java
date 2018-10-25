import java.io.FileNotFoundException;

public class Main 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		double launchTime =System.nanoTime();
		SpellChecker sc = new SpellChecker("/home/killian/Prog/GitHub/Licence-3/Algo/SpellChecker/src/dico.txt");
		System.out.println("Create Dico : "+(System.nanoTime()-launchTime)/1000000000);
		double launchTime2 =System.nanoTime();
		System.out.println("cost : "+sc.levenshteinDistance("mireille", "milereille"));
		System.out.println("Levenshtein distance : "+(System.nanoTime()-launchTime2)/1000000000);
	}
}
