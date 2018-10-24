
public class And implements Predicate{ // And est un predicate qui est la conjonction de n predicates
	private Predicate[] terms;
	
	public And(Predicate ...predicates terms)
	{
		this.terms=terms;
	}
	
	@Override
	boolean test(int i)
	{
		for(Predicate term : term)
			if (!term.booleantest(i))
				return false;
		
		return true;
	}
}
