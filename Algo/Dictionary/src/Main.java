import java.io.FileNotFoundException;

public class Main 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		//Dictionary dico = new Dictionary("/home/killian/Prog/GitHub/Licence-3/Algo/Dictionary/src/dico.txt");
//		System.out.println("LD cost : "+dico.levenshteinDistance("boulanger", "soulager"));
//		System.out.println("Levenshtein distance : "+(System.nanoTime()-launchTime2)/1000000000);
//		System.out.println(dico.TriInCommon("boulnager"));
		exec();
		
		
	}
	
	public static void exec() throws FileNotFoundException
	{
		double launchTime = System.nanoTime();
		Dictionary dico = new Dictionary("src/dico.txt");
		//dico.correctFile("src/fautes.txt");
		System.out.println("Temps d'execution de la fonction : "+(System.nanoTime()-launchTime)/1000000000);
	}
}
