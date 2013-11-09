/**
 * 
 */
package org.libjtodotxt;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

/**
 * @author muczy
 * 
 */
public class TodoTxtFileTest {

	private static String LINE_SEPARATOR = System.getProperty("line.separator");

	@Test
	public void testEmptyContentTodoTxtFile() throws IOException,
			ParseException {
		new TodoTxtFile("");
	}

	@Test
	public void testTodoTxtFile() throws IOException, ParseException {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs")
				.append(LINE_SEPARATOR)
				.append("Post signs around the @phone3 neighborhood +GarageSale2")
				.append(LINE_SEPARATOR);
		new TodoTxtFile(stringBuilder.toString());
	}

	@Test
	public void testAddTask() throws IOException, ParseException {
		final TodoTxtFile testTodoTxtFile = new TodoTxtFile("");
		testTodoTxtFile
				.addTask("Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");
	}

	@Test
	public void testRemoveTask() throws IOException, ParseException {
		final Task testTaskToRemain = new Task(
				"Post signs around the @CONTEXT neighborhood +GarageSale2");
		final Task testTaskToBeDeleted = new Task(
				"Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");

		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(testTaskToRemain.getLine()).append(LINE_SEPARATOR)
				.append(testTaskToBeDeleted.getLine()).append(LINE_SEPARATOR);
		final TodoTxtFile testTodoTxtFile = new TodoTxtFile(
				stringBuilder.toString());

		testTodoTxtFile.removeTask(testTaskToBeDeleted.getLine());

		assertEquals(testTaskToRemain, testTodoTxtFile.getTasks().get(0));
	}
	
	@Test
	public void negativeTestRemoveTask() throws IOException, ParseException {
		List<Task> tasksToRemain = new LinkedList<Task>();
		
		final Task testTask1 = new Task(
				"Post signs around the @CONTEXT neighborhood +GarageSale2");
		final Task testTask2 = new Task(
				"Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");
		
		tasksToRemain.add(testTask1);
		tasksToRemain.add(testTask2);

		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(testTask1.getLine()).append(LINE_SEPARATOR)
				.append(testTask2.getLine()).append(LINE_SEPARATOR);
		final TodoTxtFile testTodoTxtFile = new TodoTxtFile(
				stringBuilder.toString());

		testTodoTxtFile.removeTask(String.valueOf(new Random().nextInt()));

		assertEquals(tasksToRemain, testTodoTxtFile.getTasks());
	}

	@Test
	public void testGetProjects() throws IOException, ParseException {
		final List<String> expectedProjects = new LinkedList<String>();
		expectedProjects.add("GarageSale");
		expectedProjects.add("Meatballs");
		expectedProjects.add("GarageSale2");

		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs")
				.append(LINE_SEPARATOR)
				.append("Post signs around the @phone3 neighborhood +GarageSale +GarageSale2")
				.append(LINE_SEPARATOR);
		final TodoTxtFile testTodoTxtFile = new TodoTxtFile(
				stringBuilder.toString());

		assertEquals(expectedProjects, testTodoTxtFile.getProjects());
	}

	@Test
	public void testGetContexts() throws IOException, ParseException {
		final List<String> expectedContexts = new LinkedList<String>();
		expectedContexts.add("mobile");
		expectedContexts.add("phone_");
		expectedContexts.add("phone3");

		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs")
				.append(LINE_SEPARATOR)
				.append("Post signs around the @phone3 neighborhood @phone_ +GarageSale2")
				.append(LINE_SEPARATOR);
		final TodoTxtFile testTodoTxtFile = new TodoTxtFile(
				stringBuilder.toString());

		assertEquals(expectedContexts, testTodoTxtFile.getContexts());
	}

	@Test
	public void testGetTasksForProject() throws IOException, ParseException {
		final List<String> expectedTasks = new LinkedList<String>();
		expectedTasks
				.add("Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");
		expectedTasks
				.add("Post signs around the @phone3 neighborhood +GarageSale");
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs")
				.append(LINE_SEPARATOR)
				.append("Post signs around the @phone3 neighborhood +GarageSale3")
				.append(LINE_SEPARATOR)
				.append("Post signs around the @phone3 neighborhood +GarageSale")
				.append(LINE_SEPARATOR);
		final TodoTxtFile testTodoTxtFile = new TodoTxtFile(
				stringBuilder.toString());

		assertEquals(expectedTasks,
				testTodoTxtFile.getTasksForProject("GarageSale"));
	}

	@Test
	public void testGetTasksForContext() throws IOException, ParseException {
		final List<String> expectedTasks = new LinkedList<String>();
		expectedTasks
				.add("Post signs around the @phone3 neighborhood +GarageSale3");
		expectedTasks
				.add("Post signs around the @phone3 neighborhood +GarageSale");
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs")
				.append(LINE_SEPARATOR)
				.append("Post signs around the @phone3 neighborhood +GarageSale3")
				.append(LINE_SEPARATOR)
				.append("Post signs around the @phone3 neighborhood +GarageSale")
				.append(LINE_SEPARATOR);
		final TodoTxtFile testTodoTxtFile = new TodoTxtFile(
				stringBuilder.toString());

		assertEquals(expectedTasks,
				testTodoTxtFile.getTasksForContext("phone3"));
	}

	@Test
	public void testGetTasks() throws IOException, ParseException {
		final Task testTask1 = new Task(
				"Post signs around the @CONTEXT neighborhood +GarageSale2");
		final Task testTask2 = new Task(
				"Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");

		final List<Task> expectedTasks = new LinkedList<>();
		expectedTasks.add(testTask1);
		expectedTasks.add(testTask2);

		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(testTask1.getLine()).append(LINE_SEPARATOR)
				.append(testTask2.getLine()).append(LINE_SEPARATOR);
		final TodoTxtFile testTodoTxtFile = new TodoTxtFile(
				stringBuilder.toString());

		assertEquals(expectedTasks, testTodoTxtFile.getTasks());
	}
}
