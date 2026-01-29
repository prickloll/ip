/** Represents a todo task without any date or time constaa. */
public class Todo extends Task {
    /**
     * Initialises a Todo task with the given description.
     *
     * @param description The description of the task.
     */
    public Todo (String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * {@inheritDoc}
     * Includes the task in format required for the text file.
     */
    @Override
    public String toFileFormat() {
        return "T | " + super.toFileFormat();
    }
}
