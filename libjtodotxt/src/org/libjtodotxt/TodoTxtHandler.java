package org.libjtodotxt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TodoTxtHandler {

	private File todoFile;
	private File doneFile;

	private List<Task> tasks;
	private List<String> contexts;
	private List<String> projects;

	/**
	 * Constructs a TodoTxtFile initialized to the contents of the specified
	 * string.
	 * 
	 * @param todoFile
	 *            the todo.txt file.
	 * @param doneFile
	 *            the done.txt file.
	 * @throws IOException
	 *             if the reading of the string failed
	 * @throws ParseException
	 *             if the parsing of the string failed
	 */
	public TodoTxtHandler(File todoFile, File doneFile) throws IOException,
			ParseException {
		this.todoFile = todoFile;
		this.doneFile = doneFile;

		tasks = new ArrayList<Task>();
		contexts = new ArrayList<String>();
		projects = new ArrayList<String>();

		parseTasks();
	}

	private void parseTasks() throws IOException, ParseException {
		BufferedReader contentReader = null;

		try {
			contentReader = new BufferedReader(new FileReader(todoFile));

			String line = null;
			while ((line = contentReader.readLine()) != null) {
				addTask(new Task(line));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (contentReader != null) {
				contentReader.close();
			}
		}

	}

	/**
	 * Adds the specified task to the todo.txt file.
	 * 
	 * @param newTask
	 *            the task to be added.
	 * @throws IOException
	 *             if writing the task to file failed
	 */
	public void addTask(Task newTask) throws IOException {
		tasks.add(newTask);

		addTaskToFile(newTask, todoFile);

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

	private void addTaskToFile(Task task, File file) throws IOException {
		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(task.getLine());
		} catch (IOException e) {
			throw e;
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	/**
	 * Removes the specified task from the todo.txt file.
	 * 
	 * @param task
	 *            the task be to removed
	 */
	public void removeTask(Task task) {
		tasks.remove(task);
	}

	/**
	 * Returns the list of projects used by tasks in the todo.txt file.
	 * 
	 * @return the list of projects
	 */
	public List<String> getProjects() {
		return projects;
	}

	/**
	 * Returns the list of contexts used by tasks in the todo.txt file.
	 * 
	 * @return the list of contexts
	 */
	public List<String> getContexts() {
		return contexts;
	}

	/**
	 * Returns the list of tasks which have the specified project.
	 * 
	 * @param project
	 *            the project to get tasks for
	 * @return the list of tasks
	 */
	public List<String> getTasksForProject(String project) {
		List<String> foundTasks = new LinkedList<String>();

		for (Task task : tasks) {
			if (task.getProjects().contains(project)) {
				foundTasks.add(task.getLine());
			}
		}

		return foundTasks;
	}

	/**
	 * Returns the list of tasks which have the specified context.
	 * 
	 * @param context
	 *            the context to get tasks for
	 * @return the list of tasks
	 */
	public List<String> getTasksForContext(String context) {
		List<String> foundTasks = new LinkedList<String>();

		for (Task task : tasks) {
			if (task.getContexts().contains(context)) {
				foundTasks.add(task.getLine());
			}
		}

		return foundTasks;
	}

	/**
	 * Returns the list of tasks in the todo.txt file.
	 * 
	 * @return the list of tasks
	 */
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
