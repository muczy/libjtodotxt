package org.libjtodotxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Utils {
	public static void checkForNullArgument(Object object, String name) {
		if (object == null) {
			throw new IllegalArgumentException(name + " is null!");
		}
	}

	public static List<String> readFileContent(File file) throws IOException {
		BufferedReader reader = null;
		List<String> result = new LinkedList<String>();

		try {
			reader = new BufferedReader(new FileReader(file));

			String line;
			while ((line = reader.readLine()) != null) {
				result.add(line);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		return result;
	}
}
