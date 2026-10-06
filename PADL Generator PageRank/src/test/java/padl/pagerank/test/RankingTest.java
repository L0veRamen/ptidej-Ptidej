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
package padl.pagerank.test;

import junit.framework.TestCase;
import padl.pagerank.Ranking;

public class RankingTest extends TestCase {
  public RankingTest(final String name) {
    super(name);
  }

  public void testGetters() {
    final Ranking ranking = new Ranking(1, "p.A", 0.25f, 3, 5, 2, 3);

    assertEquals(1, ranking.getCount());
    assertEquals("p.A", ranking.getEntityName());
    assertEquals(0.25f, ranking.getPageRankValue(), 0f);
    assertEquals(3, ranking.getRank());
    assertEquals(5, ranking.getNumberOfEdges());
    assertEquals(2, ranking.getNumberOfIncomingEdges());
    assertEquals(3, ranking.getNumberOfOutgoingEdges());
  }

  public void testEdgesAreIndependentValues() {
    final Ranking ranking = new Ranking(0, "B", 0f, 0, 7, 4, 3);

    assertEquals(
        ranking.getNumberOfIncomingEdges() + ranking.getNumberOfOutgoingEdges(),
        ranking.getNumberOfEdges());
  }
}
