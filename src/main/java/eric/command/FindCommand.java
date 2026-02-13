package eric.command;

import java.time.LocalDate;
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
    private final String[] keywords;
    private final LocalDate searchDate;
    private final boolean isStrict;
    private final boolean isToDo;
    private final boolean isDeadLine;
    private final boolean isEvent;
    private final boolean isSorted;
    /**
     * Initialises a FindCommand object.
     *
     * @param keywords The keywords to find for.
     * @param isStrict Boolean flag to indicate a strict search.
     * @param isToDo Boolean flag to indicate searching for todo tasks.
     * @param isDeadLine Boolean flag to indicate searching for deadline tasks.
     * @param isEvent Boolean flag to indicate searching for event tasks.
     * @param isSorted Boolean flag to indicated return search results in sorted order.
     */
    public FindCommand(String[] keywords, LocalDate searchDate, boolean isStrict, boolean isToDo, boolean isDeadLine,
                       boolean isEvent, boolean isSorted) {
        this.keywords = keywords;
        this.isStrict = isStrict;
        this.isToDo = isToDo;
        this.isDeadLine = isDeadLine;
        this.isEvent = isEvent;
        this.isSorted = isSorted;
        this.searchDate = searchDate;
    }

    /**
     * {@inheritDoc}
     *
     * Specific to finding a task via a description.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Repository repo) throws EricException {
        ArrayList<Task> results = tasks.findTasksByKeyword(keywords, searchDate, isStrict, isToDo, isDeadLine,
                isEvent, isSorted);
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

        String keywordsLine = String.join(", ", keywords);
        String isStrictMatch = isStrict ? " (All must match strictly) " : " (Loose match) ";
        String isSortedResults = isSorted ? " (Sorted) " : " (Unsorted) ";
        String taskType = formatFilters();
        return SEARCH_PREFIX + keywordsLine + isStrictMatch + isSortedResults + taskType + "\n";
    }
    private String formatFilters() {
        if (!isToDo && !isDeadLine && !isEvent) {
            return "";
        }
        String type = "";
        if (isToDo) {
            type = "Todos";
        } else if (isDeadLine) {
            type = "Deadline";
        } else if (isEvent) {
            type = "Event";
        }
        return " in [" + type + "]";
    }
}
