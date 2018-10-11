
//concrete donc instaciable
public class VariadicOperator implements Formula 
{
	private Forumla[] operands;
	private Operator operator;
	
	public VariadicOperator(Formula operands, Operator operator)
	{
		this.operands = operands;
		this.operator = operator;
	}
	
	// Dans asValue() et asString() remplacer symbol() par operator.symbol()
	// et de meme pour les 2 autres methodes
}