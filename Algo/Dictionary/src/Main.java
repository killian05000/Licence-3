import java.io.FileNotFoundException;

public class Main 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		Dictionary dico = new Dictionary("src/dico.txt");
		dico.spellChecker("boulnager");
		//dico.correctFile("src/fautes.txt");
	}
}
