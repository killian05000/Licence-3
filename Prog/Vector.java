import java.util.*;

public class Vector
{
  private int[] array;
  private int size;

  public static void main()
  {
    Scanner sc = new Scanner(System.in);

    System.out.print("Pick the chosen capacity of the vector :");
    int capacity = sc.nextInt();
    Vector v = new Vector(capacity);

    System.out.println("Enter the values(q to quit)");
    for (int i=0 ; i<v.getCapacity() ; i++)
    {
      System.out.print("Enter the value for vector["+i+"] : ");
      double value = sc.nextDouble();
      if (value == 00)
        return;
      v.addElement(value);
    }

    System.out.println("Displaying of the vector :");
    for (int i=0 ; i<v.getCapacity() ; i++)
    {
      System.out.println("vector ["+i+"] = "+v.getElement(i));
    }

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
²
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

  public void ensureCapacity(int minCapacity)
  {
    // int oldCapacity = ellang.NumberElments.length;
    // if (oldCapacity >= minCapacity) return;
    // int newCapacity = Math.max(oldCapacity * 2, minCapacity);
    // elements = Arrays.copyOf(elements, newCapacity);
}

  public int getElement( int index)
  {
    if(!indexIsValid)
      return -999999999;

    return array[index];
  }

  public void addElement(int value)
  {
    ensureCapacity(size+1);
    array[size] = value;
    size++;
  }

}
