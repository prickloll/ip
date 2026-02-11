package eric.task;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import eric.EricException;

/** Manages relevant task operations */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Initilises the TaskList object with a list of tasks.
     *
     * @param tasks The task list given.
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "Tasklist cannot be initialised with a null list";
        this.tasks = tasks;
    }

    /**
     * Initialises an empty TaskList object.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        assert tasks.isEmpty() : "A newly initialised task list should be empty";
    }

    /**
     * Adds new todo task to the task list.
     *
     * @param userInput The input string from the user.
     * @return The Todo task created.
     * @throws EricException If the task description is empty.
     */
    public Task addTodo(String userInput) throws EricException {
        assert userInput != null : "User input should not be null";
        String[] descriptions = userInput.split(" ", 2);
        if (descriptions.length < 2 || descriptions[1].trim().isEmpty()) {
            throw new EricException("The todo's description cannot be empty!");
        }
        Task t = new Todo(descriptions[1].trim());
        int oldTaskSize = tasks.size();
        tasks.add(t);
        assert tasks.size() == oldTaskSize + 1 : "The size of the task list should have increased after adding a task.";
        return t;
    }

    /**
     * Adds new deadline task to task list.
     *
     * @param userInput The input string containing the task description and /by.
     * @return The Deadline task created.
     * @throws EricException If the task format is invalid or missing.
     */
    public Task addDeadline(String userInput) throws EricException {
        assert userInput != null : "User input should not be null";
        if (!userInput.contains("/by")) {
            throw new EricException("Missing /by after declaring a deadline task!");
        }
        int firstSpace = userInput.indexOf(" ");
        String[] descriptions = userInput.substring(firstSpace + 1).split(" /by");
        if (descriptions.length < 2 || descriptions[1].trim().isEmpty()) {
            throw new EricException("Missing deadline after /by!");
        }
        Task t = new Deadline(descriptions[0], descriptions[1]);
        int oldTaskSize = tasks.size();
        tasks.add(t);
        assert tasks.size() == oldTaskSize + 1 : "The size of the task list should have increased after adding a task.";
        return t;
    }

    /**
     * Adds new event task to task list.
     *
     * @param userInput The input string containing the task description, /from and /to.
     * @return The Event task created.
     * @throws EricException If the task format is invalid or missing.
     */
    public Task addEvent(String userInput) throws EricException {
        assert userInput != null : "User input should not be null";
        if (!userInput.contains("/from") || !userInput.contains("/to")) {
            throw new EricException("Event must have /from and /to identifiers!");

        }
        int firstSpace = userInput.indexOf(" ");
        String[] descriptions = userInput.substring(firstSpace + 1).split(" /from", -1);
        if (descriptions[0].trim().isEmpty()) {
            throw new EricException("Event description is empty!");
        }
        String[] dateParts = descriptions[1].split(" /to", -1);
        if (dateParts.length < 2 || dateParts[0].trim().isBlank() || dateParts[1].trim().isBlank()) {
            throw new EricException("Event missing values after /from and /to!");
        }
        Task t = new Event(descriptions[0], dateParts[0].trim(), dateParts[1].trim());
        int oldTaskSize = tasks.size();
        tasks.add(t);
        assert tasks.size() == oldTaskSize + 1 : "The size of the task list should have increased after adding a task.";
        return t;
    }

    /**
     * Marks and unmarks a task and prints a confirmation message.
     *
     * @param input The command containing the task number.
     * @throws EricException If the index is invalid or missing.
     */
    public Task setMarkUnmarked(String input) throws EricException {
        assert input != null : "Input should not be null";
        String[] inputs = input.split(" ");
        if (inputs.length < 2) {
            throw new EricException("Task number not specified!");
        }
        int index = validIndex(inputs[1]);
        assert index >= 0 && index < tasks.size() : "Index returned should not be out of range.";
        boolean isMark = inputs[0].equals("mark");
        if (isMark) {
            tasks.get(index).markDone();
        } else {
            tasks.get(index).markUndone();
        }
        return tasks.get(index);

    }

    /**
     * Removes a task from the task list based on the task number given.
     *
     * @param userInput The task number to delete.
     * @throws EricException If the task number is out of bounds or invalid.
     */
    public Task deleteTask(String userInput) throws EricException {
        assert userInput != null : "User input should not be null";
        String[] descriptions = userInput.split(" ");
        if (descriptions.length < 2) {
            throw new EricException("Task number for deletion not specified!");
        }

        int index = validIndex((descriptions[1]));
        int oldTaskSize = tasks.size();
        Task removedTask = tasks.remove(index);
        assert tasks.size() == oldTaskSize - 1 : "Size of task list should have decreased after removing a task.";
        return removedTask;

    }

    /**
     * Tackles the task number given by the user.
     * @param input The interested task number.
     * @return The task index if it exists.
     * @throws EricException If the task index is out of bounds.
     */
    private int validIndex(String input) throws EricException {
        assert input != null : "Index input should not be null";
        try {
            int index = Integer.parseInt(input) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new EricException("Task number specified not within range!");
            }
            return index;
        } catch (IllegalArgumentException e) {
            throw new EricException("Enter a valid integer task number!");
        }
    }


    /**
     * Finds task based on given date
     *
     * @param userInput The user dateline input.
     * @return The list of tasks that matches the given date.
     * @throws EricException If the date format is inaccurate.
     */
    public ArrayList<Task> findTasksByDate(String userInput) throws EricException {
        assert userInput != null : "User input should not be null.";
        String[] descriptions = userInput.split(" ");
        if (descriptions.length < 2 || descriptions[1].trim().isEmpty()) {
            throw new EricException("Please specify the date you want to search for!");
        }
        try {
            LocalDate intDate = LocalDate.parse(descriptions[1].trim());
            assert intDate != null : "Parsed date should not be null.";
            ArrayList<Task> results = new ArrayList<>();
            assert tasks != null : "Task list should be initialised.";
            for (Task t : tasks) {
                assert t != null : "Task should not be null";
                if (t instanceof Deadline && ((Deadline) t).by.equals(intDate)) {
                    results.add(t);
                } else if (t instanceof Event
                        && (intDate.isAfter(((Event) t).from) || intDate.isEqual(((Event) t).from))
                        && (intDate.isBefore(((Event) t).to) || intDate.isEqual(((Event) t).to))) {
                    results.add(t);
                }
            }
            assert results != null : "Search results list should be initialised";
            return results;
        } catch (DateTimeParseException e) {
            throw new EricException(("Use yyyy-MM-dd as the date format!"));
        }

    }
    /**
     * Finds task based on given keyword.
     *
     * @param userInput The user's keyword to search for.
     * @return The list of tasks that matches the given keyword.
     * @throws EricException If the keyword is not specified.
     */
    public ArrayList<Task> findTasksByKeyword(String userInput) throws EricException {
        assert userInput != null : "User input should not be null.";
        String[] descriptions = userInput.split(" ", 2);
        if (descriptions.length < 2 || descriptions[1].trim().isEmpty()) {
            throw new EricException("Please specify the keyword you want to search for!");
        }

        String keyword = descriptions[1].toLowerCase();
        assert keyword != null : "Keyword should not be null after extraction.";
        ArrayList<Task> results = new ArrayList<>();
        assert tasks != null : "Task list should be initialised.";
        for (Task t : tasks) {
            assert t != null : "Task should not be null";
            assert t.getDescription() != null : "Task should not be found with a null description";
            if (t.getDescription().toLowerCase().contains(keyword)) {
                results.add(t);
            }
        }
        assert results != null : "Search results list should be initialised";
        return results;


    }

    public int getSize() {
        return tasks.size();
    }

    public ArrayList<Task> getEveryTask() {
        assert tasks != null : "Task list should not be null.";
        return tasks;
    }
}
