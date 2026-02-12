package eric.task;

/** Represents a todo task without any date or time. */
public class Todo extends Task {
    private static final String TODO_TASK_SYMBOL = "[T]";
    private static final String FILE_TODO_SYMBOL = "T";
    /**
     * Initialises a Todo task with the given description.
     *
     * @param description The description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return TODO_TASK_SYMBOL + super.toString();
    }

    /**
     * {@inheritDoc}
     * Includes the task in format required for the text file.
     */
    @Override
    public String toFileFormat() {
        return FILE_TODO_SYMBOL + " | " + super.toFileFormat();
    }
}
