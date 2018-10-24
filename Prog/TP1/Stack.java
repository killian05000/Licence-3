package TP1;


public class Stack {
	private Vector v;
	
	public void push(int value)
	{
		v.add(value);
	}
	
	public int peek()
	{
		return v.get(v.size()-1)
;	}
	
	public int pop()
	{
		int a = v.size();
		v.resize(v.size()-1);
		return a;
	}
	
	public int getSize()
	{
		return v.size();
	}
	
	public boolean isEmpty()
	{
		return v.isEmpty();
	}
	
}
