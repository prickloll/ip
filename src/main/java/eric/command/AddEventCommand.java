package eric.command;

import eric.EricException;
import eric.repository.Repository;
import eric.task.TaskList;
import eric.ui.Ui;

/**
 * Represents a command to add and store a new event task.
 */
public class AddEventCommand extends Command {
    private final String description;

    public AddEventCommand(String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     *
     * Specific to adding and storing an event task.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Repository repo) throws EricException {
        ui.displayTaskAdded(tasks.addEvent(description), tasks.getSize());
        repo.save(tasks.getEveryTask());
    }
}
