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
        assert removedTask != null : "Task should have been successfully removed and returned.";
        repo.save(tasks.getEveryTask());
        String response = ui.displayDeleted(removedTask, tasks.getSize());
        assert response != null : "ui should have returned a response.";
        return response;
    }
}
