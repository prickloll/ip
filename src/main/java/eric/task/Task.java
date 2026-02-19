package eric.task;
import eric.EricException;

/** Represents a task that can be marked as completed or not */
public class Task {
    private static final String COMPLETE_INDICATOR = "X";
    private static final String INCOMPLETE_INDICATOR = " ";
    private static final String DONE_STATUS = "1";
    private static final String NOT_DONE_STATUS = "0";
    private static final String DELIMITER_PATTERN = " \\| ";
    private static final int MIN_TODO_PARTS = 3;
    private static final int MIN_DEADLINE_PARTS = 4;
    private static final int MIN_EVENT_PARTS = 5;
    private static final int DESCRIPTION_INDEX = 2;
    private static final int DEADLINE_DATE_INDEX = 3;
    private static final int EVENT_FROM_INDEX = 3;
    private static final int EVENT_TO_INDEX = 4;

    protected String description;
    protected boolean isDone;

    /**
     * Initialises a new task object with the given description.
     *
     * @param description The string that describes the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStatusIcon() {
        // Mark done task with X
        return isDone ? COMPLETE_INDICATOR : INCOMPLETE_INDICATOR;
    }
    /**
     * Marks the task as complete.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as incomplete.
     */
    public void markUndone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns the task in a suitable formatted string for storing in text file.
     *
     * @return String representation of task to be saved
     */
    public String toFileFormat() {
        assert description != null : "Task description cannot be null";
        String status = isDone ? DONE_STATUS : NOT_DONE_STATUS;
        return status + " | " + description;
    }

    /**
     * Parses a line from the save file and returns the corresponding Task object.
     * If the task is unset, null is returned.
     *
     * @param line The string obtained from the text file.
     * @return A Todo, Deadline or Event object.
     * @throws EricException If the line format is invalid or corrupted.
     */
    public static Task fileToTask(String line) throws EricException {
        if (line == null || line.trim().isEmpty()) {
            throw new EricException("File might be corrupted!");
        }
        String[] lineParts = line.split(" \\| ", -1);
        if (lineParts.length < MIN_TODO_PARTS) {
            throw new EricException("File might be corrupted!");
        }
        return createDesiredTask(lineParts);
    }

    /**
     * Creates and returns a task object.
     *
     * @param lineParts The string array pertaining to a task.
     * @return A task object.
     * @throws EricException If an unknown task type is found.
     */
    private static Task createDesiredTask(String[] lineParts) throws EricException {
        String taskType = lineParts[0];
        boolean isDone = lineParts[1].equals("1");
        String description = lineParts[DESCRIPTION_INDEX];

        Task currTask = createTaskByType(taskType, description, lineParts);
        assert currTask != null : "Task object should have been created.";

        if (isDone) {
            currTask.markDone();
        }

        return currTask;
    }

    /**
     * Creates a task object based on the task type.
     * Low-level: Handles task type switching and creation.
     *
     * @param taskType The task type identifier (T, D, E).
     * @param description The task description.
     * @param lineParts The full line parts for tasks needing additional data.
     * @return A Task object of the appropriate type.
     * @throws EricException If an unknown task type is found or required data is missing.
     */
    private static Task createTaskByType(String taskType, String description,
                                        String[] lineParts) throws EricException {
        switch(taskType) {
        case "T":
            return new Todo(description);
        case "D":
            if (lineParts.length < MIN_DEADLINE_PARTS) {
                throw new EricException("File might be corrupted!");
            }
            return new Deadline(description, lineParts[DEADLINE_DATE_INDEX]);
        case "E":
            if (lineParts.length < MIN_EVENT_PARTS) {
                throw new EricException("File might be corrupted!");
            }
            return new Event(description, lineParts[EVENT_FROM_INDEX], lineParts[EVENT_TO_INDEX]);
        default:
            throw new EricException("Unknown task type found in data file: " + taskType);
        }
    }


}
