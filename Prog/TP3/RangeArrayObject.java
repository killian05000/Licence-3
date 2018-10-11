import java.util.Iterator;

public class RangeArrayObject<T> implements Iterable<T>
{
  private Object [] array;
  private int indexMin;
  
  public static void main(String[] args)
  {
	  RangeArrayObject<String> habitations = new RangeArrayObject<String>(3,5);
	  habitations.set(3, "Maison");
	  habitations.set(4,  "Immeuble");
	  habitations.set(5, "Hutte");
	  for(int index = habitations.getIndexMin(); index <= habitations.getIndexMax(); index++)
		  System.out.println(habitations.get(index).length() + " ");
	  
	  for(String habitation : habitations)
		  System.out.println(habitation + " ");
  }
  
  public int getIndexMin() { return indexMin;}
  public int getIndexMax() { return indexMin + array.length -1; }

  public RangeArrayObject(int _indexMin,int _indexMax)
  {
	assert _indexMin <= _indexMax;
    this.indexMin = _indexMin;
    int size = _indexMax - indexMin + 1;
    array = new Object [size];
  }

  @SuppressWarnings("unchecked")
  public  T get(int userIndex)
  {
    int realIndex = userToRealIndex(userIndex);
    return (T) array[realIndex];
  }

  public void set (int userIndex, T value)
  {
    int realIndex = userToRealIndex(userIndex);
    array[realIndex] = value;
  }
  
  public int userToRealIndex(int userIndex)
  {
    assert userIndexIsValid(userIndex);
    return userIndex - indexMin;
  }
  
  public boolean userIndexIsValid(int userIndex)
  {
    return getIndexMin() <= userIndex && userIndex <= getIndexMax();
  }

@Override
public Iterator<T> iterator() {
	// TODO Auto-generated method stub
	return null;
}
}
