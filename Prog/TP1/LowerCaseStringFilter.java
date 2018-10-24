
public class LowerCaseStringFilter implements StringFilter
{
	@Override
	String filter(String string)
	{
		return string.toLowerCase();
	}
}
