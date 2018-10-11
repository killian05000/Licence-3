
public class Sum implements Operator // Sum n'est plus une formula
{
	public static Sum instance= new Sum();
	
	@Override
	public String symbol() {return "+";}
	@Override
	public double initialValue() {return null;}
	@Override
	public double cumulativeValue(double acc, double value) {returnacc+value;}
}