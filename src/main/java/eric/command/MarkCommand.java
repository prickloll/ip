package eric.command;

import eric.EricException;
import eric.repository.Repository;
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
    public void execute(TaskList tasks, Ui ui, Repository repo) throws EricException {
        boolean isMark = description.startsWith("mark");
        ui.displayMarked(tasks.setMarkUnmarked(description), isMark);
        repo.save(tasks.getEveryTask());
    }
}
