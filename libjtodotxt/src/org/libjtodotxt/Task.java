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

	public Task(final String line) throws ParseException {
		this.content = line;
		parseTask(line);
	}

	private void parseTask(final String line) throws ParseException {
		complete = parseComplete(line);
		priority = parsePriority(line);
		creationDate = parseCreationDate(line);
		completionDate = parseCompletionDate(line);
		contexts = parseContexts(line);
		projects = parseProjects(line);
	}

	private boolean parseComplete(final String line) {
		boolean parsedComplete = false;

		final Matcher completeMatcher = COMPLETE_PATTERN.matcher(line);

		if (completeMatcher.find()) {
			parsedComplete = true;
		}

		return parsedComplete;
	}

	private char parsePriority(final String line) throws ParseException {
		char parsedPriority = '\u0000';

		final Matcher priorityMatcher = PRIORITY_PATTERN.matcher(line);

		if (priorityMatcher.find()) {
			try {
				parsedPriority = priorityMatcher.group(1).charAt(0);
			} catch (final IndexOutOfBoundsException e) {
				throw new ParseException(
						"Could not parse correct priority from task line!", e);
			}
		}

		return parsedPriority;
	}

	private Date parseCreationDate(final String line) throws ParseException {
		Date parsedCreationDate = null;

		final Matcher creationDateMatcher = CREATION_DATE_PATTERN.matcher(line);

		if (creationDateMatcher.find()) {
			final DateFormat creationDateFormat = new SimpleDateFormat(
					Task.DATE_FORMAT);

			try {
				parsedCreationDate = creationDateFormat
						.parse(creationDateMatcher.group(creationDateMatcher
								.groupCount()));
			} catch (final java.text.ParseException e) {
				throw new ParseException(
						"Could not parse correct creation date from task line!",
						e);
			}
		}

		return parsedCreationDate;
	}

	private Date parseCompletionDate(final String line) throws ParseException {
		Date parsedCompletionDate = null;

		final Matcher completionDateMatcher = COMPLETION_DATE_PATTERN
				.matcher(line);

		if (completionDateMatcher.find()) {
			final DateFormat creationDateFormat = new SimpleDateFormat(
					Task.DATE_FORMAT);

			try {
				parsedCompletionDate = creationDateFormat
						.parse(completionDateMatcher
								.group(completionDateMatcher.groupCount()));
			} catch (final java.text.ParseException e) {
				throw new ParseException(
						"Could not parse correct completion date from task line!",
						e);
			}
		}

		return parsedCompletionDate;
	}

	private List<String> parseContexts(final String line) {
		final List<String> parsedContexts = new LinkedList<String>();

		final Matcher contextMatcher = CONTEXT_PATTERN.matcher(line);

		while (contextMatcher.find()) {
			for (int i = 1; i < contextMatcher.groupCount(); i++) {
				parsedContexts.add(contextMatcher.group(i));
			}
		}

		return parsedContexts;
	}

	private List<String> parseProjects(final String line) {
		final List<String> parsedProjects = new LinkedList<String>();

		final Matcher projectMatcher = PROJECT_PATTERN.matcher(line);

		while (projectMatcher.find()) {
			for (int i = 1; i < projectMatcher.groupCount(); i++) {
				parsedProjects.add(projectMatcher.group(i));
			}
		}

		return parsedProjects;
	}

	public boolean isComplete() {
		return complete;
	}

	public char getPriority() {
		return priority;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public String getLine() {
		return content;
	}

	public void setLine(final String line) throws ParseException {
		this.content = line;
		parseTask(line);
	}

	public List<String> getContexts() {
		return contexts;
	}

	public List<String> getProjects() {
		return projects;
	}

}
