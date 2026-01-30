package eric.task;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import eric.EricException;

/** Manages relevant task operations */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Initialises the TaskList object with a list of tasks.
     *
     * @param tasks The task list given.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Initialises an empty TaskList object.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds new todo task to the task list.
     *
     * @param userInput The input string from the user.
     * @return The Todo task created.
     * @throws EricException If the task description is empty.
     */
    public Task addTodo(String userInput) throws EricException {
        String[] descriptions = userInput.split(" ", 2);
        if (descriptions.length < 2 || descriptions[1].trim().isEmpty()) {
            throw new EricException("The todo's description cannot be empty!");
        }
        Task t = new Todo(descriptions[1].trim());
        tasks.add(t);
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
        if (!userInput.contains("/by")) {
            throw new EricException("Missing /by after declaring a deadline task!");
        }
        int firstSpace = userInput.indexOf(" ");
        String[] descriptions = userInput.substring(firstSpace + 1).split(" /by");
        if (descriptions.length < 2 || descriptions[1].trim().isEmpty()) {
            throw new EricException("Missing deadline after /by!");
        }
        Task t = new Deadline(descriptions[0], descriptions[1]);
        tasks.add(t);
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
        tasks.add(t);
        return t;
    }

    /**
     * Marks and unmarks a task and prints a confirmation message.
     *
     * @param input The command containing the task number.
     * @throws EricException If the index is invalid or missing.
     */
    public Task setMarkUnmarked(String input) throws EricException {

        String[] inputs = input.split(" ");
        if (inputs.length < 2) {
            throw new EricException("Task number not specified!");
        }
        int index = validIndex(inputs[1]);
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
        String[] descriptions = userInput.split(" ");
        if (descriptions.length < 2) {
            throw new EricException("Task number for deletion not specified!");
        }
        int index = validIndex((descriptions[1]));
        return tasks.remove(index);

    }

    /**
     * Tackles the task number given by the user.
     * @param input The interested task number.
     * @return The task index if it exists.
     * @throws EricException If the task index is out of bounds.
     */
    private int validIndex(String input) throws EricException {
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
        String[] descriptions = userInput.split(" ");
        if (descriptions.length < 2 || descriptions[1].trim().isEmpty()) {
            throw new EricException("Please specify the date you want to search for!");
        }
        try {
            LocalDate intDate = LocalDate.parse(descriptions[1].trim());
            ArrayList<Task> results = new ArrayList<>();
            for (Task t : tasks) {
                if (t instanceof Deadline && ((Deadline) t).by.equals(intDate)) {
                    results.add(t);
                } else if (t instanceof Event
                        && (intDate.isAfter(((Event) t).from) || intDate.isEqual(((Event) t).from))
                        && (intDate.isBefore(((Event) t).to) || intDate.isEqual(((Event) t).to))) {
                    results.add(t);
                }
            }
            return results;
        } catch (DateTimeParseException e) {
            throw new EricException(("Use yyyy-MM-dd as the date format!"));
        }

    }

    public int getSize() {
        return tasks.size();
    }

    public ArrayList<Task> getEveryTask() {
        return tasks;
    }
}
