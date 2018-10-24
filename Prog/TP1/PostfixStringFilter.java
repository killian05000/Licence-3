
public class PostfixStringFilter implements StringFilter
{	
	private int n=0;
	
	public PrefixStringFilter(int _n)
	{
		n = _n;
	}
	
	@Override
	String filter(String string)
	{
		assert n<=length() && n>=0; 
		return string.subString(string.length()-n, string.length());
	}
}
