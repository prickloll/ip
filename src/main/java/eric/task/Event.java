package eric.task;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import eric.EricException;

/**
 * Represents an event task that can be configured with a start and end date
 */
public class Event extends Task {
    private static final String DATE_OUTPUT_PATTERN = "MMM d yyyy";
    private static final String EVENT_TASK_SYMBOL = "[E]";
    private static final String FILE_EVENT_SYMBOL = "E";
    protected LocalDate to;
    protected LocalDate from;

    /**
     * Initialises an Event task with a description, start and end time.
     *
     * @param description The description of the event.
     * @param from The start time and/or date of the event in yyyy-MM-dd format.
     * @param to The end time and/or date of the event in yyyy-MM-dd format.
     * @throws EricException If wrong date format given.
     */
    public Event(String description, String from, String to) throws EricException {
        super(description);
        parseDate(from, to);

    }

    /**
     * Parses the from and to string into the Event object.
     *
     * @param from The from date string in yyyy-MM-dd format.
     * @param to The to date string in yyyy-MM-dd format.
     * @throws EricException If wrong date format given.
     */
    private void parseDate(String from, String to) throws EricException {
        try {
            this.from = LocalDate.parse(from.trim());
            this.to = LocalDate.parse(to.trim());
            if (this.from.isAfter(this.to)) {
                throw new EricException("Event start date must not be after end date.");
            }
            assert this.from != null && this.to != null : "Event from and to must not be null.";
        } catch (DateTimeParseException e) {
            throw new EricException("Event date is in the wrong format!");
        }


    }

    /**
     * Returns a human-readable representation of the event task.
     *
     * @return The formatted string.
     */
    @Override
    public String toString() {
        assert this.from != null && this.to != null : "Event  from and to must not be null.";
        return EVENT_TASK_SYMBOL + super.toString() + " (from: "
                + from.format(DateTimeFormatter.ofPattern(DATE_OUTPUT_PATTERN))
                + " to: " + to.format(DateTimeFormatter.ofPattern(DATE_OUTPUT_PATTERN)) + ")";
    }

    /**
     * {@inheritDoc}
     *
     * @return The text representation suitable for saving to file
     */
    @Override
    public String toFileFormat() {
        assert from != null && to != null : "Event  from and to must not be null.";
        return FILE_EVENT_SYMBOL + " | " + super.toFileFormat() + " | " + this.from + " | " + this.to;
    }
}
