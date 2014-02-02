package org.libjtodotxt;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class TodoTxtHandlerTest {

	private static String LINE_SEPARATOR = System.getProperty("line.separator");

	@Test
	public void testEmptyContentTodoTxtFile() throws IOException,
			ParseException {
		new TodoTxtFile("");
	}

	@Test
	public void testTodoTxtFile() throws IOException, ParseException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs")
				.append(LINE_SEPARATOR)
				.append("Post signs around the @phone3 neighborhood +GarageSale2")
				.append(LINE_SEPARATOR);
		new TodoTxtFile(stringBuilder.toString());
	}

	@Test
	public void testAddTask() throws IOException, ParseException {
		TodoTxtFile testTodoTxtFile = new TodoTxtFile("");
		testTodoTxtFile
				.addTask(new Task(
						"Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs"));
	}

	@Test
	public void testRemoveTask() throws IOException, ParseException {
		Task testTaskToRemain = new Task(
				"Post signs around the @CONTEXT neighborhood +GarageSale2");
		Task testTaskToBeDeleted = new Task(
				"Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(testTaskToRemain.getLine()).append(LINE_SEPARATOR)
				.append(testTaskToBeDeleted.getLine()).append(LINE_SEPARATOR);
		TodoTxtFile testTodoTxtFile = new TodoTxtFile(stringBuilder.toString());

		testTodoTxtFile.removeTask(testTaskToBeDeleted);

		assertEquals(testTaskToRemain, testTodoTxtFile.getTasks().get(0));
	}

	@Test
	public void negativeTestRemoveTask() throws IOException, ParseException {
		List<Task> tasksToRemain = new LinkedList<Task>();

		Task testTask1 = new Task(
				"Post signs around the @CONTEXT neighborhood +GarageSale2");
		Task testTask2 = new Task(
				"Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");

		tasksToRemain.add(testTask1);
		tasksToRemain.add(testTask2);

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(testTask1.getLine()).append(LINE_SEPARATOR)
				.append(testTask2.getLine()).append(LINE_SEPARATOR);
		TodoTxtFile testTodoTxtFile = new TodoTxtFile(stringBuilder.toString());

		testTodoTxtFile.removeTask(new Task(String.valueOf(new Random()
				.nextInt())));

		assertEquals(tasksToRemain, testTodoTxtFile.getTasks());
	}

	@Test
	public void testGetProjects() throws IOException, ParseException {
		List<String> expectedProjects = new LinkedList<String>();
		expectedProjects.add("GarageSale");
		expectedProjects.add("Meatballs");
		expectedProjects.add("GarageSale2");

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs")
				.append(LINE_SEPARATOR)
				.append("Post signs around the @phone3 neighborhood +GarageSale +GarageSale2")
				.append(LINE_SEPARATOR);
		TodoTxtFile testTodoTxtFile = new TodoTxtFile(stringBuilder.toString());

		assertEquals(expectedProjects, testTodoTxtFile.getProjects());
	}

	@Test
	public void testGetContexts() throws IOException, ParseException {
		List<String> expectedContexts = new LinkedList<String>();
		expectedContexts.add("mobile");
		expectedContexts.add("phone_");
		expectedContexts.add("phone3");

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs")
				.append(LINE_SEPARATOR)
				.append("Post signs around the @phone3 neighborhood @phone_ +GarageSale2")
				.append(LINE_SEPARATOR);
		TodoTxtFile testTodoTxtFile = new TodoTxtFile(stringBuilder.toString());

		assertEquals(expectedContexts, testTodoTxtFile.getContexts());
	}

	@Test
	public void testGetTasksForProject() throws IOException, ParseException {
		List<String> expectedTasks = new LinkedList<String>();
		expectedTasks
				.add("Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");
		expectedTasks
				.add("Post signs around the @phone3 neighborhood +GarageSale");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs")
				.append(LINE_SEPARATOR)
				.append("Post signs around the @phone3 neighborhood +GarageSale3")
				.append(LINE_SEPARATOR)
				.append("Post signs around the @phone3 neighborhood +GarageSale")
				.append(LINE_SEPARATOR);
		TodoTxtFile testTodoTxtFile = new TodoTxtFile(stringBuilder.toString());

		assertEquals(expectedTasks,
				testTodoTxtFile.getTasksForProject("GarageSale"));
	}

	@Test
	public void testGetTasksForContext() throws IOException, ParseException {
		List<String> expectedTasks = new LinkedList<String>();
		expectedTasks
				.add("Post signs around the @phone3 neighborhood +GarageSale3");
		expectedTasks
				.add("Post signs around the @phone3 neighborhood +GarageSale");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs")
				.append(LINE_SEPARATOR)
				.append("Post signs around the @phone3 neighborhood +GarageSale3")
				.append(LINE_SEPARATOR)
				.append("Post signs around the @phone3 neighborhood +GarageSale")
				.append(LINE_SEPARATOR);
		TodoTxtFile testTodoTxtFile = new TodoTxtFile(stringBuilder.toString());

		assertEquals(expectedTasks,
				testTodoTxtFile.getTasksForContext("phone3"));
	}

	@Test
	public void testGetTasks() throws IOException, ParseException {
		Task testTask1 = new Task(
				"Post signs around the @CONTEXT neighborhood +GarageSale2");
		Task testTask2 = new Task(
				"Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");

		List<Task> expectedTasks = new LinkedList<>();
		expectedTasks.add(testTask1);
		expectedTasks.add(testTask2);

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(testTask1.getLine()).append(LINE_SEPARATOR)
				.append(testTask2.getLine()).append(LINE_SEPARATOR);
		TodoTxtFile testTodoTxtFile = new TodoTxtFile(stringBuilder.toString());

		assertEquals(expectedTasks, testTodoTxtFile.getTasks());
	}
}
