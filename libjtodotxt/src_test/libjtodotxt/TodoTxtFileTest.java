/**
 * 
 */
package libjtodotxt;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.libjtodotxt.ParseException;
import org.libjtodotxt.TodoTxtFile;

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
}
