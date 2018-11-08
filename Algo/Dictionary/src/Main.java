import java.io.FileNotFoundException;

public class Main 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		double launchTime =System.nanoTime();
		Dictionary dico = new Dictionary("/home/killian/Prog/GitHub/Licence-3/Algo/Dictionary/src/dico.txt");
		System.out.println("Create Dico : "+(System.nanoTime()-launchTime)/1000000000);
		//double launchTime2 =System.nanoTime();
//		System.out.println("LD cost : "+dico.levenshteinDistance("boulanger", "soulager"));
//		System.out.println("Levenshtein distance : "+(System.nanoTime()-launchTime2)/1000000000);
//		System.out.println("Contient le mot : "+dico.search("Killian"));
		System.out.println(dico.TriInCommon("boulnager"));
		
		
	}
}
