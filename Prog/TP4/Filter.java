import java.util.ArrayList;
import java.util.List;

public class Filter {
	private Predicate predicate;
	
	public Filter(Predicate p)
	{
		predicate =p;
	}
	
	public List<Integer> apply(List<Integer> list)
	{
		List<Integer> result = new ArrayList<>();
		for(int i : list)
			if (predicate.test(i))
				result.add(i);
		
		return result;		
	}
}
