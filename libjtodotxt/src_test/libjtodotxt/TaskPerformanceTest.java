package libjtodotxt;

import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Test;
import org.libjtodotxt.ParseException;
import org.libjtodotxt.Task;

public class TaskPerformanceTest {
	@Test
	public void testTaskParse() {
		final int repeatTest = 5;
		final int numberOfTasks = 1000000;

		final Random random = new Random();
		final long[] results = new long[repeatTest];

		for (int j = 0; j < repeatTest; j++) {

			final String[] testData = new String[numberOfTasks];

			for (int i = 0; i < numberOfTasks; i++) {
				testData[i] = new StringBuilder("Test task @")
						.append(random.nextInt(10000)).append(" +")
						.append(random.nextInt(10000)).toString();
			}

			final long start = System.currentTimeMillis();

			for (int i = 0; i < numberOfTasks; i++) {
				try {
					new Task(testData[i]);
				} catch (final ParseException e) {
					fail(e.getLocalizedMessage());
				}
			}

			final long result = (System.currentTimeMillis() - start);
			System.out.println("Total time for " + numberOfTasks + " tasks: "
					+ result + " ms");

			results[j] = result;
		}

		long finalResult = 0L;
		for (int j = 0; j < repeatTest; j++) {
			finalResult += results[j];
		}

		finalResult = finalResult / repeatTest;
		System.out.println("\nFinal average result for " + repeatTest
				+ " runs: " + finalResult);
	}
}
