package eric.task;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import eric.EricException;

public class TaskListTest {
    @Test
    public void deleteTask_validIndex_success() throws EricException {
        TaskList tasks = new TaskList();
        tasks.addTodo("todo play piano");
        tasks.deleteTask("delete 1");
        assertEquals(0, tasks.getSize());
    }

    @Test
    public void deleteTask_validIndex_exceptionThrown() {
        try {
            new TaskList().deleteTask("delete 100");
            fail();
        } catch (EricException e) {
            assertEquals("Task number specified not within range!", e.getMessage());
        }

    }
}
