package eric.command;

import eric.EricException;
import eric.repository.Repository;
import eric.task.Task;
import eric.task.TaskList;
import eric.ui.Ui;

/**
 * Represents a command to delete a task.
 */
public class DeleteCommand extends Command {
    private final String description;

    public DeleteCommand(String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     *
     * Specific to deleting a task.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Repository repo) throws EricException {
        assert tasks != null : "Command cannot execute with null tasks being passed.";
        assert ui != null : "Command cannot execute with null ui being passed.";
        assert repo != null : "Command cannot execute with null repo being passed.";
        Task removedTask = tasks.deleteTask(description);
        repo.save(tasks.getEveryTask());
        return ui.displayDeleted(removedTask, tasks.getSize());
    }
}
