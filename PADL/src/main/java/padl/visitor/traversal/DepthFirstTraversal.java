package padl.visitor.traversal;

import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IContainer;
import padl.visitor.IFilter;
import padl.visitor.IVisitor;

/**
 * Depth-first traversal: a container is opened, its children are traversed, then it is closed;
 * a leaf is visited. Without a filter, it calls the visitor in exactly the same order as
 * IAbstractModel.walk() but without relying on the accept() methods of the constituents.
 *
 * @author Chen Qian
 */
public class DepthFirstTraversal extends AbstractTraversal {
  public DepthFirstTraversal() {
    super();
  }

  public DepthFirstTraversal(final IFilter aFilter) {
    super(aFilter);
  }

  public void traverse(final IAbstractModel anAbstractModel, final IVisitor aVisitor) {
    aVisitor.open(anAbstractModel);
    for (final IConstituent child : this.getChildren(anAbstractModel)) {
      this.traverse(child, aVisitor);
    }
    aVisitor.close(anAbstractModel);
  }

  private void traverse(final IConstituent aConstituent, final IVisitor aVisitor) {
    if (ConstituentDispatcher.isContainer(aConstituent)) {
      ConstituentDispatcher.open(aConstituent, aVisitor);
      for (final IConstituent child : this.getChildren((IContainer) aConstituent)) {
        this.traverse(child, aVisitor);
      }
      ConstituentDispatcher.close(aConstituent, aVisitor);
    } else {
      ConstituentDispatcher.visit(aConstituent, aVisitor);
    }
  }
}
