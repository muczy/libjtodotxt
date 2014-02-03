package org.libjtodotxt;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class TodoTxtHandlerTest {

	private static String LINE_SEPARATOR = System.getProperty("line.separator");

	@Rule
	public TemporaryFolder tmpFolder = new TemporaryFolder();

	private void writeTasksToFile(List<Task> tasks, File file)
			throws IOException {
		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			for (Task task : tasks) {
				writer.write(task.getLine());
				writer.write(LINE_SEPARATOR);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private boolean fileContainsTask(File file, Task testTask)
			throws IOException {
		boolean result = false;

		for (String line : Utils.readFileContent(file)) {
			if (line.equals(testTask.getLine())) {
				result = true;
				break;
			}
		}

		return result;
	}

	@Test
	public void constructor_EmptyFiles() throws IOException, ParseException {
		new TodoTxtHandler(tmpFolder.newFile(), tmpFolder.newFile(), "");
	}

	@Test
	public void constructor_TodoFileWithTasks() throws IOException,
			ParseException {
		List<Task> tasks = new LinkedList<Task>();
		tasks.add(new Task(
				"Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs"));
		tasks.add(new Task(
				"Post signs around the @phone3 neighborhood +GarageSale2"));

		File todoFile = tmpFolder.newFile();
		writeTasksToFile(tasks, todoFile);
		TodoTxtHandler handler = new TodoTxtHandler(todoFile,
				tmpFolder.newFile(), LINE_SEPARATOR);

		assertEquals(tasks, handler.getTasks());
	}

	@Test
	public void addTask() throws IOException, ParseException {
		File todoFile = tmpFolder.newFile();
		TodoTxtHandler handler = new TodoTxtHandler(todoFile,
				tmpFolder.newFile(), LINE_SEPARATOR);
		Task testTask = new Task(
				"Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");
		handler.addTask(testTask);

		assertEquals(1, handler.getTasks().size());
		assertEquals(testTask, handler.getTasks().get(0));
		assertEquals(true, fileContainsTask(todoFile, testTask));
	}

	@Test
	public void removeTask() throws IOException, ParseException {
		List<Task> tasks = new LinkedList<Task>();
		Task testTaskToRemain = new Task(
				"Post signs around the @CONTEXT neighborhood +GarageSale2");
		Task testTaskToBeDeleted = new Task(
				"Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");
		tasks.add(testTaskToRemain);
		tasks.add(testTaskToBeDeleted);

		File todoFile = tmpFolder.newFile();
		writeTasksToFile(tasks, todoFile);
		TodoTxtHandler handler = new TodoTxtHandler(todoFile,
				tmpFolder.newFile(), LINE_SEPARATOR);

		handler.removeTask(testTaskToBeDeleted);

		assertEquals(1, handler.getTasks().size());
		assertEquals(testTaskToRemain, handler.getTasks().get(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void removeTask_NonExistingTask() throws IOException, ParseException {
		List<Task> tasks = new LinkedList<Task>();
		Task testTask1 = new Task(
				"Post signs around the @CONTEXT neighborhood +GarageSale2");
		Task testTask2 = new Task(
				"Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");
		tasks.add(testTask1);
		tasks.add(testTask2);

		File todoFile = tmpFolder.newFile();
		writeTasksToFile(tasks, todoFile);
		TodoTxtHandler handler = new TodoTxtHandler(todoFile,
				tmpFolder.newFile(), LINE_SEPARATOR);

		handler.removeTask(new Task(String.valueOf(new Random().nextInt())));

		assertEquals(tasks, handler.getTasks());
	}

	@Test
	public void getProjects() throws IOException, ParseException {
		List<String> expectedProjects = new LinkedList<String>();
		expectedProjects.add("GarageSale");
		expectedProjects.add("Meatballs");
		expectedProjects.add("GarageSale2");

		List<Task> tasks = new LinkedList<Task>();
		Task testTask1 = new Task(
				"Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");
		Task testTask2 = new Task(
				"Post signs around the @phone3 neighborhood +GarageSale +GarageSale2");
		tasks.add(testTask1);
		tasks.add(testTask2);

		File todoFile = tmpFolder.newFile();
		writeTasksToFile(tasks, todoFile);
		TodoTxtHandler handler = new TodoTxtHandler(todoFile,
				tmpFolder.newFile(), LINE_SEPARATOR);

		assertEquals(expectedProjects, handler.getProjects());
	}

	@Test
	public void getProjects_AfterTaskRemoved() throws IOException,
			ParseException {

	}

	@Test
	public void getContexts() throws IOException, ParseException {
		List<String> expectedContexts = new LinkedList<String>();
		expectedContexts.add("mobile");
		expectedContexts.add("phone_");
		expectedContexts.add("phone3");

		List<Task> tasks = new LinkedList<Task>();
		Task testTask1 = new Task(
				"Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");
		Task testTask2 = new Task(
				"Post signs around the @phone3 neighborhood @phone_ +GarageSale2");
		tasks.add(testTask1);
		tasks.add(testTask2);

		File todoFile = tmpFolder.newFile();
		writeTasksToFile(tasks, todoFile);
		TodoTxtHandler handler = new TodoTxtHandler(todoFile,
				tmpFolder.newFile(), LINE_SEPARATOR);

		assertEquals(expectedContexts, handler.getContexts());
	}

	@Test
	public void getTasksForProject() throws IOException, ParseException {

		Task testTask1 = new Task(
				"Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");
		Task testTask2 = new Task(
				"Post signs around the @phone3 neighborhood +GarageSale3");
		Task testTask3 = new Task(
				"Post signs around the @phone3 neighborhood +GarageSale");

		List<Task> expectedTasks = new LinkedList<Task>();
		expectedTasks.add(testTask1);
		expectedTasks.add(testTask3);

		List<Task> allTasks = new LinkedList<Task>();
		allTasks.add(testTask1);
		allTasks.add(testTask2);
		allTasks.add(testTask3);

		File todoFile = tmpFolder.newFile();
		writeTasksToFile(allTasks, todoFile);
		TodoTxtHandler handler = new TodoTxtHandler(todoFile,
				tmpFolder.newFile(), LINE_SEPARATOR);

		assertEquals(expectedTasks, handler.getTasksForProject("GarageSale"));
	}

	@Test
	public void getTasksForContext() throws IOException, ParseException {
		Task testTask1 = new Task(
				"Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");
		Task testTask2 = new Task(
				"Post signs around the @phone3 neighborhood +GarageSale3");
		Task testTask3 = new Task(
				"Post signs around the @phone3 neighborhood +GarageSale");

		List<Task> expectedTasks = new LinkedList<Task>();
		expectedTasks.add(testTask2);
		expectedTasks.add(testTask3);

		List<Task> allTasks = new LinkedList<Task>();
		allTasks.add(testTask1);
		allTasks.add(testTask2);
		allTasks.add(testTask3);

		File todoFile = tmpFolder.newFile();
		writeTasksToFile(allTasks, todoFile);
		TodoTxtHandler handler = new TodoTxtHandler(todoFile,
				tmpFolder.newFile(), LINE_SEPARATOR);

		assertEquals(expectedTasks, handler.getTasksForContext("phone3"));
	}

	@Test
	public void archiveTask() throws IOException, ParseException {
		Task taskToRemain = new Task(
				"Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");
		Task taskToBeArchived = new Task(
				"Post signs around the @phone3 neighborhood +GarageSale2");
		List<Task> tasks = new LinkedList<Task>();
		tasks.add(taskToRemain);
		tasks.add(taskToBeArchived);

		File todoFile = tmpFolder.newFile();
		File doneFile = tmpFolder.newFile();
		writeTasksToFile(tasks, todoFile);

		TodoTxtHandler handler = new TodoTxtHandler(todoFile, doneFile,
				LINE_SEPARATOR);

		handler.archiveTask(taskToBeArchived);

		assertEquals(tasks.size() - 1, handler.getTasks().size());
		assertEquals(taskToRemain, handler.getTasks().get(0));
		assertEquals(true, fileContainsTask(todoFile, taskToRemain));
		assertEquals(false, fileContainsTask(todoFile, taskToBeArchived));
		assertEquals(true, fileContainsTask(doneFile, taskToBeArchived));
		assertEquals(false, fileContainsTask(doneFile, taskToRemain));
	}
}
