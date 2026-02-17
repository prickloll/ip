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
        assert this.description != null : "Todo description cannot be null during display.";
        return TODO_TASK_SYMBOL + super.toString();
    }

    /**
     * {@inheritDoc}
     *
     * <p>File format: {@code T | <status> | <description>}</p>
     *
     * @return the text representation suitable for saving to file
     */
    @Override
    public String toFileFormat() {
        assert this.description != null : "Todo description must be present for file storage.";
        return FILE_TODO_SYMBOL + " | " + super.toFileFormat();
    }
}
