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
        Task removedTask = tasks.deleteTask(description);
        saveTasks(tasks, repo);
        return ui.displayDeleted(removedTask, tasks.getSize());
    }

    /**
     * Helper method to abstract low-level saving task process.
     *
     * @param tasks The tasks to save.
     * @param repo The repository to save the tasks into.
     * @throws EricException If an error occurs during the saving of the task.
     */
    public void saveTasks(TaskList tasks, Repository repo) throws EricException {
        repo.save(tasks.getEveryTask());
    }
}
