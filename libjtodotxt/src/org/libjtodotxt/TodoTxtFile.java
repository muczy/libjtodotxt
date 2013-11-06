package org.libjtodotxt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class TodoTxtFile {

	private final List<Task> tasks;
	private final List<String> contexts;
	private final List<String> projects;

	public TodoTxtFile(String todoTxtFileContent) throws IOException,
			ParseException {
		tasks = new ArrayList<Task>();
		contexts = new ArrayList<String>();
		projects = new ArrayList<String>();

		parseTasks(todoTxtFileContent);
	}

	private void parseTasks(String todoTxtFileContent) throws IOException,
			ParseException {
		final BufferedReader bufReader = new BufferedReader(new StringReader(
				todoTxtFileContent));

		String line = null;
		while ((line = bufReader.readLine()) != null) {
			tasks.add(new Task(line));
		}

		bufReader.close();
	}

	public void addTask(String line) throws ParseException {
		tasks.add(new Task(line));
	}
}
