package org.libjtodotxt;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ParseExceptionTest {

	@Test
	public void testParseException() {
		new ParseException();
	}

	@Test
	public void testParseExceptionStringThrowable() {
		String cause = "testCause";
		Throwable throwable = new ParseException();
		
		ParseException testException = new ParseException(cause, throwable);
		
		assertEquals(cause, testException.getMessage());
		assertEquals(throwable, testException.getCause());
	}

	@Test
	public void testParseExceptionString() {
		String cause = "testCause";
		
		ParseException testException = new ParseException(cause);
		
		assertEquals(cause, testException.getMessage());
	}

	@Test
	public void testParseExceptionThrowable() {
		Throwable throwable = new ParseException();
		
		ParseException testException = new ParseException(throwable);
		
		assertEquals(throwable, testException.getCause());
	}

}
