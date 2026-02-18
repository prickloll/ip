package eric.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import eric.EricException;

/**
 * Tests for Deadline class covering date parsing and formatting.
 */
public class DeadlineTest {

    @Test
    public void deadline_validDate_success() throws EricException {
        Deadline deadline = new Deadline("submit assignment", "2026-02-20");
        assertEquals("submit assignment", deadline.getDescription());
    }

    @Test
    public void deadline_toString_containsDateInCorrectFormat() throws EricException {
        Deadline deadline = new Deadline("final exam", "2026-02-20");
        String result = deadline.toString();
        assertEquals("[D][ ] final exam (by: Feb 20 2026)", result);
    }

    @Test
    public void deadline_toFileFormat_correctFormat() throws EricException {
        Deadline deadline = new Deadline("submit report", "2026-03-15");
        String result = deadline.toFileFormat();
        assertEquals("D | 0 | submit report | 2026-03-15", result);
    }

    @Test
    public void deadline_markDone_statusChanges() throws EricException {
        Deadline deadline = new Deadline("study for exam", "2026-02-25");
        deadline.markDone();
        String result = deadline.toString();
        assertEquals("[D][X] study for exam (by: Feb 25 2026)", result);
    }

    @Test
    public void deadline_markUndone_statusChanges() throws EricException {
        Deadline deadline = new Deadline("complete project", "2026-02-28");
        deadline.markDone();
        deadline.markUndone();
        String result = deadline.toString();
        assertEquals("[D][ ] complete project (by: Feb 28 2026)", result);
    }

    @Test
    public void deadline_invalidDateFormat_exceptionThrown() {
        EricException exception = assertThrows(EricException.class, ()
                -> new Deadline("some task", "02-20-2026"));
        assertEquals("Deadline date is in the wrong format!", exception.getMessage());
    }

    @Test
    public void deadline_invalidDate_exceptionThrown() {
        EricException exception = assertThrows(EricException.class, ()
                -> new Deadline("some task", "2026-02-30"));
        assertEquals("Deadline date is in the wrong format!", exception.getMessage());
    }

    @Test
    public void deadline_invalidMonth_exceptionThrown() {
        EricException exception = assertThrows(EricException.class, ()
                -> new Deadline("some task", "2026-13-01"));
        assertEquals("Deadline date is in the wrong format!", exception.getMessage());
    }

    @Test
    public void deadline_emptyDescription_success() throws EricException {
        Deadline deadline = new Deadline("", "2026-02-20");
        assertEquals("", deadline.getDescription());
    }

    @Test
    public void deadline_dateEdgeCases_success() throws EricException {
        // Test leap year
        Deadline deadline1 = new Deadline("leap year task", "2024-02-29");
        assertEquals("leap year task", deadline1.getDescription());

        // Test year boundary
        Deadline deadline2 = new Deadline("end of year", "2026-12-31");
        assertEquals("end of year", deadline2.getDescription());

        // Test start of year
        Deadline deadline3 = new Deadline("start of year", "2026-01-01");
        assertEquals("start of year", deadline3.getDescription());
    }

    @Test
    public void deadline_fileFormatWithDoneStatus_correct() throws EricException {
        Deadline deadline = new Deadline("archive files", "2026-02-20");
        deadline.markDone();
        String result = deadline.toFileFormat();
        assertEquals("D | 1 | archive files | 2026-02-20", result);
    }

    @Test
    public void deadline_specialCharactersInDescription_success() throws EricException {
        Deadline deadline = new Deadline("submit: report (final) @deadline", "2026-02-20");
        assertEquals("submit: report (final) @deadline", deadline.getDescription());
    }

    @Test
    public void deadline_longDescription_success() throws EricException {
        String longDesc =
                "This is a very long deadline description that contains multiple words "
                + "and should be handled correctly by the system";
        Deadline deadline = new Deadline(longDesc, "2026-02-20");
        assertEquals(longDesc, deadline.getDescription());
    }

    @Test
    public void deadline_dateComparison_correctOrdering() throws EricException {
        Deadline deadline1 = new Deadline("task1", "2026-02-20");
        Deadline deadline2 = new Deadline("task2", "2026-02-21");
        // Verify dates are different (implicit via different string representations)
        assertEquals("[D][ ] task1 (by: Feb 20 2026)", deadline1.toString());
        assertEquals("[D][ ] task2 (by: Feb 21 2026)", deadline2.toString());
    }
}

