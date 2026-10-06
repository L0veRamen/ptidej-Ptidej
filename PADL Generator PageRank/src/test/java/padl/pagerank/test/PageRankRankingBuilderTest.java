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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import junit.framework.TestCase;
import padl.pagerank.PageRankRankingBuilder;
import padl.pagerank.Ranking;

public class PageRankRankingBuilderTest extends TestCase {
  private static final String HEADER = "Count;Name;PageRank;Rank;AllEdges;Incoming;Outgoing\n";

  private File file;

  public PageRankRankingBuilderTest(final String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
    this.file = File.createTempFile("PageRankRankingBuilderTest", ".csv");
  }

  protected void tearDown() throws Exception {
    this.file.delete();
    super.tearDown();
  }

  private Ranking[] getSortedRankings(final String someContent) throws IOException {

    final FileWriter writer = new FileWriter(this.file);
    writer.write(someContent);
    writer.close();

    final FileReader reader = new FileReader(this.file);
    try {
      final Ranking[] rankings = PageRankRankingBuilder.getInstance().getRankings(reader);
      Arrays.sort(
          rankings,
          new Comparator<Ranking>() {
            public int compare(final Ranking r1, final Ranking r2) {
              return r1.getCount() - r2.getCount();
            }
          });
      return rankings;
    } finally {
      reader.close();
    }
  }

  public void testSingleton() {
    assertSame(PageRankRankingBuilder.getInstance(), PageRankRankingBuilder.getInstance());
  }

  public void testEmptyFile() throws IOException {
    assertEquals(0, this.getSortedRankings("").length);
  }

  public void testHeaderOnly() throws IOException {
    assertEquals(0, this.getSortedRankings(HEADER).length);
  }

  public void testHeaderIsSkipped() throws IOException {
    final Ranking[] rankings = this.getSortedRankings(HEADER + "0;A;0.5;1;2;1;1\n");

    assertEquals(1, rankings.length);
    assertEquals("A", rankings[0].getEntityName());
  }

  public void testSeveralRankings() throws IOException {
    final Ranking[] rankings =
        this.getSortedRankings(
            HEADER + "0;p.A;0.5;1;3;1;2\n" + "1;p.B;0.3;2;2;2;0\n" + "2;p.C;0.2;3;1;0;1\n");

    assertEquals(3, rankings.length);

    assertEquals(0, rankings[0].getCount());
    assertEquals("p.A", rankings[0].getEntityName());
    assertEquals(0.5f, rankings[0].getPageRankValue(), 0f);
    assertEquals(1, rankings[0].getRank());
    assertEquals(3, rankings[0].getNumberOfEdges());
    assertEquals(1, rankings[0].getNumberOfIncomingEdges());
    assertEquals(2, rankings[0].getNumberOfOutgoingEdges());

    assertEquals("p.B", rankings[1].getEntityName());
    assertEquals(2, rankings[1].getRank());
    assertEquals(0, rankings[1].getNumberOfOutgoingEdges());

    assertEquals("p.C", rankings[2].getEntityName());
    assertEquals(0.2f, rankings[2].getPageRankValue(), 0f);
    assertEquals(0, rankings[2].getNumberOfIncomingEdges());
  }

  public void testLastLineWithoutNewline() throws IOException {
    final Ranking[] rankings = this.getSortedRankings(HEADER + "0;A;0.5;1;2;1;1");

    assertEquals(1, rankings.length);
  }

  public void testMalformedNumber() throws IOException {
    try {
      this.getSortedRankings(HEADER + "zero;A;0.5;1;2;1;1\n");
      fail("Expected a NumberFormatException");
    } catch (final NumberFormatException e) {
      // Expected.
    }
  }

  public void testMissingField() throws IOException {
    try {
      this.getSortedRankings(HEADER + "0;A;0.5;1\n");
      fail("Expected a NoSuchElementException");
    } catch (final java.util.NoSuchElementException e) {
      // Expected.
    }
  }
}
