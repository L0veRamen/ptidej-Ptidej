package padl.creator.msefile.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import junit.framework.TestCase;
import padl.creator.msefile.FAMIXBuilder;
import padl.creator.msefile.misc.Attribute;
import padl.creator.msefile.misc.Element;
import padl.creator.msefile.misc.Value;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.kernel.IOperation;
import padl.kernel.IParameter;
import padl.kernel.ISetter;

public class FAMIXBuilderTest extends TestCase {
  private static Attribute primitive(final String aName, final String aValue) {
    return new Attribute(aName, new Value[] {new Value("primitive", aValue)});
  }

  private static Attribute idref(final String aName, final String anID) {
    return new Attribute(aName, new Value[] {new Value("idref", anID)});
  }

  private static Element element(final String aType, final Attribute[] someAttributes) {

    return new Element(aType, someAttributes);
  }

  private static Element aPackage(final String anID, final String aName) {
    return element(
        "FAMIX.Package", new Attribute[] {primitive("id", anID), primitive("name", aName)});
  }

  private static Element aPackage(
      final String anID, final String aName, final String anEnclosingID) {

    return element(
        "FAMIX.Package",
        new Attribute[] {
          primitive("id", anID), primitive("name", aName), idref("packagedIn", anEnclosingID)
        });
  }

  private static Element aClass(
      final String anID, final String aName, final String aPackageID, final boolean isAbstract) {

    return element(
        "FAMIX.Class",
        new Attribute[] {
          primitive("id", anID),
          primitive("name", aName),
          idref("packagedIn", aPackageID),
          primitive("isAbstract", String.valueOf(isAbstract))
        });
  }

  private static Element aMethod(
      final String anID,
      final String aName,
      final String anOwnerID,
      final String anAccess,
      final String aKind,
      final String aSignature) {

    final List<Attribute> attributes = new ArrayList<>();
    attributes.add(primitive("id", anID));
    attributes.add(primitive("name", aName));
    attributes.add(idref("belongsTo", anOwnerID));
    attributes.add(primitive("accessControlQualifier", anAccess));
    attributes.add(primitive("hasClassScope", "false"));
    attributes.add(primitive("signature", aSignature));
    if (aKind != null) {
      attributes.add(primitive("kind", aKind));
    }
    return element("FAMIX.Method", (Attribute[]) attributes.toArray(new Attribute[0]));
  }

  private static Element anAttribute(
      final String anID,
      final String aName,
      final String anOwnerID,
      final String anAccess,
      final boolean isStatic) {

    return element(
        "FAMIX.Attribute",
        new Attribute[] {
          primitive("id", anID),
          primitive("name", aName),
          idref("belongsTo", anOwnerID),
          primitive("accessControlQualifier", anAccess),
          primitive("hasClassScope", String.valueOf(isStatic))
        });
  }

  // Package "1" (org) encloses package "2" (ptidej). The builder
  // qualifies an entity with the full name of its package's
  // enclosing packages, so classes in package "2" are named "org.X".
  private static final String PACKAGE_PREFIX = "org.";

  private ICodeLevelModel build(final Element[] someElements) {
    final Element[] elements = new Element[someElements.length + 2];
    elements[0] = aPackage("1", "org");
    elements[1] = aPackage("2", "ptidej", "1");
    System.arraycopy(someElements, 0, elements, 2, someElements.length);
    return new FAMIXBuilder().build(elements);
  }

  private static IFirstClassEntity entity(final ICodeLevelModel aModel, final String aSimpleName) {

    final IFirstClassEntity entity =
        (IFirstClassEntity) aModel.getConstituentFromID(PACKAGE_PREFIX + aSimpleName);
    assertNotNull("Missing entity " + aSimpleName, entity);
    return entity;
  }

  public void testEmptyModelContainsObjectGhost() {
    final ICodeLevelModel model = new FAMIXBuilder().build(new Element[0]);
    assertNotNull(model);
    assertEquals(1, model.getNumberOfConstituents());
    assertTrue(model.getConstituentFromID("java.lang.Object") instanceof IGhost);
  }

