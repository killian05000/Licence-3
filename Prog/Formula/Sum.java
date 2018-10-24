public class Sum implements Formula
{
  private Formula left, right;
  private Formula[] operands;
  //

  public Sum(Formula[] operands)
  {

  }


  public Sum(Formula _left, Formla _right)
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
    return "("+left.asString()+"+"+right.asString()+") = "+""
  }

}
