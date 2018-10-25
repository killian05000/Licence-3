public interface FormulaFactory
{
  Formula createConstant(double value);
  Formula createSum(double value);
  Formula createSum(Formula... operands);
  Formula createPorduct(...);
}
