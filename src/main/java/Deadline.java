/** Represents a deadline task that can be configured with a deadline and extends the Task class. */
public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by:" + by + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + super.toFileFormat();
    }
}
