package eric.command;

import eric.EricException;
import eric.repository.Repository;
import eric.task.Task;
import eric.task.TaskList;
import eric.ui.Ui;

/**
 * Represents a command to add and store a new deadline task.
 */
public class AddDeadlineCommand extends Command {
    private final String description;

    public AddDeadlineCommand(String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     *
     * Adds and stores a deadline task.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Repository repo) throws EricException {
        Task addedTask = tasks.addDeadline(description);
        assert addedTask != null : "Task should have been successfully created and returned.";
        saveTasks(tasks, repo);
        return ui.displayTaskAdded(addedTask, tasks.getSize());
    }

    /**
     * Abstracts low-level saving task process.
     *
     * @param tasks The tasks to save.
     * @param repo The repository to save the tasks into.
     * @throws EricException If an error occurs during the saving of the task.
     */
    public void saveTasks(TaskList tasks, Repository repo) throws EricException {
        repo.save(tasks.getEveryTask());
    }
}
