public class Vector
{
  private int[] array;
  private int size;

  public static void main()
  {
    //Vector v = new Vector(10);
  }

  public Vector (int capacity)
  {
    this.size = 0;
    this.array = new int[capacity];
  }

  public Vector ()
  {
    this(10);
  }

  public int getSize()
  {
    return size;
  }

  public int getCapacity()
  {
    return array.length();
  }

  public boolean isEmpty() // Une methode qui verifie une variable est appellée un predicat
  {
    return size==0;
  }

  public boolean indexIsValid(int index)
  {
    return (0 <= index && index < size);
  }

  public void replaceElement(int index, int value)
  {
    if (!indexIsValid(index))
      return;

    array[index] = value;
  }

  public void resize(int _size)
  {
    // je sais pas quoi
  }

  public ensureCapacity(int wantedCapacity)
  {
    if(wantedCapacity < getCapacity())
      return ;

    int new Capacity = Math.max(2*getCapacity(), wantedCapacity);
    int[] new Array = new int[new Capacity];
    System.arraycopy(array, 0, new Array, 0, size);
    array = new Array;
  }

  public int getElement( int index)
  {
    if(!indexIsValid)
      return int.min();

    return array[index];
  }

  public addElement(int value)
  {
    ensureCapacity(size+1);
    array[size] = value;
    size++;
  }

}
