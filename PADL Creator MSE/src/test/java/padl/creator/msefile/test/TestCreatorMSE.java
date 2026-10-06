package padl.creator.msefile.test;

import junit.framework.TestSuite;

public class TestCreatorMSE extends TestSuite {
  public static TestSuite suite() {
    final TestCreatorMSE suite = new TestCreatorMSE();
    suite.setName(TestCreatorMSE.class.getName());

    suite.addTestSuite(Sanity1Test.class);
    suite.addTestSuite(Sanity2Test.class);
    suite.addTestSuite(MiscTest.class);
    suite.addTestSuite(MSEParserTest.class);
    suite.addTestSuite(FAMIXBuilderTest.class);
    suite.addTestSuite(MSECreatorTest.class);

    return suite;
  }
}
