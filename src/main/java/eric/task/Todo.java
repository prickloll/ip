package eric.task;

/** Represents a todo task without any date or time. */
public class Todo extends Task {
    /**
     * Initialises a Todo task with the given description.
     *
     * @param description The description of the task.
     */
    public Todo(String description) {
        super(description);
        assert this.description != null : "Todo description cannot be null.";
    }

    @Override
    public String toString() {
        assert this.description != null : "Todo description cannot be null during display.";
        return "[T]" + super.toString();
    }

    /**
     * {@inheritDoc}
     * Includes the task in format required for the text file.
     */
    @Override
    public String toFileFormat() {
        assert this.description != null : "Todo description must be present for file storage.";
        return "T | " + super.toFileFormat();
    }
}
