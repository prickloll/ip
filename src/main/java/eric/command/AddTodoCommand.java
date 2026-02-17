package eric.command;

import eric.EricException;
import eric.task.Task;
import eric.task.TaskList;

/**
 * Represents a command to add and store a new todo task.
 */
public class AddTodoCommand extends AddTaskCommand {

    /**
     * Initialises an AddTodoCommand with a task description.
     *
     * @param description The description string for the todo task.
     */
    public AddTodoCommand(String description) {
        super(description);
    }

    /**
     * Adds a todo task to the task list.
     *
     * @param tasks The task list to add to.
     * @return The newly created Todo task.
     * @throws EricException If the description is empty.
     */
    @Override
    protected Task addTask(TaskList tasks) throws EricException {
        return tasks.addTodo(description);
    }
}
