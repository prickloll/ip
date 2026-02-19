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
     * Finds a task via the relevant search criteria.
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
     * Extracts the search criteria into a string to display to the user.
     *
     * @return The search result string.
     */
    private String extractSearchCriteria() {
        String keywordsLine = formatKeywords();
        String strictMatch = formatStrictMatchStatus();
        String sortStatus = formatSortStatus();
        String filters = formatFilters();

        return SEARCH_PREFIX + keywordsLine + strictMatch + sortStatus + filters + "\n";
    }

    /**
     * Formats the keywords for display.
     *
     * @return Comma-separated keywords string.
     */
    private String formatKeywords() {
        return String.join(", ", keywords);
    }

    /**
     * Formats the strict match status for display.
     *
     * @return String indicating strict or loose matching.
     */
    private String formatStrictMatchStatus() {
        return isStrict ? " (All must match strictly) " : " (Loose match) ";
    }

    /**
     * Formats the sort status for display.
     *
     * @return String indicating sorted or unsorted results.
     */
    private String formatSortStatus() {
        return isSorted ? " (Sorted) " : " (Unsorted) ";
    }


    /**
     * Formats and returns a string represent the task type.
     *
     * @return String representation of the task type.
     */
    private String formatFilters() {
        // Guard clause if no filters are set, return empty string
        if (!isToDo && !isDeadLine && !isEvent) {
            return "";
        }

        String type = determineFilterType();
        return " in [" + type + "]";
    }

    /**
     * Determines the filter type name based on active flags.
     *
     * @return The filter type name.
     */
    private String determineFilterType() {
        if (isToDo) {
            return "Todos";
        } else if (isDeadLine) {
            return "Deadline";
        } else if (isEvent) {
            return "Event";
        }
        return "";
    }
}
