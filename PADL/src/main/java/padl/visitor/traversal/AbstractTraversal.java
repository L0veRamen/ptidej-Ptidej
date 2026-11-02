package padl.visitor.traversal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import padl.kernel.IConstituent;
import padl.kernel.IContainer;
import padl.visitor.IFilter;

/**
 * Common navigation of the traversals: which children of a container are traversed. A constituent
 * rejected by the filter is skipped together with its whole subtree.
 *
 * @author Chen Qian
 */
public abstract class AbstractTraversal implements ITraversal {
  private final IFilter filter;

  protected AbstractTraversal() {
    this(aConstituent -> true);
  }

  protected AbstractTraversal(final IFilter aFilter) {
    if (aFilter == null) {
      throw new IllegalArgumentException("The filter of a traversal cannot be null");
    }
    this.filter = aFilter;
  }

  /**
   * Uses the concurrent iterator, like IAbstractModel.walk(), so that a walker may modify the model
   * while it is being traversed.
   */
  protected List<IConstituent> getChildren(final IContainer aContainer) {
    final List<IConstituent> children = new ArrayList<>();
    final Iterator iterator = aContainer.getConcurrentIteratorOnConstituents();
    while (iterator.hasNext()) {
      final IConstituent child = (IConstituent) iterator.next();
      if (this.filter.accept(child)) {
        children.add(child);
      }
    }
    return children;
  }
}