  public void testPackagesAloneAddNoEntities() {
    final ICodeLevelModel model = this.build(new Element[0]);
    assertEquals(1, model.getNumberOfConstituents());
  }

  public void testClass() {
    final ICodeLevelModel model = this.build(new Element[] {aClass("10", "Foo", "2", false)});
    assertEquals(2, model.getNumberOfConstituents());

    final IFirstClassEntity foo = entity(model, "Foo");
    assertTrue(foo instanceof IClass);
    assertEquals("Foo", foo.getDisplayName());
    assertFalse(foo.isAbstract());
  }

  public void testAbstractElementBecomesInterface() {
    // FAMIXBuilder reads "isAbstract" for interfaceness,
    // so abstract FAMIX classes are built as interfaces.
    final ICodeLevelModel model = this.build(new Element[] {aClass("10", "Bar", "2", true)});
    assertTrue(entity(model, "Bar") instanceof IInterface);
  }

  public void testMethodsAccessAndKind() {
    final ICodeLevelModel model =
        this.build(
            new Element[] {
              aClass("10", "Foo", "2", false),
              aMethod("20", "run", "10", "public", null, "run()"),
              aMethod("21", "help", "10", "protected", null, "help()"),
              aMethod("22", "hide", "10", "private", null, "hide()"),
              aMethod("23", "size", "10", "public", "getter", "size"),
              aMethod("24", "size", "10", "public", "getter", "size:")
            });
    final IFirstClassEntity foo = entity(model, "Foo");
    assertEquals(5, foo.getNumberOfConstituents());

    // Method names are suffixed with their MSE id to keep them unique.
    final IOperation run = (IOperation) foo.getConstituentFromID("run20");
    assertTrue(run instanceof IMethod);
    assertTrue(run.isPublic());

    assertTrue(((IOperation) foo.getConstituentFromID("help21")).isProtected());
    assertTrue(((IOperation) foo.getConstituentFromID("hide22")).isPrivate());
    assertTrue(foo.getConstituentFromID("size23") instanceof IGetter);
    assertTrue(foo.getConstituentFromID("size24:") instanceof ISetter);
  }

  public void testFormalParameter() {
    final ICodeLevelModel model =
        this.build(
            new Element[] {
              aClass("10", "Foo", "2", false),
              aMethod("20", "run", "10", "public", null, "run(x)"),
              element(
                  "FAMIX.FormalParameter",
                  new Attribute[] {
                    primitive("id", "30"), primitive("name", "x"), idref("belongsTo", "20")
                  })
            });
    final IOperation run = (IOperation) entity(model, "Foo").getConstituentFromID("run20");
    assertEquals(1, run.getNumberOfConstituents());
    assertTrue(run.getConstituentFromName("x") instanceof IParameter);
  }

  public void testFields() {
    final ICodeLevelModel model =
        this.build(
            new Element[] {
              aClass("10", "Foo", "2", false),
              anAttribute("40", "count", "10", "private", true),
              anAttribute("41", "name", "10", "public", false)
            });
    final IFirstClassEntity foo = entity(model, "Foo");
    assertEquals(2, foo.getNumberOfConstituents());

    final IField count = (IField) foo.getConstituentFromID("count40");
    assertTrue(count.isPrivate());
    assertTrue(count.isStatic());

    final IField name = (IField) foo.getConstituentFromID("name41");
    assertTrue(name.isPublic());
    assertFalse(name.isStatic());
  }

  public void testInheritance() {
    final ICodeLevelModel model =
        this.build(
            new Element[] {
              aClass("10", "Parent", "2", false),
              aClass("11", "Child", "2", false),
              element(
                  "FAMIX.InheritanceDefinition",
                  new Attribute[] {
                    primitive("id", "50"), idref("subclass", "11"), idref("superclass", "10")
                  })
            });
    final Iterator inheritedEntities = entity(model, "Child").getIteratorOnInheritedEntities();
    assertTrue(inheritedEntities.hasNext());
    assertSame(entity(model, "Parent"), inheritedEntities.next());
    assertFalse(inheritedEntities.hasNext());
  }
}
