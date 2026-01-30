package eric.command;

import eric.EricException;
import eric.repository.Repository;
import eric.task.TaskList;
import eric.ui.Ui;

/**
 * Represents a command.
 */
public abstract class Command {

    /**
     * Executes the relevant command.
     *
     * @param tasks The list of tasks.
     * @param ui The user interface for displaying the bot.
     * @param repo The repository for saving or loading the tasks.
     * @throws EricException If an error occurs during command execution.
     */
    public abstract void execute(TaskList tasks, Ui ui, Repository repo) throws EricException;

    public boolean isExit() {
        return false;
    }
}
