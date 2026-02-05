package eric.command;

import java.util.ArrayList;

import eric.EricException;
import eric.repository.Repository;
import eric.task.Task;
import eric.task.TaskList;
import eric.ui.Ui;

/**
 * Represents a command to find a task via a date.
 */
public class FindDateCommand extends Command {
    private final String description;

    public FindDateCommand(String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     *
     * Specific to finding a task via a deadline.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Repository repo) throws EricException {
        ArrayList<Task> results = tasks.findTasksByDate(description);
        return ui.displaySearch(results, description.split(" ")[1]);
    }
}
