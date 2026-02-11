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
    public String execute(TaskList tasks, Ui ui, Repository repo) throws EricException {
        assert tasks != null : "Command cannot execute with null tasks being passed.";
        assert ui != null : "Command cannot execute with null ui being passed.";
        assert repo != null : "Command cannot execute with null repo being passed.";
        ArrayList<Task> results = tasks.findTasksByKeyword(description);
        return ui.displaySearch(results, "keyword: " + description.split(" ")[1]);
    }
}
