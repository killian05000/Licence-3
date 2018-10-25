public class TemplateFormulaFactory implements Formula FormulaFactory
{
  public Formula create Sum(Forumla... operands)
  {
    return new Forumla.template.Sum(operands);
  }
}
