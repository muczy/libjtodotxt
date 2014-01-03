package org.libjtodotxt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TodoTxtFile {

	private List<Task> tasks;
	private List<String> contexts;
	private List<String> projects;

	public TodoTxtFile(String todoTxtFileContent) throws IOException,
			ParseException {
		tasks = new ArrayList<Task>();
		contexts = new ArrayList<String>();
		projects = new ArrayList<String>();

		parseTasks(todoTxtFileContent);
	}

	private void parseTasks(String todoTxtFileContent)
			throws IOException, ParseException {
		BufferedReader contentReader = new BufferedReader(
				new StringReader(todoTxtFileContent));

		String line = null;
		while ((line = contentReader.readLine()) != null) {
			addTask(line);
		}

		contentReader.close();
	}

	public void addTask(String line) throws ParseException {
		Task newTask = new Task(line);

		tasks.add(newTask);

		for (String context : newTask.getContexts()) {
			if (!contexts.contains(context)) {
				contexts.add(context);
			}
		}

		for (String project : newTask.getProjects()) {
			if (!projects.contains(project)) {
				projects.add(project);
			}
		}
	}

	public void removeTask(String line) {
		for (Task task : tasks) {
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

	public List<String> getTasksForProject(String project) {
		List<String> foundTasks = new LinkedList<String>();

		for (Task task : tasks) {
			if (task.getProjects().contains(project)) {
				foundTasks.add(task.getLine());
			}
		}

		return foundTasks;
	}

	public List<String> getTasksForContext(String context) {
		List<String> foundTasks = new LinkedList<String>();

		for (Task task : tasks) {
			if (task.getContexts().contains(context)) {
				foundTasks.add(task.getLine());
			}
		}

		return foundTasks;
	}

	public List<Task> getTasks() {
		List<Task> clone = new LinkedList<Task>();

		for (Task task : tasks) {
			try {
				clone.add(new Task(task.getLine()));
			} catch (ParseException shouldNeverHappenException) {
				throw new RuntimeException(shouldNeverHappenException);
			}
		}

		return clone;
	}
}
