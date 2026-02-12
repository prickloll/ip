package eric.command;

import eric.EricException;
import eric.repository.Repository;
import eric.task.Task;
import eric.task.TaskList;
import eric.ui.Ui;

/**
 * Represents a command to mark or unmark a task.
 */
public class MarkCommand extends Command {
    public static final String MARK_KEYWORD = "mark";
    private final String description;

    public MarkCommand(String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     *
     * Specific to marking or unmarking a task.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Repository repo) throws EricException {
        boolean isMark = checkIsMark();
        Task markTask = tasks.setMarkUnmarked(description);
        assert markTask != null : "Task to be marked or unmarked should not be null.";
        saveTasks(tasks, repo);
        return ui.displayMarked(markTask, isMark);


    }

    private boolean checkIsMark() {
        return description.startsWith(MARK_KEYWORD);
    }
    /**
     * Helper method to abstract low-level saving task process.
     *
     * @param tasks The tasks to save.
     * @param repo The repository to save the tasks into.
     * @throws EricException If an error occurs during the saving of the task.
     */
    public void saveTasks(TaskList tasks, Repository repo) throws EricException {
        repo.save(tasks.getEveryTask());
    }
}
