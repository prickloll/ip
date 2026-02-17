package eric.command;

import eric.EricException;
import eric.task.Task;
import eric.task.TaskList;

/**
 * Represents a command to add and store a new event task.
 */
public class AddEventCommand extends AddTaskCommand {

    /**
     * Initialises an AddEventCommand with event details.
     *
     * @param description The event command string including /from and /to flags with dates.
     */
    public AddEventCommand(String description) {
        super(description);
    }

    /**
     * Adds an event task to the task list.
     *
     * @param tasks The task list to add to.
     * @return The newly created Event task.
     * @throws EricException If the format is invalid or dates are malformed.
     */
    @Override
    protected Task addTask(TaskList tasks) throws EricException {
        return tasks.addEvent(description);
    }
}
