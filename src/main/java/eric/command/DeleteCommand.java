package eric.command;

import eric.EricException;
import eric.repository.Repository;
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
    public void execute(TaskList tasks, Ui ui, Repository repo) throws EricException {
        ui.displayDeleted(tasks.deleteTask(description), tasks.getSize());
        repo.save(tasks.getEveryTask());
    }
}
