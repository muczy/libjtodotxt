package org.libjtodotxt;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class UtilsTest {
	
	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();

	@Test(expected = IllegalArgumentException.class)
	public void checkForNullArgument_NullArg() {
		Utils.checkForNullArgument(null, "testNullArg");
	}
	
	@Test
	public void checkForNullArgument_NonNullArg() {
		Utils.checkForNullArgument("", "testNullArg");
	}

	@Test
	public void readFileContent_EmptyFile() throws IOException {
		File testFile = tmpFolder.newFile();
		
		List<String> result = Utils.readFileContent(testFile);
		
		assertEquals(new LinkedList<String>(), result);
	}
	
	@Test(expected = IOException.class)
	public void readFileContent_NonExistentFile() throws IOException {
		File testFile = tmpFolder.newFile();
		testFile.delete();
		
		List<String> result = Utils.readFileContent(testFile);
		
		assertEquals(new LinkedList<String>(), result);
	}

}
