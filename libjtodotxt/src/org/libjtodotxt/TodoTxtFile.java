package org.libjtodotxt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
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
		final BufferedReader contentReader = new BufferedReader(
				new StringReader(todoTxtFileContent));

		String line = null;
		while ((line = contentReader.readLine()) != null) {
			addTask(line);
		}

		contentReader.close();
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

	public void removeTask(final String line) {
		for (final Task task : tasks) {
			if (task.getLine().equals(line)) {
				tasks.remove(task);
				break;
			}
		}
	}

	public List<String> getProjects() {
		return projects;
	}

	public List<String> getContexts() {
		return contexts;
	}

	public List<String> getTasksForProject(final String project) {
		final List<String> foundTasks = new LinkedList<String>();

		for (final Task task : tasks) {
			if (task.getProjects().contains(project)) {
				foundTasks.add(task.getLine());
			}
		}

		return foundTasks;
	}

	public List<String> getTasksForContext(final String context) {
		final List<String> foundTasks = new LinkedList<String>();

		for (final Task task : tasks) {
			if (task.getContexts().contains(context)) {
				foundTasks.add(task.getLine());
			}
		}

		return foundTasks;
	}

	public List<Task> getTasks() {
		final List<Task> clone = new LinkedList<Task>();

		for (final Task task : tasks) {
			try {
				clone.add(new Task(task.getLine()));
			} catch (final ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return clone;
	}
}
