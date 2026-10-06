package padl.creator.msefile.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import junit.framework.TestCase;
import padl.creator.msefile.MSECreator;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IOperation;
import padl.kernel.impl.Factory;
import util.io.ProxyDisk;

public class MSECreatorTest extends TestCase {
  private static String writeMSEFile(final String aName, final String someMSE) throws IOException {

    final String path =
        ProxyDisk.getInstance().directoryTempString()
            + "padl.creator.msefile.test.MSECreatorTest."
            + aName
            + ".mse";
    final File file = new File(path);
    file.getParentFile().mkdirs();
    final FileWriter writer = new FileWriter(file);
    writer.write(someMSE);
    writer.close();
    return path;
  }

  private static ICodeLevelModel create(final String aPath) {
    final ICodeLevelModel model = Factory.getInstance().createCodeLevelModel("MSECreatorTest");
    new MSECreator(new String[] {aPath}).create(model);
    return model;
  }

  public void testCreateFromMultiElementFile() throws IOException {
    final String path =
        writeMSEFile(
            "multi",
            "(\n"
                + "\t(FAMIX.Package (id: 1) (name 'org'))\n"
                + "\t(FAMIX.Package (id: 2) (name 'ptidej') (packagedIn (idref: 1)))\n"
                + "\t(FAMIX.Class (id: 10) (name 'Foo') (packagedIn (idref: 2)) (isAbstract false))\n"
                + "\t(FAMIX.Method (id: 20) (name 'run') (belongsTo (idref: 10))\n"
                + "\t\t(accessControlQualifier 'public') (hasClassScope false) (signature 'run()'))\n"
                + ")\n");
    final ICodeLevelModel model = create(path);

    assertEquals(2, model.getNumberOfConstituents());
    assertTrue(model.getConstituentFromID("java.lang.Object") instanceof IGhost);

    final IFirstClassEntity foo = (IFirstClassEntity) model.getConstituentFromID("org.Foo");
    assertTrue(foo instanceof IClass);
    assertEquals("Foo", foo.getDisplayName());

    final IOperation run = (IOperation) foo.getConstituentFromID("run20");
    assertNotNull(run);
    assertTrue(run.isPublic());
  }

  public void testDoubledSingleQuotesAreUnescaped() throws IOException {
    // MSE escapes a quote inside a string by doubling it; MSECreator
    // turns '' into " before parsing, so it does not end the string.
    final String path =
        writeMSEFile(
            "quotes",
            "(\n"
                + "\t(FAMIX.Package (id: 1) (name 'org'))\n"
                + "\t(FAMIX.Package (id: 2) (name 'ptidej') (packagedIn (idref: 1)))\n"
                + "\t(FAMIX.Class (id: 10) (name 'It''s') (packagedIn (idref: 2)) (isAbstract false))\n"
                + ")\n");
    final ICodeLevelModel model = create(path);

    final IFirstClassEntity entity = (IFirstClassEntity) model.getConstituentFromID("org.It\"s");
    assertNotNull(entity);
    assertEquals("It\"s", entity.getDisplayName());
  }
}
