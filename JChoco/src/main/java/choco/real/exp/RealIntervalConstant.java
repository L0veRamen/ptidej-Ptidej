package choco.real.exp;

import choco.ContradictionException;
import choco.real.RealExp;
import choco.real.RealInterval;
import choco.real.RealVar;
import java.util.List;
import java.util.Set;

/** A constant real interval. */
public class RealIntervalConstant implements RealExp {
  protected final double inf;
  protected final double sup;

  public RealIntervalConstant(final double inf, final double sup) {
    this.inf = inf;
    this.sup = sup;
    // this.problem = pb;
  }

  public Set<RealVar> collectVars(final Set<RealVar> s) {
    return s;
  }

  public double getInf() {
    return this.inf;
  }

  public double getSup() {
    return this.sup;
  }

  public void intersect(final RealInterval interval) throws ContradictionException {}

  public boolean isolate(final RealVar var, final List<RealExp> wx, final List<RealExp> wox) {
    return false;
  }

  public String pretty() {
    return this.toString();
  }

  public void project() {}

  public List<RealExp> subExps(final List<RealExp> l) {
    l.add(this);
    return l;
  }

  public void tighten() {}

  public String toString() {
    return "[" + this.inf + "," + this.sup + "]";
  }
}
