package choco.real.exp;

import choco.Problem;
import choco.real.RealExp;
import choco.real.RealVar;
import java.util.List;
import java.util.Set;

/** A binary real expression. */
public abstract class AbstractRealBinTerm extends AbstractRealCompoundTerm {
  protected RealExp exp1, exp2;

  public AbstractRealBinTerm(final Problem pb, final RealExp exp1, final RealExp exp2) {
    super(pb);
    this.exp1 = exp1;
    this.exp2 = exp2;
  }

  public Set<RealVar> collectVars(final Set<RealVar> s) {
    this.exp1.collectVars(s);
    this.exp2.collectVars(s);
    return s;
  }

  public boolean isolate(final RealVar var, final List<RealExp> wx, final List<RealExp> wox) {
    final boolean dependsOnX = this.exp1.isolate(var, wx, wox) | this.exp2.isolate(var, wx, wox);
    if (dependsOnX) {
      wx.add(this);
    } else {
      wox.add(this);
    }
    return dependsOnX;
  }

  public List<RealExp> subExps(final List<RealExp> l) {
    this.exp1.subExps(l);
    this.exp2.subExps(l);
    l.add(this);
    return l;
  }
}
