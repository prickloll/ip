package eric.task;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

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
        String description = toDoDescription(userInput, "The todo's description cannot be empty!");
        Task t = new Todo(description);
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
        checkFormat(userInput, "Missing /by after declaring a deadline task!", "/by");
        String[] deadlineParts = tidyDeadlineParts(userInput);
        Task t = new Deadline(deadlineParts[0], deadlineParts[1]);
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
        checkFormat(userInput, "Event must have /from and /to identifiers!", "/from", "/to");
        String[] descriptions = tidyEventParts(userInput);
        Task t = new Event(descriptions[0], descriptions[1].trim(), descriptions[2].trim());
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
        int index = extractTaskIndex(input);
        boolean isMarked = input.startsWith("mark");
        Task task = tasks.get(index);
        updateTaskStatus(task, isMarked);
        return task;

    }

    /**
     * Removes a task from the task list based on the task number given.
     *
     * @param input The task number to delete.
     * @throws EricException If the task number is out of bounds or invalid.
     */
    public Task deleteTask(String input) throws EricException {
        int index = extractTaskIndex(input);
        return tasks.remove(index);

    }

    /**
     * Finds task based on given date
     *
     * @param userInput The user dateline input.
     * @return The list of tasks that matches the given date.
     * @throws EricException If the date format is inaccurate.
     */
    public ArrayList<Task> findTasksByDate(String userInput) throws EricException {
        LocalDate searchDate = decipherSearchDate(userInput);
        ArrayList<Task> results = new ArrayList<>();
        for (Task task : tasks) {
            assert task != null : "Task object cannot be null here";
            if (withinDateRange(task, searchDate)) {
                results.add(task);
            }
        }
        return results;
    }
    /**
     * Finds task based on keywords.
     * @param keywords The keywords to search against.
     * @param isStrict How strict the searching must be.
     * @param isTodo Task type flag for searching only for todo tasks.
     * @param isDeadline Task type flag for searching only for deadline tasks.
     * @param isEvent Task type flag for searching only for event tasks.
     * @return List of tasks that matches the contraints.
     * @throws EricException If the keyword is not specified.
     */
    public ArrayList<Task> findTasksByKeyword(String[] keywords, boolean isStrict, boolean isTodo, boolean isDeadline,
                                              boolean isEvent) throws EricException {
        return tasks.stream()
               .filter(task -> matchTaskType(task, isTodo, isDeadline, isEvent))
               .filter(task -> matchKeyword(task, keywords, isStrict))
               .collect(Collectors.toCollection(ArrayList::new));
    }

    public int getSize() {
        return tasks.size();
    }

    public ArrayList<Task> getEveryTask() {
        return tasks;
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
     * Helper method to retreive todo task description
     *
     * @param input The todo task string input by the user.
     * @param errorMsg The error message associated with the todo task.
     * @return The todo task's description.
     * @throws EricException If the todo task description is empty.
     */
    private String toDoDescription(String input, String errorMsg) throws EricException {
        String[] descriptionParts = input.split(" ", 2);
        if (descriptionParts.length < 2 || descriptionParts[1].trim().isEmpty()) {
            throw new EricException(errorMsg);
        }
        return descriptionParts[1].trim();
    }

    /**
     * Checks the format of the tasks which requires date inputs.
     *
     * @param input The user input.
     * @param errorMsg The error message associated with missing date parameters.
     * @param keywords The date parameters that are to be included.
     * @throws EricException If there are missing date parameters.
     */
    private void checkFormat(String input, String errorMsg, String... keywords) throws EricException {
        for (String keyword : keywords) {
            if (!input.contains(keyword)) {
                throw new EricException(errorMsg);
            }
        }
    }

    /**
     * Tidy the deadline task parts.
     * @param input The deadline task description.
     * @return The tidied deadline task string.
     * @throws EricException If there are missing parameters in the string.
     */
    private String[] tidyDeadlineParts(String input) throws EricException {
        int firstSpace = input.indexOf(" ");
        String[] deadlineParts = input.substring(firstSpace + 1).split(" /by");
        if (deadlineParts.length < 2 || deadlineParts[1].trim().isEmpty()) {
            throw new EricException("Missing deadline after /by!");
        }
        return new String[]{deadlineParts[0].trim(), deadlineParts[1].trim()};
    }

    /**
     * Tidy the event task parts.
     * @param input The event task description.
     * @return The tidied event task string.
     * @throws EricException If there are missing parameters in the string.
     */
    private String[] tidyEventParts(String input) throws EricException {
        int firstSpace = input.indexOf(" ");
        String content = input.substring(firstSpace + 1);
        String[] fromParts = content.split(" /from", -1);
        if (fromParts[0].trim().isEmpty()) {
            throw new EricException("Event description is empty!");
        }
        String[] toParts = fromParts[1].split(" /to", -1);
        if (toParts.length < 2 || toParts[0].trim().isBlank() || toParts[1].trim().isBlank()) {
            throw new EricException("Event missing values after /from and /to!");
        }

        return new String[]{fromParts[0].trim(), toParts[0].trim(), toParts[1].trim()};
    }

    /**
     * Extract the task index the user is interested in.
     *
     * @param input The string user input for the task index.
     * @return The task index integer.
     * @throws EricException If an invalid task index was given.
     */
    private int extractTaskIndex(String input) throws EricException {
        String[] inputParts = input.split(" ");
        if (inputParts.length < 2) {
            throw new EricException("Task number not specified!");
        }
        return validIndex(inputParts[1]);
    }

    /**
     * To update the task status as done or undone.
     *
     * @param task The interested task to mark.
     * @param isMarked Boolean value indicating whether a task is marked or not.
     */
    private void updateTaskStatus(Task task, boolean isMarked) {
        if (isMarked) {
            task.markDone();
        } else {
            task.markUndone();
        }
    }

    /**
     * Check whether the date given is within the date range of the task.
     *
     * @param task The task interested to check against.
     * @param date The date range specfied.
     * @return Boolean value indicated whether the date given is within the date range of the task.
     */
    private boolean withinDateRange(Task task, LocalDate date) {
        if (task instanceof Deadline) {
            return ((Deadline) task).by.equals(date);
        }
        if (task instanceof Event) {
            Event event = (Event) task;
            boolean notBeforeStart = !date.isBefore(event.from);
            boolean notAfterEnd = !date.isBefore(event.to);
            return notBeforeStart && notAfterEnd;
        }
        return false;
    }

    /**
     * Retrieve and convert the date string to a LocalDate object from the user input.
     *
     * @param userInput The user input which contains the date string.
     * @return The LocalDate object of the date string.
     * @throws EricException If an incorrect date format was given.
     */
    private LocalDate decipherSearchDate(String userInput) throws EricException {
        String[] descriptions = userInput.split(" ");
        if (descriptions.length < 2 || descriptions[1].trim().isEmpty()) {
            throw new EricException("Please specify the date you want to search for!");
        }
        try {
            return LocalDate.parse(descriptions[1].trim());
        } catch (DateTimeParseException e) {
            throw new EricException(("Use yyyy-MM-dd as the date format!"));
        }
    }
    /**
     * Return a boolean flag if the task description matches with the keywords.
     *
     * @param task The interested task to search against.
     * @param keywords The list of keywords to search for.
     * @param isStrict Specifying an "And" search or an "Or" search.
     * @return A boolean flag indicating if the task description is matched against any keyword.
     */
    private boolean matchKeyword(Task task, String[] keywords, boolean isStrict) {
        String taskDescription = task.getDescription().toLowerCase();
        if (isStrict) {
            return Arrays.stream(keywords)
                    .allMatch(keyword -> taskDescription.contains(keyword.toLowerCase()));
        } else {
            return Arrays.stream(keywords)
                    .anyMatch(keyword -> taskDescription.contains(keyword.toLowerCase()));
        }
    }

    /**
     * Return a boolean flag if the task matches the desired task type.
     *
     * @param task The interested task to search against.
     * @param isTodo A boolean flag to limit search to todo task.
     * @param isDeadLine A boolean flag to limit search to deadline task.
     * @param isEvent A boolean flag to limit search to event task.
     * @return
     */
    private boolean matchTaskType(Task task, boolean isTodo, boolean isDeadLine, boolean isEvent) {
        if (!isTodo && !isDeadLine && !isEvent) {
            return true;
        }
        if (isTodo && task instanceof Todo) {
            return true;
        }
        if (isDeadLine && task instanceof Deadline) {
            return true;
        }
        if (isEvent && task instanceof Event) {
            return true;
        }
        return false;

    }
}
