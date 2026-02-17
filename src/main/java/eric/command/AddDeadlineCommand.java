package eric.command;

import eric.EricException;
import eric.task.Task;
import eric.task.TaskList;

/**
 * Represents a command to add and store a new deadline task.
 */
public class AddDeadlineCommand extends AddTaskCommand {

    /**
     * Initialises an AddDeadlineCommand with a task description and deadline.
     *
     * @param description The deadline command string including /by flag and date.
     */
    public AddDeadlineCommand(String description) {
        super(description);
    }

    /**
     * Adds a deadline task to the task list.
     *
     * @param tasks The task list to add to.
     * @return The newly created Deadline task.
     * @throws EricException If the format is invalid or date is malformed.
     */
    @Override
    protected Task addTask(TaskList tasks) throws EricException {
        return tasks.addDeadline(description);
    }
}
