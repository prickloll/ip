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
    private static final String SEARCH_PREFIX = "keyword: ";
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
        ArrayList<Task> results = tasks.findTasksByKeyword(description);
        assert results != null : "Search results list should be initialised even if it is empty.";
        String searchCriteria = extractSearchCriteria();
        return ui.displaySearch(results, searchCriteria);
    }

    /**
     * Helper method to deal with low-level parsing of string.
     *
     * @return The search result string.
     */
    private String extractSearchCriteria() {
        String keyword = description.split(" ")[1];
        return SEARCH_PREFIX + keyword.trim();
    }
}
