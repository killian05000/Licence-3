import java.util.*;

public class Grid<T> implements Iterable<T>
{
  private T[][] elements;


  public Grid(T[][] elements) {this.elements=elements;}
  public T get(int line, int column) {return elements[line][column];}
  public int nbLines() {return elements.length;}
  public int nbColumns() { return elements[0]. length;}
  public Iterator<T> iterator() {return new GridIterator();}
  

}
