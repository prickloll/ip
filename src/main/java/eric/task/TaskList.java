package eric.task;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        String description = extractToDoDescription(userInput);
        Task task = new Todo(description);
        return addTaskToList(task);
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
        Task task = new Deadline(deadlineParts[0], deadlineParts[1]);
        return addTaskToList(task);
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
        Task task = new Event(descriptions[0], descriptions[1].trim(), descriptions[2].trim());
        return addTaskToList(task);
    }

    /**
     * Adds a task to the list with proper validation.
     *
     * @param task The task to add to the list.
     * @return The added task.
     */
    private Task addTaskToList(Task task) {
        int oldTaskSize = tasks.size();
        tasks.add(task);
        assert tasks.size() == oldTaskSize + 1 : "The size of the task list should have increased after adding a task.";
        return task;
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
     * Finds task based on keywords and filter.
     *
     * @param keywords The keywords to search against.
     * @param searchDate Optional date to filter tasks.
     * @param isStrict How strict the searching must be.
     * @param isTodo Task type flag for searching only for todo tasks.
     * @param isDeadline Task type flag for searching only for deadline tasks.
     * @param isEvent Task type flag for searching only for event tasks.
     * @param isSorted Whether to sort results alphabetically.
     * @return List of tasks that matches the constraints.
     */
    public ArrayList<Task> findTasksByKeyword(String[] keywords, LocalDate searchDate, boolean isStrict, boolean isTodo,
                                              boolean isDeadline, boolean isEvent, boolean isSorted) {
        Stream<Task> filtered = filterTasksBySearchCriteria(keywords, searchDate,
                                                           isStrict, isTodo,
                                                           isDeadline, isEvent);
        return collectWithSorting(filtered, isSorted);
    }

    /**
     * Filters tasks based on search criteria.
     *
     * @param keywords Keywords to search for.
     * @param searchDate Optional date filter.
     * @param isStrict Strictness of keyword matching.
     * @param isTodo Filter for todo tasks.
     * @param isDeadline Filter for deadline tasks.
     * @param isEvent Filter for event tasks.
     * @return Filtered stream of tasks.
     */
    private Stream<Task> filterTasksBySearchCriteria(String[] keywords, LocalDate searchDate,
                                                     boolean isStrict, boolean isTodo,
                                                     boolean isDeadline, boolean isEvent) {
        return tasks.stream()
            .filter(task -> matchTaskType(task, isTodo, isDeadline, isEvent))
            .filter(task -> matchKeyword(task, keywords, isStrict))
            .filter(task -> searchDate == null || withinDateRange(task, searchDate));
    }

    /**
     * Collects stream results with optional sorting.
     *
     * @param stream The filtered task stream.
     * @param isSorted Whether to sort alphabetically.
     * @return ArrayList of tasks, sorted if requested.
     */
    private ArrayList<Task> collectWithSorting(Stream<Task> stream, boolean isSorted) {
        if (isSorted) {
            stream = sortAlphabetically(stream);
        }
        return stream.collect(Collectors.toCollection(ArrayList::new));
    }


    public int getSize() {
        return tasks.size();
    }

    public ArrayList<Task> getEveryTask() {
        return tasks;
    }

    /**
     * Returns the task index in relation to the user's given task number.
     *
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
     * Extracts the todo task description from user input.
     *
     * @param input The todo task string input by the user.
     * @return The todo task's description.
     * @throws EricException If the todo task description is empty.
     */
    private String extractToDoDescription(String input) throws EricException {
        String[] descriptionParts = input.split(" ", 2);
        if (descriptionParts.length < 2 || descriptionParts[1].trim().isEmpty()) {
            throw new EricException("The todo's description cannot be empty!");
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
     * Tidies the deadline task parts into a string array.
     *
     * @param input The deadline task description.
     * @return The tidied deadline task string.
     * @throws EricException If there are missing parameters in the string.
     */
    private String[] tidyDeadlineParts(String input) throws EricException {
        int firstSpace = input.indexOf(" ");
        String content = input.substring(firstSpace + 1);

        // Find /by position
        int byIndex = content.indexOf("/by");
        if (byIndex == -1) {
            throw new EricException("Missing /by in deadline command!");
        }

        String description = content.substring(0, byIndex).trim();
        String dateString = content.substring(byIndex + 3).trim(); // +3 for "/by"

        if (description.isEmpty()) {
            throw new EricException("Deadline description cannot be empty!");
        }
        if (dateString.isEmpty()) {
            throw new EricException("Missing deadline after /by!");
        }

        return new String[]{description, dateString};
    }

    /**
     * Tidies the event task parts into a string array.
     *
     * @param input The event task description.
     * @return The tidied event task string.
     * @throws EricException If there are missing parameters in the string.
     */
    private String[] tidyEventParts(String input) throws EricException {
        int firstSpace = input.indexOf(" ");
        String content = input.substring(firstSpace + 1);

        // Find /from and /to positions
        int fromIndex = content.indexOf("/from");
        int toIndex = content.indexOf("/to");

        if (fromIndex == -1) {
            throw new EricException("Missing /from in event command!");
        }
        if (toIndex == -1) {
            throw new EricException("Missing /to in event command!");
        }
        if (toIndex <= fromIndex) {
            throw new EricException("/to must come after /from!");
        }

        String description = content.substring(0, fromIndex).trim();
        String fromDate = content.substring(fromIndex + 5, toIndex).trim();
        String toDate = content.substring(toIndex + 3).trim();

        if (description.isEmpty()) {
            throw new EricException("Event description cannot be empty!");
        }
        if (fromDate.isEmpty()) {
            throw new EricException("Missing start date after /from!");
        }
        if (toDate.isEmpty()) {
            throw new EricException("Missing end date after /to!");
        }

        return new String[]{description, fromDate, toDate};
    }

    /**
     * Extracts the task index the user is interested in.
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
     * Updates the task status as done or undone.
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
     * Checks whether the date given is within the date range of the task.
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
            boolean notAfterEnd = !date.isAfter(event.to);
            return notBeforeStart && notAfterEnd;
        }
        return false;
    }

    /**
     * Returns a boolean flag if the task description matches with the keywords.
     *
     * @param task The interested task to search against.
     * @param keywords The list of keywords to search for.
     * @param isStrict Specifying an "And" search or an "Or" search.
     * @return A boolean flag indicating if the task description is matched against any keyword.
     */
    private boolean matchKeyword(Task task, String[] keywords, boolean isStrict) {
        if (hasNoValidKeywords(keywords)) {
            return true;
        }

        String[] validKeywords = extractValidKeywords(keywords);
        if (validKeywords.length == 0) {
            return true;
        }

        return performKeywordMatching(task, validKeywords, isStrict);
    }

    /**
     * Checks if the provided keywords array contains no valid keywords.
     *
     * @param keywords The keywords array to check.
     * @return True if no valid keywords are present.
     */
    private boolean hasNoValidKeywords(String[] keywords) {
        return keywords == null || keywords.length == 0
                || (keywords.length == 1 && keywords[0].isEmpty());
    }

    /**
     * Extracts non-empty keywords from the keywords array.
     *
     * @param keywords The original keywords array.
     * @return Array containing only non-empty keywords.
     */
    private String[] extractValidKeywords(String[] keywords) {
        return Arrays.stream(keywords)
                .filter(keyword -> !keyword.isEmpty())
                .toArray(String[]::new);
    }

    /**
     * Performs the actual keyword matching against task description.
     *
     * @param task The task to match against.
     * @param validKeywords Non-empty keywords to match.
     * @param isStrict Whether to use strict (AND) or loose (OR) matching.
     * @return True if task matches the keyword criteria.
     */
    private boolean performKeywordMatching(Task task, String[] validKeywords, boolean isStrict) {
        String taskDescription = task.getDescription().toLowerCase();

        if (isStrict) {
            return Arrays.stream(validKeywords)
                    .allMatch(keyword -> taskDescription.contains(keyword.toLowerCase()));
        } else {
            return Arrays.stream(validKeywords)
                    .anyMatch(keyword -> taskDescription.contains(keyword.toLowerCase()));
        }
    }

    /**
     * Returns a boolean flag if the task matches the desired task type.
     *
     * @param task The interested task to search against.
     * @param isTodo A boolean flag to limit search to todo task.
     * @param isDeadLine A boolean flag to limit search to deadline task.
     * @param isEvent A boolean flag to limit search to event task.
     * @return A boolean flag to indicate which task type it is tagged to.
     */
    private boolean matchTaskType(Task task, boolean isTodo, boolean isDeadLine, boolean isEvent) {
        // If no specific type filters are set, match all tasks
        if (!isTodo && !isDeadLine && !isEvent) {
            return true;
        }

        // Check each type filter
        return matchesTodo(task, isTodo)
                || matchesDeadline(task, isDeadLine)
                || matchesEvent(task, isEvent);
    }

    /**
     * Checks if task matches todo filter.
     */
    private boolean matchesTodo(Task task, boolean isTodo) {
        return isTodo && task instanceof Todo;
    }

    /**
     * Checks if task matches deadline filter.
     */
    private boolean matchesDeadline(Task task, boolean isDeadLine) {
        return isDeadLine && task instanceof Deadline;
    }

    /**
     * Checks if task matches event filter.
     */
    private boolean matchesEvent(Task task, boolean isEvent) {
        return isEvent && task instanceof Event;
    }

    /**
     * Sorts the task stream alphabetically while ignoring case.
     *
     * @param taskStream The stream of tasks to sort.
     * @return The sorted stream of tasks.
     */
    private Stream<Task> sortAlphabetically(Stream<Task> taskStream) {
        return taskStream.sorted((t1, t2) -> t1.getDescription().compareToIgnoreCase(t2.getDescription()));
    }
}
