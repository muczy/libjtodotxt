# libjtodotxt #

A Java library for Gina Trapani's todo.txt.

**This library is still under heavy development! Please do not use it until an official version is released!**

## Main aspects ##
* Well tested, well working code.
* Lightweight code (minimize dependency).
* Android compatibility.

## Basic usage ##
First create a new `TodoTxtHandler` instance with a todo.txt file (we store tasks here), a done.txt file (used only to move archive tasks to) and a line separator string (Windows <-> Unix collision):

    String lineSeparator = System.getProperty("line.separator");
    TodoTxtHandler handler = new TodoTxtHandler(todoFile, doneFile, lineSeparator);

Then add a task:

    Task myTask = new Task("Schedule Goodwill pickup @mobile +GarageSale @phone +Meatballs");
    handler.addTask(myTask);

Get projects and contexts:

    List<String> projects = handler.getProjects();
    List<String> contexts = handler.getContexts();

Which tasks have the given project or context?

    List<Task> garageSaleTasks = handler.getTasksForProject("GarageSale");
    List<Task> phoneTasks = handler.getTasksForContext("phone");

Okay, just give me all the tasks:

    List<Task> allTasks = handler.getTasks();

Let's say we are done with the previously added task, so we archive it:

    handler.archiveTask(myTask);

or just simply remove it:

    handler.removeTask(myTask);