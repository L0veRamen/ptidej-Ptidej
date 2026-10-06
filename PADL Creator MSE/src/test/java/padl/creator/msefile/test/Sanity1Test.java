package padl.creator.msefile.test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import junit.framework.TestCase;
import padl.creator.msefile.MSECreator;
import padl.creator.msefile.misc.Element;
import util.io.ProxyDisk;

public class Sanity1Test extends TestCase {
	public void testParseMSEFile() throws IOException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {
		// ✅ Step 1: Get the same directory as this test file
		String testFilePath = ProxyDisk.getInstance().directoryTempString()
				+ "padl.creator.msefile.test.Sanity1Test.mse";
		File testFile = new File(testFilePath);
		File secondTestFile = new File(testFilePath + ".second");

		// ✅ Step 2: Ensure the parent directory exists
		testFile.getParentFile().mkdirs();

		// ✅ Step 3: Create a temporary MSE file in the same folder as the test
		try {
			Files.writeString(testFile.toPath(),
				"(FAMIX.Class (id: 1) (name 'TestClass'))",
				StandardCharsets.UTF_8);
			Files.writeString(secondTestFile.toPath(),
				"(FAMIX.Class (id: 2) (name 'SecondTestClass'))",
				StandardCharsets.UTF_8);

			final MSECreator creator = new MSECreator(
				new String[] { testFilePath, secondTestFile.getPath() });
			final Method parseMethod = MSECreator.class.getDeclaredMethod(
				"parseMSEFile");
			parseMethod.setAccessible(true);

			final Element[] elements = (Element[]) parseMethod.invoke(creator);

			assertNotNull("Parsing should return non-null elements", elements);
			assertEquals("Both MSE fixtures should be parsed", 2,
				elements.length);
		}
		finally {
			Files.deleteIfExists(testFile.toPath());
			Files.deleteIfExists(secondTestFile.toPath());
		}
	}
}
