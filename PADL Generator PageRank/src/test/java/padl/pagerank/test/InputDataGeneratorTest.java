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

import java.util.List;
import junit.framework.TestCase;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFactory;
import padl.kernel.IInterface;
import padl.kernel.impl.Factory;
import padl.pagerank.utils.InputDataGeneratorWith3Relations;
import padl.pagerank.utils.InputDataGeneratorWith9Relations;

/**
 * Tests the generators of ClassRank/PageRank input data on a small, hand-built model: interface I,
 * class A implements I, class B extends A.
 */
public class InputDataGeneratorTest extends TestCase {
  private ICodeLevelModel model;

  public InputDataGeneratorTest(final String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();

    final IFactory factory = Factory.getInstance();
    this.model = factory.createCodeLevelModel("Model");

    final IInterface i = factory.createInterface("I".toCharArray(), "I".toCharArray());
    final IClass a = factory.createClass("A".toCharArray(), "A".toCharArray());
    final IClass b = factory.createClass("B".toCharArray(), "B".toCharArray());
    a.addImplementedInterface(i);
    b.addInheritedEntity(a);

    this.model.addConstituent(i);
    this.model.addConstituent(a);
    this.model.addConstituent(b);
  }

  public void testWith3RelationsName() {
    assertEquals("ClassRank", new InputDataGeneratorWith3Relations().getName());
  }

  public void testWith3RelationsEntities() {
    final InputDataGeneratorWith3Relations generator = new InputDataGeneratorWith3Relations();
    this.model.generate(generator);

    final String code = generator.getCode();
    assertTrue(code, code.contains("c,0,"));
    assertTrue(code, code.contains("c,1,"));
    assertTrue(code, code.contains("c,2,"));
    assertFalse(code, code.contains("c,3,"));
  }

  public void testWith3RelationsInheritance() {
    final InputDataGeneratorWith3Relations generator = new InputDataGeneratorWith3Relations();
    this.model.generate(generator);

    // Inheritance relations have label 3: A -> I and B -> A.
    int numberOfInheritances = 0;
    for (final String line : generator.getCode().split("\n")) {
      if (line.startsWith("r,") && line.endsWith(",3")) {
        numberOfInheritances++;
      }
    }
    assertEquals(2, numberOfInheritances);
  }

  public void testWith3RelationsReset() {
    final InputDataGeneratorWith3Relations generator = new InputDataGeneratorWith3Relations();
    this.model.generate(generator);
    assertTrue(generator.getCode().length() > 0);

    generator.reset();
    assertEquals("", generator.getCode());
  }

  public void testWith3RelationsGenerateTwiceAfterReset() {
    final InputDataGeneratorWith3Relations generator = new InputDataGeneratorWith3Relations();
    this.model.generate(generator);
    final String firstCode = generator.getCode();

    generator.reset();
    this.model.generate(generator);

    assertEquals(firstCode, generator.getCode());
  }

  public void testWith9RelationsName() {
    assertNotNull(new InputDataGeneratorWith9Relations(false, false).getName());
  }

  public void testWith9RelationsInheritances() {
    final InputDataGeneratorWith9Relations generator =
        new InputDataGeneratorWith9Relations(false, false);
    this.model.generate(generator);

    final List inheritances = generator.getRelationsType3Inheritances();
    assertEquals(2, inheritances.size());
  }

  public void testWith9RelationsOtherRelationsAreEmpty() {
    final InputDataGeneratorWith9Relations generator =
        new InputDataGeneratorWith9Relations(false, false);
    this.model.generate(generator);

    assertEquals(0, generator.getRelationsType1Associations().size());
    assertEquals(0, generator.getRelationsType1Creations().size());
    assertEquals(0, generator.getRelationsType1Uses().size());
    assertEquals(0, generator.getRelationsType2Aggregations().size());
    assertEquals(0, generator.getRelationsType2Compositions().size());
    assertEquals(0, generator.getRelationsType4CalledMethods().size());
    assertEquals(0, generator.getRelationsType5FieldAccesses().size());
    assertEquals(0, generator.getRelationsType6TypesOfFields().size());
  }

  public void testWith9RelationsHeaderStartsOutput() {
    final InputDataGeneratorWith9Relations generator =
        new InputDataGeneratorWith9Relations(false, false);
    this.model.generate(generator);

    final String firstLine = generator.getCode().split("\n")[0];
    assertTrue(firstLine, firstLine.matches("\\d+,\\d+"));
  }

  public void testWith9RelationsReset() {
    final InputDataGeneratorWith9Relations generator =
        new InputDataGeneratorWith9Relations(false, false);
    this.model.generate(generator);
    generator.reset();

    assertEquals("", generator.getCode());
    assertEquals(0, generator.getRelationsType3Inheritances().size());
  }
}
