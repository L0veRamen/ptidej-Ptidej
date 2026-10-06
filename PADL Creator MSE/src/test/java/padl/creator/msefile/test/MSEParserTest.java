package padl.creator.msefile.test;

import java.io.StringReader;
import junit.framework.TestCase;
import padl.creator.msefile.MSELexer;
import padl.creator.msefile.MSEParser;
import padl.creator.msefile.misc.Attribute;
import padl.creator.msefile.misc.Element;

public class MSEParserTest extends TestCase {
  private static Element[] parse(final String someMSE) throws Exception {
    final MSEParser parser = new MSEParser(new MSELexer(new StringReader(someMSE)));
    final Element[] elements = (Element[]) parser.parse().value;
    assertNotNull(elements);
    return elements;
  }

  private static Element parseSingleElement(final String someMSE) throws Exception {

    final Element[] elements = parse(someMSE);
    assertEquals(1, elements.length);
    return elements[0];
  }

  private static String valueOf(
      final Element anElement, final String anAttributeName, final String aValueName) {

    final Attribute attribute = anElement.getAttribute(anAttributeName);
    assertNotNull("Missing attribute " + anAttributeName, attribute);
    return attribute.getValue(aValueName).getValue();
  }

  public void testElementWithOnlyID() throws Exception {
    final Element element = parseSingleElement("(FAMIX.Class (id: 5))");
    assertEquals("FAMIX.Class", element.getType());
    assertEquals(1, element.getAttributes().length);
    assertEquals("5", valueOf(element, "id", "primitive"));
  }

  public void testIDIsAddedAsLastAttribute() throws Exception {
    final Element element =
        parseSingleElement("(FAMIX.Class (id: 2) (name Foo) (isAbstract false))");
    final Attribute[] attributes = element.getAttributes();
    assertEquals(3, attributes.length);
    assertEquals("name", attributes[0].getName());
    assertEquals("isAbstract", attributes[1].getName());
    assertEquals("id", attributes[2].getName());
  }

  public void testPrimitiveValues() throws Exception {
    final Element element =
        parseSingleElement(
            "(FAMIX.Method (id: 4) (name foo) (count 12) (isPublic true) (isStub false) (weight -1.5))");
    assertEquals("FAMIX.Method", element.getType());
    assertEquals("foo", valueOf(element, "name", "primitive"));
    assertEquals("12", valueOf(element, "count", "primitive"));
    assertEquals("true", valueOf(element, "isPublic", "primitive"));
    assertEquals("false", valueOf(element, "isStub", "primitive"));
    assertEquals("-1.5", valueOf(element, "weight", "primitive"));
  }

  public void testDottedName() throws Exception {
    final Element element = parseSingleElement("(FAMIX.Class (id: 1) (name java.lang.String))");
    assertEquals("java.lang.String", valueOf(element, "name", "primitive"));
  }

  public void testReferenceValue() throws Exception {
    final Element element = parseSingleElement("(FAMIX.Class (id: 2) (packagedIn (idref: 3)))");
    assertEquals("3", valueOf(element, "packagedIn", "idref"));
  }

  public void testPrimitiveCommandValue() throws Exception {
    final Element element =
        parseSingleElement("(FAMIX.Attribute (id: 1) (declaredType (primitive: String)))");
    assertEquals("String", valueOf(element, "declaredType", "primitiveCommand"));
  }

  public void testPathCommandValue() throws Exception {
    final Element element = parseSingleElement("(FAMIX.Class (id: 1) (location (path: a.b.C)))");
    assertEquals("a.b.C", valueOf(element, "location", "path"));
  }

  public void testMultipleValuesInOneAttribute() throws Exception {
    final Element element =
        parseSingleElement("(FAMIX.Invocation (id: 9) (candidates (idref: 4) foo))");
    final Attribute attribute = element.getAttribute("candidates");
    assertEquals(2, attribute.getValues().length);
    assertEquals("4", attribute.getValue("idref").getValue());
    assertEquals("foo", attribute.getValue("primitive").getValue());
  }

  public void testAttributeWithoutValue() throws Exception {
    final Element element = parseSingleElement("(FAMIX.Class (id: 1) (isStub))");
    final Attribute attribute = element.getAttribute("isStub");
    assertNotNull(attribute);
    assertEquals(0, attribute.getValues().length);
  }

  public void testCommentsAndWhiteSpaceAreIgnored() throws Exception {
    final Element element =
        parseSingleElement("# A comment line\n(FAMIX.Class\n\t(id: 6)\r\n\t(name Q))\n");
    assertEquals("6", valueOf(element, "id", "primitive"));
    assertEquals("Q", valueOf(element, "name", "primitive"));
  }

  public void testQuotedString() throws Exception {
    final Element element = parseSingleElement("(FAMIX.Class (id: 1) (name 'TestClass'))");
    assertEquals("TestClass", valueOf(element, "name", "primitive"));
  }

  public void testQuotedStringWithSpacesAndPunctuation() throws Exception {
    final Element element =
        parseSingleElement("(FAMIX.Method (id: 1) (signature 'foo(int, String):'))");
    assertEquals("foo(int, String):", valueOf(element, "signature", "primitive"));
  }

  public void testEmptyQuotedString() throws Exception {
    final Element element = parseSingleElement("(FAMIX.Class (id: 1) (name ''))");
    assertEquals("", valueOf(element, "name", "primitive"));
  }

  public void testValueCommand() throws Exception {
    final Element element =
        parseSingleElement("(FAMIX.Attribute (id: 1) (initialValue (value: 'abc')))");
    assertEquals("abc", valueOf(element, "initialValue", "value"));
  }

  public void testMultipleElementsInRootList() throws Exception {
    final Element[] elements =
        parse(
            "((FAMIX.Package (id: 1) (name 'p'))\n (FAMIX.Class (id: 2) (name 'C') (packagedIn (idref: 1)))\n (FAMIX.Method (id: 3) (name 'm')))");
    assertEquals(3, elements.length);
    assertEquals("FAMIX.Package", elements[0].getType());
    assertEquals("FAMIX.Class", elements[1].getType());
    assertEquals("FAMIX.Method", elements[2].getType());
    assertEquals("p", valueOf(elements[0], "name", "primitive"));
    assertEquals("C", valueOf(elements[1], "name", "primitive"));
    assertEquals("1", valueOf(elements[1], "packagedIn", "idref"));
    assertEquals("3", valueOf(elements[2], "id", "primitive"));
  }

  public void testSingleElementInRootList() throws Exception {
    final Element[] elements = parse("((FAMIX.Class (id: 7)))");
    assertEquals(1, elements.length);
    assertEquals("7", valueOf(elements[0], "id", "primitive"));
  }
}
