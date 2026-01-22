/** Represents an event task that can be configured with a start and end date and extends the Task class. */
public class Event extends Task {
    protected String to;
    protected String from;

    public Event(String description, String to, String from) {
        super(description);
        this.to = to;
        this.from = from;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
