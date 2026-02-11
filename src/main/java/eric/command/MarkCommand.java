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
        assert tasks != null : "Command cannot execute with null tasks being passed.";
        assert ui != null : "Command cannot execute with null ui being passed.";
        assert repo != null : "Command cannot execute with null repo being passed.";
        boolean isMark = description.startsWith("mark");
        Task markTask = tasks.setMarkUnmarked(description);
        repo.save(tasks.getEveryTask());
        return ui.displayMarked(markTask, isMark);
    }
}
