public class Product implements Formula
{
  private Formula left, right;

  public Product(Formula _left, Formla _right)
  {
    this.left = _left;
    this.right = _right;
  }

  @Override
  public double asValue()
  {
    return left.asValue()+right.asValue();

  }

  @Override
  public String asString()
  {
    return "("+left.asString()+"*"+right.asString()+") = "+""
  }

}
