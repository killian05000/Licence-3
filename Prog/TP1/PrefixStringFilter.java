
public class PrefixStringFilter implements StringFilter
{	
	private int n=0;
	
	public PrefixStringFilter(int _n)
	{
		n = _n;
	}
	
	@Override
	String filter(String string)
	{
		assert n>0 && n<=string.length();
		return string.subString(0, n-1);
	}
}
