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
     * Specific to adding and storing a deadline task.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Repository repo) throws EricException {
        Task addedTask = tasks.addDeadline(description);
        repo.save(tasks.getEveryTask());
        return ui.displayTaskAdded(addedTask, tasks.getSize());




    }
}
