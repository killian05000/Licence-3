import java.util.*;

public class GridIterator<T> implements Iterator<T>
{
  private int line, column;
  //private Grid<T> grid;

  public GridIterator ()


    //formel grid;
    nextLine = null;
    nextColumn = null;
  }

  @Override
  public boolean hasNext()
  {
    return nextLine < this.nbLines();
  }

  @Override public T next()
  {
    T next = this.get(nextLine, nextColumn);
    nextColumn++;
    if (nextColumn == this.nbColumn())
    {
      nextColumn = null;
      nextLine++;
    }
    return next;
  }

}
