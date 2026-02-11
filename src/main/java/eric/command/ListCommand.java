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
        assert tasks != null : "Command cannot execute with null tasks being passed.";
        assert ui != null : "Command cannot execute with null ui being passed.";
        assert repo != null : "Command cannot execute with null repo being passed.";
        return ui.displayTaskList(tasks.getEveryTask());
    }
}
