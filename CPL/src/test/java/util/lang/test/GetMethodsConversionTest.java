package util.lang.test;

import com.ibm.toad.cfparse.ClassFile;
import java.io.FileInputStream;
import java.io.IOException;
import junit.framework.TestCase;
import org.apache.bcel.classfile.ClassParser;
import org.junit.Assert;
import util.lang.CFParseBCELConvertorAdhoc;

/**
 * @since 2024/10/11
 * @author Rushin Dipak Makwana
 * @author Nicolas C. Rousse
 */
public class GetMethodsConversionTest extends TestCase {
  private ClassFile classFile_CFParse_Original;
  private ClassFile classFile_CFParse_Converted;

  public void setUp() throws IOException {
    final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

    this.classFile_CFParse_Original = new ClassFile(new FileInputStream(classFile_Path));

    this.classFile_CFParse_Converted =
        CFParseBCELConvertorAdhoc.convertClassFile(
            new ClassParser(new FileInputStream(classFile_Path), "").parse());
  }

  public void testGetAccess() {
    Assert.assertEquals(
        this.classFile_CFParse_Original.getAccess(), this.classFile_CFParse_Converted.getAccess());
  }

  public void testGetAttrs() {
    Assert.assertEquals(
        this.classFile_CFParse_Original.getAttrs().toString(),
        this.classFile_CFParse_Converted.getAttrs().toString());
  }

  public void testGetName() {
    Assert.assertEquals(
        this.classFile_CFParse_Original.getName(), this.classFile_CFParse_Converted.getName());
  }

  public void testGetFields() {
    Assert.assertEquals(
        this.classFile_CFParse_Original.getFields().get(0).toString(),
        this.classFile_CFParse_Converted.getFields().get(0).toString());
  }

  public void testGetDesc() {
    int fieldCount = this.classFile_CFParse_Original.getFields().length();
    for (int i = 0; i < fieldCount; i++) {
      Assert.assertEquals(
          this.classFile_CFParse_Original.getFields().get(i).getDesc(),
          this.classFile_CFParse_Converted.getFields().get(i).getDesc());
    }
  }

  public void testGetMethods() {

    int fieldCount = this.classFile_CFParse_Original.getMethods().length();
    for (int i = 0; i < fieldCount; i++) {
      Assert.assertEquals(
          this.classFile_CFParse_Original.getMethods().get(i).toString(),
          this.classFile_CFParse_Converted.getMethods().get(i).toString());
    }
  }
}
