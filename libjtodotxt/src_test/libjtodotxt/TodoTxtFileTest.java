/**
 * 
 */
package libjtodotxt;

import java.io.IOException;

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
}
