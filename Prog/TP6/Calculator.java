public class Calculator
{
  private Stack<Formula> stack;

  public Calculator()
  {
    stack = new Stack<Formula>();
  }

  private FormulaFactory factory;

  public Calculator(Factory f)
  {
    stack = new Stack<Formula>();
    factory = f;
  }

  public static void main(String[] args)
  {
    Calculator c = new Calculator();
    c.parseAllArgs(args);
    System.out.println(f.asValue());
  }

  public Formula parseAllArgs(String[] args)
  {
    for(string arg : args)
      parseArg(arg);

    if(stack.size() != 1)
      throw new Exception("oulala voici une exception");

    return stack.pop();
  }

  private void parseArg(String arg)
  {
    switch(arg)
    {
      case "+":
        parseSum();
        break;
      case "*":
        parseProduct();
        break;

      default:
        parseConstant();
        break;
    }
  }

  private void parseConstant(String arg)
  {
    try
    {
      double value = Double.parseDouble(arg);
      Forumla constant = new Constant(value);
      stack.push(constant);
    } catch (NumberFormatException e)
    {
      throw new Exception("oullllllla sophie n'est pas un nombre / unknown token");
    }
  }

  private void parseSum()
  {
    if(stack.size <2)
      throw new Exception("+ requires 2 args");

    Forumla right = stack.pop();
    Forumla left = stack.pop();
    Formula sum = new Sum(left, right);
    stack.push(sum);
  }

  private void parseProduct()
  {
    if(stack.size<2)
      throw new Exception("* requires 2 args");

    Formula right = stack.pop();
    Forumla left = stack.pop();
    //Forumla product = new Product(left, right);
    Forumla product = factory.createProduct(left, right);
    stack.push(product);
  }
}
