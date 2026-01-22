/** Represents an event task that can be configured with a start and end date and extends the Task class. */
public class Event extends Task {
    protected String to;
    protected String from;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;

    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.split("from ")[1] + " to: " + to.split("to ")[1] + ")";
    }
}
