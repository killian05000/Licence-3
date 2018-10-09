import java.util.*;

public interface Iterator<T>
{
  public boolean hasNext(); // Renvoie true si a une case après
  public T next(); // passe a la case suivante
}
