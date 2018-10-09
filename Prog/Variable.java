public class Variable implements Formule
{
  private String name;
  private double value;

  public Variable (String _name, double _value)
  {
    this.name = name;
    this.value = value;
  }

  public voit setValue(double _value)
  {
    this.value = value;
  }

  @Override
  public String asString()
  {
    return name;
  }

  @Override
  public double asValue()
  {
    return value;
  }
}
