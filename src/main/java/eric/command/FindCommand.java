package eric.command;

import java.util.ArrayList;

import eric.EricException;
import eric.repository.Repository;
import eric.task.Task;
import eric.task.TaskList;
import eric.ui.Ui;

/**
 * Represents a command to find a task via a description.
 */
public class FindCommand extends Command {
    private final String description;

    public FindCommand(String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     *
     * Specific to finding a task via a description.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Repository repo) throws EricException {
        ArrayList<Task> results = tasks.findTasksByKeyword(description);
        ui.displaySearch(results, "keyword: " + description.split(" ")[1]);
    }
}
