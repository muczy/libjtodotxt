package org.libjtodotxt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TodoTxtHandler {

	private final String newLine;

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
	public TodoTxtHandler(File todoFile, File doneFile, String newLine)
			throws IOException, ParseException {
		Utils.checkForNullArgument(todoFile, "todoFile");
		Utils.checkForNullArgument(doneFile, "doneFile");
		Utils.checkForNullArgument(newLine, "newLine");

		this.todoFile = todoFile;
		this.doneFile = doneFile;
		this.newLine = newLine;

		tasks = new ArrayList<Task>();
		contexts = new ArrayList<String>();
		projects = new ArrayList<String>();

		parseTasks();
	}

	private void parseTasks() throws IOException, ParseException {
		List<String> lines = Utils.readFileContent(todoFile);

		for (String line : lines) {
			Task task = new Task(line);
			tasks.add(task);

			parseContexts(task);
			parseProjects(task);
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
		Utils.checkForNullArgument(newTask, "newTask");
		tasks.add(newTask);

		addTaskToFile(newTask, todoFile);

		parseContexts(newTask);
		parseProjects(newTask);
	}

	private void parseProjects(Task task) {
		for (String project : task.getProjects()) {
			if (!projects.contains(project)) {
				projects.add(project);
			}
		}
	}

	private void parseContexts(Task task) {
		for (String context : task.getContexts()) {
			if (!contexts.contains(context)) {
				contexts.add(context);
			}
		}
	}

	private void addTaskToFile(Task task, File file) throws IOException {
		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(task.getLine());
			writer.write(newLine);
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
	 * @param taskToRemove
	 *            the task be to removed
	 * @throws IOException
	 *             if the removal of task from the file failed
	 */
	public void removeTask(Task taskToRemove) throws IOException {
		Utils.checkForNullArgument(taskToRemove, "task");
		checkForExistingTask(taskToRemove);

		tasks.remove(taskToRemove);

		removeTaskFromFile(taskToRemove, todoFile);
	}

	private void checkForExistingTask(Task task) {
		if (!tasks.contains(task)) {
			throw new IllegalArgumentException("Task \"" + task.getLine()
					+ "\" does not exist!");
		}
	}

	private void removeTaskFromFile(Task task, File file) throws IOException {
		BufferedWriter writer = null;

		List<String> lines = Utils.readFileContent(file);

		try {
			writer = new BufferedWriter(new FileWriter(file, false));

			for (String line : lines) {
				if (!line.trim().equals(task.getLine())) {
					writer.write(task.getLine());
					writer.write(newLine);
				}
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	/**
	 * Archives the specified task by moving it from the todo.txt to the
	 * done.txt file .
	 * 
	 * @param taskToArchive
	 *            the task to be archived
	 * @throws IOException
	 *             if the moving of the task failed
	 */
	public void archiveTask(Task taskToArchive) throws IOException {
		Utils.checkForNullArgument(taskToArchive, "task");
		checkForExistingTask(taskToArchive);

		tasks.remove(taskToArchive);

		removeTaskFromFile(taskToArchive, todoFile);
		addTaskToFile(taskToArchive, doneFile);
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
	public List<Task> getTasksForProject(String project) {
		List<Task> foundTasks = new LinkedList<Task>();

		for (Task task : tasks) {
			if (task.getProjects().contains(project)) {
				foundTasks.add(task);
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
	public List<Task> getTasksForContext(String context) {
		List<Task> foundTasks = new LinkedList<Task>();

		for (Task task : tasks) {
			if (task.getContexts().contains(context)) {
				foundTasks.add(task);
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
