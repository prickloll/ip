package eric.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import eric.EricException;
/**
 * Represents a deadline task that can be configured with a deadline.
 */
public class Deadline extends Task {
    private static final String DATE_OUTPUT_PATTERN = "MMM d yyyy";
    private static final String DEADLINE_TASK_SYMBOL = "[D]";
    private static final String FILE_DEADLINE_SYMBOL = "D";
    protected LocalDate by;

    /**
     * Initialises a Deadline task with a description and deadline.
     *
     * @param description The description of the task.
     * @param by The deadline in yyyy-MM-dd format.
     */
    public Deadline(String description, String by) throws EricException {
        super(description);
        this.by = parseDate(by);
    }

    /**
     * Parses dateString into an actual LocalDate object.
     *
     * @param dateString The date string given.
     * @return A LocalDate object.
     * @throws EricException The deadline is in the wrong format or not valid.
     */
    private LocalDate parseDate(String dateString) throws EricException {
        try {
            return LocalDate.parse(dateString.trim());
        } catch (DateTimeParseException e) {
            throw new EricException("Deadline date is in the wrong format!");
        }
    }

    /**
     * Returns a human-readable representation of the deadline task.
     *
     * @return formatted string, e.g. "[D][ ] description (by: Jan 1 2024)"
     */
    @Override
    public String toString() {
        checkInternalState();
        return DEADLINE_TASK_SYMBOL + super.toString() + " (by: "
                + by.format(DateTimeFormatter.ofPattern(DATE_OUTPUT_PATTERN)) + ")";
    }

    /**
     * {@inheritDoc}
     *
     * <p>File format: {@code D | <status> | <description> | <yyyy-MM-dd>}</p>
     *
     * @return the text representation suitable for saving to file
     */
    @Override
    public String toFileFormat() {
        checkInternalState();
        return FILE_DEADLINE_SYMBOL + " | " + super.toFileFormat() + " | " + by;
    }

    /**
     * Asserts the object's invariants before serialization.
     * Called before toString() and toFileFormat() to ensure data consistency
     * and catch internal state corruption early during testing.
     */
    private void checkInternalState() {
        assert this.by != null : "Deadline 'by' date should not be null.";
        assert this.description != null : "Description should not be null here.";
    }
}
