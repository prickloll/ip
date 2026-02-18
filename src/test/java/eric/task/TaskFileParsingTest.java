package eric.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import eric.EricException;

/**
 * Comprehensive tests for Task.fileToTask() method.
 * Tests parsing of file format strings into Task objects with various scenarios.
 */
public class TaskFileParsingTest {

    @Test
    public void fileToTask_validTodoFormat_success() throws EricException {
        String line = "T | 0 | buy groceries";
        Task task = Task.fileToTask(line);

        assertInstanceOf(Todo.class, task);
        assertEquals("buy groceries", task.getDescription());
        assertFalse(task.getStatusIcon().contains("X"));
    }

    @Test
    public void fileToTask_validTodoFormatDone_success() throws EricException {
        String line = "T | 1 | complete homework";
        Task task = Task.fileToTask(line);

        assertInstanceOf(Todo.class, task);
        assertEquals("complete homework", task.getDescription());
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void fileToTask_validDeadlineFormat_success() throws EricException {
        String line = "D | 0 | submit assignment | 2026-02-20";
        Task task = Task.fileToTask(line);

        assertInstanceOf(Deadline.class, task);
        assertEquals("submit assignment", task.getDescription());
        assertFalse(task.getStatusIcon().contains("X"));
    }

    @Test
    public void fileToTask_validDeadlineFormatDone_success() throws EricException {
        String line = "D | 1 | finish project | 2026-03-01";
        Task task = Task.fileToTask(line);

        assertInstanceOf(Deadline.class, task);
        assertEquals("finish project", task.getDescription());
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void fileToTask_validEventFormat_success() throws EricException {
        String line = "E | 0 | team meeting | 2026-02-15 | 2026-02-16";
        Task task = Task.fileToTask(line);

        assertInstanceOf(Event.class, task);
        assertEquals("team meeting", task.getDescription());
        assertFalse(task.getStatusIcon().contains("X"));
    }

    @Test
    public void fileToTask_validEventFormatDone_success() throws EricException {
        String line = "E | 1 | conference | 2026-03-10 | 2026-03-12";
        Task task = Task.fileToTask(line);

        assertInstanceOf(Event.class, task);
        assertEquals("conference", task.getDescription());
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void fileToTask_descriptionWithDelimiter_success() throws EricException {
        String line = "T | 0 | task | with | pipes";
        Task task = Task.fileToTask(line);

        assertInstanceOf(Todo.class, task);
        assertEquals("task", task.getDescription());
    }

    @Test
    public void fileToTask_descriptionWithSpecialCharacters_success() throws EricException {
        String line = "T | 0 | buy @store & pay $50";
        Task task = Task.fileToTask(line);

        assertInstanceOf(Todo.class, task);
        assertEquals("buy @store & pay $50", task.getDescription());
    }

    @Test
    public void fileToTask_emptyDescription_success() throws EricException {
        String line = "T | 0 | ";
        Task task = Task.fileToTask(line);

        assertInstanceOf(Todo.class, task);
        assertEquals("", task.getDescription());
    }

    @Test
    public void fileToTask_insufficientParts_exceptionThrown() {
        String line = "T | 0";
        EricException exception = assertThrows(EricException.class, ()
                -> Task.fileToTask(line));
        assertEquals("File might be corrupted!", exception.getMessage());
    }

    @Test
    public void fileToTask_missingStatusField_exceptionThrown() {
        String line = "T | buy groceries";
        EricException exception = assertThrows(EricException.class, ()
                -> Task.fileToTask(line));
        assertEquals("File might be corrupted!", exception.getMessage());
    }

    @Test
    public void fileToTask_emptyLine_exceptionThrown() {
        String line = "";
        EricException exception = assertThrows(EricException.class, ()
                -> Task.fileToTask(line));
        assertEquals("File might be corrupted!", exception.getMessage());
    }

    @Test
    public void fileToTask_invalidTaskType_exceptionThrown() {
        String line = "X | 0 | unknown task type";
        EricException exception = assertThrows(EricException.class, ()
                -> Task.fileToTask(line));
        assertEquals("Unknown task type found in data file: X", exception.getMessage());
    }

    @Test
    public void fileToTask_deadlineMissingDate_exceptionThrown() {
        String line = "D | 0 | submit assignment";
        EricException exception = assertThrows(EricException.class, ()
                -> Task.fileToTask(line));
        assertTrue(exception.getMessage().contains("corrupted")
                || exception.getMessage().contains("format"));
    }

    @Test
    public void fileToTask_eventMissingEndDate_exceptionThrown() {
        String line = "E | 0 | meeting | 2026-02-15";
        EricException exception = assertThrows(EricException.class, ()
                -> Task.fileToTask(line));
        assertTrue(exception.getMessage().contains("corrupted")
                || exception.getMessage().contains("format"));
    }

    @Test
    public void fileToTask_multipleTasksSequence_success() throws EricException {
        String[] lines = {
            "T | 0 | task 1",
            "D | 1 | task 2 | 2026-02-20",
            "E | 0 | task 3 | 2026-02-15 | 2026-02-16"
        };

        Task task1 = Task.fileToTask(lines[0]);
        Task task2 = Task.fileToTask(lines[1]);
        Task task3 = Task.fileToTask(lines[2]);

        assertInstanceOf(Todo.class, task1);
        assertInstanceOf(Deadline.class, task2);
        assertInstanceOf(Event.class, task3);

        assertEquals("task 1", task1.getDescription());
        assertEquals("task 2", task2.getDescription());
        assertEquals("task 3", task3.getDescription());

        assertFalse(task1.getStatusIcon().contains("X"));
        assertEquals("X", task2.getStatusIcon());
        assertFalse(task3.getStatusIcon().contains("X"));
    }

    @Test
    public void fileToTask_statusFieldInvalidValue_marksAsIncomplete()
            throws EricException {
        String line = "T | 2 | task description";
        Task task = Task.fileToTask(line);

        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    public void fileToTask_longDescriptionWithManyWords_success()
            throws EricException {
        String longDesc = "This is a very long task description with many "
                + "words that needs to be parsed correctly from the file format";
        String line = "T | 0 | " + longDesc;
        Task task = Task.fileToTask(line);

        assertEquals(longDesc, task.getDescription());
    }

    @Test
    public void fileToTask_descriptionWithNumbers_success() throws EricException {
        String line = "D | 0 | Pay 12345 invoices by | 2026-02-20";
        Task task = Task.fileToTask(line);

        assertInstanceOf(Deadline.class, task);
        assertTrue(task.getDescription().contains("12345"));
    }
}


