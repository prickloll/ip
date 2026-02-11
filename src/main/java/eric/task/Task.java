package eric.task;
import eric.EricException;

/** Represents a task that can be marked as completed or not */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Initialises a new task object with the given description.
     *
     * @param description The string that describes the task.
     */
    public Task(String description) {
        assert description != null : "Task description cannot be null";
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStatusIcon() {
        // Mark done task with X
        return (isDone ? "X" : " ");
    }
    /**
     * Marks the task as complete.
     */
    public void markDone() {
        this.isDone = true;
        assert isDone : "Task should be marked as done";
    }

    /**
     * Marks the task as incomplete.
     */
    public void markUndone() {
        this.isDone = false;
        assert isDone : "Task should be marked as undone";
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
        return (isDone ? "1" : "0") + " | " + description;
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
        assert line != null : "Line cannot be null";
        String[] lineParts = line.split(" \\| ", -1);
        if (lineParts.length < 3) {
            throw new EricException("File might be corrupted!");
        }

        String taskType = lineParts[0];
        boolean isDone = lineParts[1].equals("1");
        String description = lineParts[2];

        Task currTask;
        switch(taskType) {
        case "T":
            currTask = new Todo(description);
            break;
        case "D":
            currTask = new Deadline(description, lineParts[3]);
            break;
        case "E":
            currTask = new Event(description, lineParts[3], lineParts[4]);
            break;
        default:
            return null;
        }
        assert currTask != null : "Task object should have been created.";
        if (isDone) {
            currTask.markDone();
        }
        return currTask;
    }

}
