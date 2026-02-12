package eric.task;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import eric.EricException;

/**
 * Represents an event task that can be configured with a start and end date
 */
public class Event extends Task {
    protected LocalDate to;
    protected LocalDate from;

    /**
     * Intialises an Event task  with a description, start and end time.
     *
     * @param description The description of the event.
     * @param from The start time and/or date of the event.
     * @param to The end time and/or date of the event.
     */
    public Event(String description, String from, String to) throws EricException {
        super(description);
        try {
            this.from = LocalDate.parse(from.trim());
            this.to = LocalDate.parse(to.trim());
        } catch (DateTimeParseException e) {
            throw new EricException("Enter the date in the yyyy-MM-dd format please!");
        }
        assert this.from != null : "Event 'from' date should have been parsed.";
        assert this.to != null : "Event 'to' date should have been parsed.";

    }

    @Override
    public String toString() {
        assert from != null && to != null : "Event  from and to must not be null.";
        return "[E]" + super.toString() + " (from: " + from.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to: " + to.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    /**
     * {@inheritDoc}
     * Includes the start and end times of the task in the text file.
     */
    @Override
    public String toFileFormat() {
        assert from != null && to != null : "Event  from and to must not be null.";
        return "E | " + super.toFileFormat();
    }
}
