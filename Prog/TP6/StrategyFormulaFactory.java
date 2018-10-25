public class StrategyFormulaFactory implements FormulaFactory
{
  @Override
  public Formula createSum(Formula...operands)
  {
    return formula.strategy.Sum.create(operands);
  }
}
