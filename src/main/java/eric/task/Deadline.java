package eric.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import eric.EricException;
/**
 * Represents a deadline task that can be configured with a deadline.
 */
public class Deadline extends Task {
    protected LocalDate by;

    /**
     * Initialises a Deadline task with a description and deadline.
     *
     * @param description The description of the task.
     * @param by The deadline in yyyy-MM-dd format
     */
    public Deadline(String description, String by) throws EricException {
        super(description);
        try {
            this.by = LocalDate.parse(by.trim());
        } catch (DateTimeParseException e) {
            throw new EricException("Enter the date in the yyyy-MM-dd format please!");
        }
        assert this.by != null : "Deadline 'by' date should not be null.";
        assert this.description != null : "Description should not be null here.";
    }

    @Override
    public String toString() {
        assert by != null : "Cannot format a null dateline";
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    /**
     * (@inheritDoc)
     * Includes the format the deadline task has to be in the text file.
     */
    @Override
    public String toFileFormat() {
        assert by != null : "Cannot format a null dateline";
        return "D | " + super.toFileFormat() + " | " + by;
    }
}
