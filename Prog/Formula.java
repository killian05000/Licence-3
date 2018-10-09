public interface Formula
{
  public String asString();
  public double asValue();
  Formula[] operands = {x, y, new Sum(x,y)};
  Formula f = new Sum(operands);
}
