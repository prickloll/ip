/**
 * Represents an event task that can be configured with a start and end date
 */
public class Event extends Task {
    protected String to;
    protected String from;

    /**
     * Intialises an Event task  with a description, start and end time.
     *
     * @param description The description of the event.
     * @param from The start time and/or date of the event.
     * @param to The end time and/or date of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;

    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    /**
     * {@inheritDoc}
     * Includes the start and end times of the task in the text file.
     */
    @Override
    public String toFileFormat() {
        return "E | " + super.toFileFormat();
    }
}
