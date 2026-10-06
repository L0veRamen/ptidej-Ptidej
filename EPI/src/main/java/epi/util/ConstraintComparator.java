/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
/*
 * Created on 2005-07-13
 *
 */
package epi.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * @author OlivierK
 */
public class ConstraintComparator implements Comparator<String> {

  private final ArrayList<String> constraintPriority;

  public ConstraintComparator(final ArrayList<String> myConstraintOrder) {
    this.constraintPriority = myConstraintOrder;
  }

  /**
   * Compare two string triplets representing two constraint. if (a > b) return > 0; if (a == b)
   * return 0; if (a < b) return < 0;
   */
  public final int compare(final String a, final String b) {
    return this.getConstraintPriority(a) - this.getConstraintPriority(b);
  }

  private final int getConstraintPriority(final String a) throws ClassCastException {
    final StringTokenizer st = new StringTokenizer(a);
    if (st.countTokens() == 3) {
      st.nextToken();
      final int indexOfConstraint = this.constraintPriority.indexOf(st.nextToken());
      if (indexOfConstraint != -1) {
        return indexOfConstraint;
      }
    }
    throw new ClassCastException("Invalid constraint");
  }
}
