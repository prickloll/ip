/** Represents a todo task that extends the Task class. */
public class Todo extends Task {
    public Todo (String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileFormat() {
        return "T | " + super.toFileFormat();
    }
}
