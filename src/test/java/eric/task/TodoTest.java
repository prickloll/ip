package eric.task;

import org.junit.jupiter.api.Test;
import eric.EricException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TodoTest {
    @Test
    public void toFileFormat_normalTask_matchStorageFormat() {
        Todo todo = new Todo("read notes");
        assertEquals("T | 0 | read notes", todo.toFileFormat());

        todo.markDone();
        assertEquals("T | 1 | read notes", todo.toFileFormat());
    }

    @Test
    public void toFileFormat_descriptionAnomaly_preserveContent() {
        Todo todo = new Todo("Book | Chapter 1");
        assertEquals("T | 0 | Book | Chapter 1", todo.toFileFormat());
    }
}
