
public interface Predicate {
	//Question 3
	public booleantest(int i);
	//Question 4
	Filter filterOdd = new Filter(new Odd());
	Filter filterLeq = new Filter(new Leq(6));
	//filterOdd.apply(...)
}
