package org.libjtodotxt;

import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Test;

public class TaskPerformanceTest {
	@Test
	public void testTaskParse() {
		int repeatTest = 5;
		int numberOfTasks = 1000000;

		Random random = new Random();
		long[] results = new long[repeatTest];

		for (int j = 0; j < repeatTest; j++) {

			String[] testData = new String[numberOfTasks];

			for (int i = 0; i < numberOfTasks; i++) {
				testData[i] = new StringBuilder("Test task @")
						.append(random.nextInt(10000)).append(" +")
						.append(random.nextInt(10000)).toString();
			}

			long start = System.currentTimeMillis();

			for (int i = 0; i < numberOfTasks; i++) {
				try {
					new Task(testData[i]);
				} catch (ParseException e) {
					fail(e.getLocalizedMessage());
				}
			}

			long result = (System.currentTimeMillis() - start);
			System.out.println("Total time for " + numberOfTasks + " tasks: "
					+ result + " ms");

			results[j] = result;
		}

		long finalResult = 0L;
		for (int j = 0; j < repeatTest; j++) {
			finalResult += results[j];
		}

		finalResult = finalResult / repeatTest;
		System.out.println("\naverage result for " + repeatTest
				+ " runs: " + finalResult + " ms");
	}
}
