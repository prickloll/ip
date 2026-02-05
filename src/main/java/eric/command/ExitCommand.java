package eric.command;

import eric.EricException;
import eric.repository.Repository;
import eric.task.TaskList;
import eric.ui.Ui;

/**
 * Represents a command to exit a task.
 */
public class ExitCommand extends Command {

    /**
     * {@inheritDoc}
     *
     * Specific to exiting the program.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Repository repo) throws EricException {
        return ui.bye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
