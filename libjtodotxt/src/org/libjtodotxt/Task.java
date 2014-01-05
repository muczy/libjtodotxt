package org.libjtodotxt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task {

	public static String DATE_FORMAT = "yyyy-MM-dd";

	private static Pattern COMPLETE_PATTERN = Pattern.compile("^x\\ ");
	private static Pattern PRIORITY_PATTERN = Pattern
			.compile("^\\(([A-Z])\\)\\ ");
	private static Pattern CREATION_DATE_PATTERN = Pattern
			.compile("^(\\(([A-Z])\\)\\ ){0,1}(\\d{4}\\-\\d{2}\\-\\d{2})");
	private static Pattern COMPLETION_DATE_PATTERN = Pattern
			.compile("^x\\ (\\d{4}\\-\\d{2}\\-\\d{2})");
	private static Pattern CONTEXT_PATTERN = Pattern.compile("@((\\S+))");
	private static Pattern PROJECT_PATTERN = Pattern.compile("\\+((\\S+))");

	private boolean complete;
	private char priority;
	private Date creationDate;
	private Date completionDate;
	private String content;
	private List<String> contexts;
	private List<String> projects;

	/**
	 * Constructs a Task initialized to the contents of the specified string.
	 * 
	 * @param line
	 *            the initial contents of the task.
	 * @throws ParseException
	 *             if the parsing of the string failed
	 */
	public Task(String line) throws ParseException {
		this.content = line;
		parseTask(line);
	}

	private void parseTask(String line) throws ParseException {
		complete = parseComplete(line);
		priority = parsePriority(line);
		creationDate = parseCreationDate(line);
		completionDate = parseCompletionDate(line);
		contexts = parseContexts(line);
		projects = parseProjects(line);
	}

	private boolean parseComplete(String line) {
		boolean parsedComplete = false;

		Matcher completeMatcher = COMPLETE_PATTERN.matcher(line);

		if (completeMatcher.find()) {
			parsedComplete = true;
		}

		return parsedComplete;
	}

	private char parsePriority(String line) throws ParseException {
		char parsedPriority = '\u0000';

		Matcher priorityMatcher = PRIORITY_PATTERN.matcher(line);

		if (priorityMatcher.find()) {
			try {
				parsedPriority = priorityMatcher.group(1).charAt(0);
			} catch (IndexOutOfBoundsException e) {
				throw new ParseException(
						"Could not parse correct priority from task line!", e);
			}
		}

		return parsedPriority;
	}

	private Date parseCreationDate(String line) throws ParseException {
		Date parsedCreationDate = null;

		Matcher creationDateMatcher = CREATION_DATE_PATTERN.matcher(line);

		if (creationDateMatcher.find()) {
			DateFormat creationDateFormat = new SimpleDateFormat(
					Task.DATE_FORMAT);

			try {
				parsedCreationDate = creationDateFormat
						.parse(creationDateMatcher.group(creationDateMatcher
								.groupCount()));
			} catch (java.text.ParseException e) {
				throw new ParseException(
						"Could not parse correct creation date from task line!",
						e);
			}
		}

		return parsedCreationDate;
	}

	private Date parseCompletionDate(String line) throws ParseException {
		Date parsedCompletionDate = null;

		Matcher completionDateMatcher = COMPLETION_DATE_PATTERN.matcher(line);

		if (completionDateMatcher.find()) {
			DateFormat creationDateFormat = new SimpleDateFormat(
					Task.DATE_FORMAT);

			try {
				parsedCompletionDate = creationDateFormat
						.parse(completionDateMatcher
								.group(completionDateMatcher.groupCount()));
			} catch (java.text.ParseException e) {
				throw new ParseException(
						"Could not parse correct completion date from task line!",
						e);
			}
		}

		return parsedCompletionDate;
	}

	private List<String> parseContexts(String line) {
		List<String> parsedContexts = new LinkedList<String>();

		Matcher contextMatcher = CONTEXT_PATTERN.matcher(line);

		while (contextMatcher.find()) {
			for (int i = 1; i < contextMatcher.groupCount(); i++) {
				parsedContexts.add(contextMatcher.group(i));
			}
		}

		return parsedContexts;
	}

	private List<String> parseProjects(String line) {
		List<String> parsedProjects = new LinkedList<String>();

		Matcher projectMatcher = PROJECT_PATTERN.matcher(line);

		while (projectMatcher.find()) {
			for (int i = 1; i < projectMatcher.groupCount(); i++) {
				parsedProjects.add(projectMatcher.group(i));
			}
		}

		return parsedProjects;
	}

	/**
	 * Returns true if this task is complete.
	 * 
	 * @return true if this task is complete
	 */
	public boolean isComplete() {
		return complete;
	}

	/**
	 * Returns the priority of this task.
	 * 
	 * @return the priority of this task
	 */
	public char getPriority() {
		return priority;
	}

	/**
	 * Returns the creation date of this task.
	 * 
	 * @return the creation date of this task.
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Returns the completion date of this task.
	 * 
	 * @return the completion date of this task.
	 */
	public Date getCompletionDate() {
		return completionDate;
	}

	/**
	 * Returns the string representing this task.
	 * 
	 * @return the string representing this task.
	 */
	public String getLine() {
		return content;
	}

	/**
	 * Initializes this task with a new line content.
	 * 
	 * @param line
	 *            the new line content.
	 * @throws ParseException
	 *             if the parsing of the string failed
	 */
	public void setLine(String line) throws ParseException {
		this.content = line;
		parseTask(line);
	}

	/**
	 * Returns the contexts of this task.
	 * 
	 * @return the contexts of this task
	 */
	public List<String> getContexts() {
		return contexts;
	}

	/**
	 * Returns the projects of this task.
	 * 
	 * @return the projects of this task
	 */
	public List<String> getProjects() {
		return projects;
	}

	/**
	 * Indicates whether some other object is "equal to" this task by comparing
	 * the lines of the tasks.
	 */
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Task && getLine().equals(((Task) obj).getLine()));
	}

}
