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

	public TodoTxtFile(final String todoTxtFileContent) throws IOException,
			ParseException {
		tasks = new ArrayList<Task>();
		contexts = new ArrayList<String>();
		projects = new ArrayList<String>();

		parseTasks(todoTxtFileContent);
	}

	private void parseTasks(final String todoTxtFileContent)
			throws IOException, ParseException {
		final BufferedReader bufReader = new BufferedReader(new StringReader(
				todoTxtFileContent));

		String line = null;
		while ((line = bufReader.readLine()) != null) {
			addTask(line);
		}

		bufReader.close();
	}

	public void addTask(final String line) throws ParseException {
		final Task newTask = new Task(line);

		tasks.add(newTask);

		for (final String context : newTask.getContexts()) {
			if (!contexts.contains(context)) {
				contexts.add(context);
			}
		}

		for (final String project : newTask.getProjects()) {
			if (!projects.contains(project)) {
				projects.add(project);
			}
		}
	}

	public List<String> getProjects() {
		return projects;
	}

	public List<String> getContexts() {
		return contexts;
	}
}