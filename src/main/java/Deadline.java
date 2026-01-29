/**
 * Represents a deadline task that can be configured with a deadline.
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Initialises a Deadline task with a description and deadline.
     *
     * @param description The description of the task.
     * @param by The time and/or date the task has to be completed.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by:" + by + ")";
    }

    /**
     * (@inheritDoc)
     * Includes the format the deadline task has to be in the text file.
     */
    @Override
    public String toFileFormat() {
        return "D | " + super.toFileFormat();
    }
}
