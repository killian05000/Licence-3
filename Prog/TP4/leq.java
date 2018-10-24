
public class leq implements Predicate {
	
	private int value;
	
	public Leq(int value)
	{
		this.value=value;
	}
	@Override
	boolean test(int i)
	{
		return i <= value;
	}
}
