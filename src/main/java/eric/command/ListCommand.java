package eric.command;

import eric.EricException;
import eric.repository.Repository;
import eric.task.TaskList;
import eric.ui.Ui;

/**
 * Represents a command to list the tasks.
 */
public class ListCommand extends Command {

    /**
     * {@inheritDoc}
     *
     * Specific to listing the tasks.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Repository repo) throws EricException {
        return ui.displayTaskList(tasks.getEveryTask());
    }
}
