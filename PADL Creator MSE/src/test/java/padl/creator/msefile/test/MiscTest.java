package padl.creator.msefile.test;

import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;
import padl.creator.msefile.misc.Attribute;
import padl.creator.msefile.misc.Element;
import padl.creator.msefile.misc.Package;
import padl.creator.msefile.misc.Value;

public class MiscTest extends TestCase {
  public void testValueDefaults() {
    final Value value = new Value();
    assertEquals("<no name>", value.getName());
    assertEquals("<no value>", value.getValue());

    final Value namedValue = new Value("idref");
    assertEquals("idref", namedValue.getName());
    assertEquals("<no value>", namedValue.getValue());
  }

  public void testValueSettersAndToString() {
    final Value value = new Value("primitive", "42");
    assertEquals("(primitive,42)", value.toString());

    value.setName("idref");
    value.setValue("7");
    assertEquals("idref", value.getName());
    assertEquals("7", value.getValue());
    assertEquals("(idref,7)", value.toString());
  }

  public void testAttributeWithoutValues() {
    final Attribute attribute = new Attribute("isStub");
    assertEquals("isStub", attribute.getName());
    assertEquals(0, attribute.getValues().length);
    assertNull(attribute.getValue("primitive"));
  }

  public void testAttributeGetValueByName() {
    final Value primitive = new Value("primitive", "foo");
    final Value idref = new Value("idref", "3");
    final Attribute attribute = new Attribute("belongsTo", new Value[] {primitive, idref});

    assertSame(primitive, attribute.getValue("primitive"));
    assertSame(idref, attribute.getValue("idref"));
    assertNull(attribute.getValue("path"));
  }

  public void testElementGetAttributeByName() {
    final Attribute name = new Attribute("name", new Value[] {new Value("primitive", "C")});
    final Attribute id = new Attribute("id", new Value[] {new Value("primitive", "1")});
    final Element element = new Element("FAMIX.Class", new Attribute[] {name, id});

    assertEquals("FAMIX.Class", element.getType());
    assertEquals(2, element.getAttributes().length);
    assertSame(name, element.getAttribute("name"));
    assertSame(id, element.getAttribute("id"));
    assertNull(element.getAttribute("packagedIn"));
  }

  public void testElementToString() {
    final Element element =
        new Element(
            "FAMIX.Class",
            new Attribute[] {new Attribute("id", new Value[] {new Value("primitive", "1")})});
    assertEquals("element FAMIX.Class\n\tattribute id\n\t\t(primitive,1)", element.toString());
  }

  public void testPackageNames() {
    final Package aPackage = new Package("util");
    assertEquals("util", aPackage.getSimpleName());
    assertEquals("util", aPackage.toString());
  }

  public void testPackageFullNameIsNameOfEnclosingPackages() {
    final Map<String, Package> packages = new HashMap<>();
    packages.put("1", new Package("org"));
    packages.put("2", new Package("ptidej", "1"));
    packages.put("3", new Package("padl", "2"));

    // getFullName() yields the qualified name of the enclosing
    // packages, not including the receiver's own simple name.
    assertEquals("org", ((Package) packages.get("2")).getFullName(packages));
    assertEquals("org.ptidej", ((Package) packages.get("3")).getFullName(packages));
  }
}
