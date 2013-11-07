/**
 * 
 */
package org.libjtodotxt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

/**
 * @author evoavpe0
 * 
 */
public class TaskTest {

	private static char DEFAULT_PRIORITY = '\u0000';

	@Test
	public void testTask() {
		try {
			new Task("(B) Schedule Goodwill pickup +GarageSale @phone");
		} catch (final ParseException e) {
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	public void testStandardGetPriority() throws ParseException {
		final Task testTask = new Task(
				"(B) Schedule Goodwill pickup +GarageSale @phone");
		assertEquals('B', testTask.getPriority());
	}

	@Test
	public void testNonStandardGetPriority() throws ParseException {
		final Task testTask = new Task(
				"(X) Schedule Goodwill pickup +GarageSale @phone");
		assertEquals('X', testTask.getPriority());
	}

	@Test
	public void testNoPriorityGetPriority() throws ParseException {
		final Task testTask = new Task(
				"Schedule Goodwill pickup +GarageSale @phone");
		assertEquals(DEFAULT_PRIORITY, testTask.getPriority());
	}

	@Test
	public void negativeTestGetPriority() throws ParseException {
		final Task testTask = new Task(
				"Schedule Goodwill (B) pickup +GarageSale @phone");
		assertEquals(DEFAULT_PRIORITY, testTask.getPriority());
	}

	@Test
	public void testGetLine() throws ParseException {
		final String taskLine = "Schedule Goodwill pickup +GarageSale @phone";
		final Task testTask = new Task(taskLine);
		assertEquals(taskLine, testTask.getLine());
	}

	@Test
	public void testSetLine() throws ParseException {
		final String newTaskLine = "Post signs around the @phone3 neighborhood +GarageSale2";
		final List<String> projects = new LinkedList<String>();
		projects.add("GarageSale2");
		final List<String> contexts = new LinkedList<String>();
		contexts.add("phone3");

		final Task testTask = new Task(
				"Schedule Goodwill pickup +GarageSale @phone");

		testTask.setLine(newTaskLine);

		assertEquals(newTaskLine, testTask.getLine());
		assertEquals(DEFAULT_PRIORITY, testTask.getPriority());
		assertEquals(projects, testTask.getProjects());
		assertEquals(contexts, testTask.getContexts());
	}

	@Test
	public void testGetProjects() throws ParseException {
		final List<String> projects = new LinkedList<String>();
		projects.add("GarageSale");
		projects.add("Meatballs");

		final Task testTask = new Task(
				"Schedule Goodwill pickup +GarageSale @phone +Meatballs");

		assertEquals(projects, testTask.getProjects());
	}

	@Test
	public void testGetProjectsWithUnderscore() throws ParseException {
		final List<String> projects = new LinkedList<String>();
		projects.add("GarageSale_");
		projects.add("Meatballs");

		final Task testTask = new Task(
				"Schedule Goodwill pickup +GarageSale_ @phone +Meatballs");

		assertEquals(projects, testTask.getProjects());
	}

	@Test
	public void testNoProjectsGetProjects() throws ParseException {
		final Task testTask = new Task("Schedule Goodwill pickup @phone");

		assertEquals(0, testTask.getProjects().size());
	}

	@Test
	public void testGetContexts() throws ParseException {
		final List<String> contexts = new LinkedList<String>();
		contexts.add("mobile");
		contexts.add("phone");

		final Task testTask = new Task(
				"Schedule Goodwill pickup @mobile +GarageSale @phone +Meatballs");

		assertEquals(contexts, testTask.getContexts());
	}

	@Test
	public void testGetContextsWithUnderscore() throws ParseException {
		final List<String> contexts = new LinkedList<String>();
		contexts.add("mobile");
		contexts.add("phone_");

		final Task testTask = new Task(
				"Schedule Goodwill pickup @mobile +GarageSale @phone_ +Meatballs");

		assertEquals(contexts, testTask.getContexts());
	}

	@Test
	public void testNoContextsGetContexts() throws ParseException {
		final Task testTask = new Task("Schedule Goodwill pickup +GarageSale");

		assertEquals(0, testTask.getContexts().size());
	}

	@Test
	public void testGetCreationDate() throws ParseException,
			java.text.ParseException {
		final Task testTask = new Task(
				"2011-03-02 Document +TodoTxt task format");
		final DateFormat dateFormat = new SimpleDateFormat(Task.DATE_FORMAT);
		final Date expectedDate = dateFormat.parse("2011-03-02");

		assertEquals(expectedDate, testTask.getCreationDate());
	}

	@Test
	public void testNoCreationDateGetCreationDate() throws ParseException,
			java.text.ParseException {
		final Task testTask = new Task("Document +TodoTxt task format");

		assertEquals(null, testTask.getCreationDate());
	}

	@Test
	public void negativeTestGetCreationDate() throws ParseException,
			java.text.ParseException {
		final Task testTask = new Task(
				"Document +TodoTxt 2011-03-02 task format");

		assertEquals(null, testTask.getCreationDate());
	}

	@Test
	public void testPriorityGetCreationDate() throws ParseException,
			java.text.ParseException {
		final Task testTask = new Task(
				"(B) 2011-03-02 Document +TodoTxt task format");
		final DateFormat dateFormat = new SimpleDateFormat(Task.DATE_FORMAT);
		final Date expectedDate = dateFormat.parse("2011-03-02");

		assertEquals(expectedDate, testTask.getCreationDate());
	}

	@Test
	public void testGetCompletionDate() throws ParseException,
			java.text.ParseException {
		final Task testTask = new Task(
				"x 2011-03-02 Document +TodoTxt task format");
		final DateFormat dateFormat = new SimpleDateFormat(Task.DATE_FORMAT);
		final Date expectedDate = dateFormat.parse("2011-03-02");

		assertEquals(expectedDate, testTask.getCompletionDate());
	}

	@Test
	public void testNoCompletionDateGetCreationDate() throws ParseException,
			java.text.ParseException {
		final Task testTask = new Task("x Document +TodoTxt task format");

		assertEquals(null, testTask.getCompletionDate());
	}

	@Test
	public void negativeOtherDateTestGetCompletionDate() throws ParseException,
			java.text.ParseException {
		final Task testTask = new Task(
				"x Document +TodoTxt 2011-03-02 task format");

		assertEquals(null, testTask.getCompletionDate());
	}

	@Test
	public void negativeTestGetCompletionDate() throws ParseException,
			java.text.ParseException {
		final Task testTask = new Task(
				"2011-03-02 Document +TodoTxt task format");

		assertEquals(null, testTask.getCompletionDate());
	}

	@Test
	public void testPriorityGetCompletionDate() throws ParseException,
			java.text.ParseException {
		final Task testTask = new Task(
				"x 2011-03-02 (B) Document +TodoTxt task format");
		final DateFormat dateFormat = new SimpleDateFormat(Task.DATE_FORMAT);
		final Date expectedDate = dateFormat.parse("2011-03-02");

		assertEquals(expectedDate, testTask.getCompletionDate());
	}

	@Test
	public void testIsComplete() throws ParseException {
		final Task testTask = new Task("x 2011-03-03 Call Mom");
		assertEquals(true, testTask.isComplete());
	}

	@Test
	public void notCompletetestIsComplete() throws ParseException {
		final Task testTask = new Task("2011-03-03 Call Mom");
		assertEquals(false, testTask.isComplete());
	}

	@Test
	public void negativeTestIsComplete() throws ParseException {
		final Task testTask = new Task("x2011-03-03 Call Mom");
		assertEquals(false, testTask.isComplete());
	}

	@Test
	public void testEquals() throws ParseException {
		final String taskLine = "2011-03-03 Call Mom @phone +everyday";

		final Task testTask = new Task(taskLine);
		final Task equalTestTask = new Task(taskLine);

		assertEquals(true, testTask.equals(equalTestTask));
	}

	@Test
	public void negativeTestEquals() throws ParseException {
		final String taskLine = "2011-03-03 Call Mom @phone +everyday";

		final Task testTask = new Task(taskLine);
		final Task equalTestTask = new Task(taskLine + " NOT EQUAL");

		assertEquals(false, testTask.equals(equalTestTask));
	}

	@Test
	public void negativeNotTaskTestEquals() throws ParseException {
		final String taskLine = "2011-03-03 Call Mom @phone +everyday";

		final Task testTask = new Task(taskLine);

		assertEquals(false, testTask.equals(new ArrayList<>()));
	}

}
