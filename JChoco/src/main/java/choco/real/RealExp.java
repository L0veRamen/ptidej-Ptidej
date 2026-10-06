package choco.real;

import choco.ContradictionException;
import java.util.List;
import java.util.Set;

/** An interface for real expressions. */
public interface RealExp extends RealInterval {
  /**
   * Collects recursively all the variable this expression depends on.
   *
   * @return
   */
  public Set<RealVar> collectVars(Set<RealVar> s);

  /**
   * Isolates sub terms depending or not on a variable x.
   *
   * @param var
   * @param wx
   * @param wox
   * @return
   */
  public boolean isolate(RealVar var, List<RealExp> wx, List<RealExp> wox);

  /**
   * Projects computed bounds to the sub expressions.
   *
   * @throws ContradictionException
   */
  public void project() throws ContradictionException;

  /**
   * Computes recursively the sub expressions (avoids to tighten and project recursively).
   *
   * @return
   */
  public List<RealExp> subExps(List<RealExp> l);

  /** Computes the narrowest bounds with respect to sub terms. */
  public void tighten();
}
