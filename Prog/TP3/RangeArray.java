import java.util.*;
import java.io.IOException;

public class RangeArray
{
  private int [] array;
  private int indexMin;
  
  public static void main(String[] args)
  {
	  int indexMin = -3;
	  int indexMax = 1;
	  RangeArray squares = new RangeArray(indexMin, indexMax);
	  for(int index = squares.getIndexMin(); index <= squares.getIndexMax(); index++)
		  squares.set(index, index*index);
	  
	  for(int index = squares.getIndexMin(); index <= squares.getIndexMax(); index++)
		  System.out.println(index + " -> " + squares.get(index));
	  
	  System.out.println(squares.rangeSize());
	  
  }

  public RangeArray(int _indexMin,int _indexMax)
  {
    assert _indexMin <= _indexMax;
    this.indexMin = _indexMin;
    int size = _indexMax - indexMin + 1;
    array = new int [size];
  }

  public int getIndexMin() { return indexMin;}
  public int getIndexMax() { return indexMin + array.length -1; }
  public int rangeSize(){ return array.length; }
  public int get(int userIndex)
  {
    int realIndex = userToRealIndex(userIndex);
    return array[realIndex];
  }

  public int userToRealIndex(int userIndex)
  {
    assert userIndexIsValid(userIndex);
    return userIndex - indexMin;
  }

  public boolean userIndexIsValid(int userIndex)
  {
    return indexMin() <= userIndex && userIndex <= indexMax();
  }

  public void set(int userIndex, int value)
  {
    int realIndex = userToRealIndex(userIndex);
    array[realIndex] = value;
  }

}
