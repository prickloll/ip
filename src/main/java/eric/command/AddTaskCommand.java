package eric.command;

import eric.EricException;
import eric.repository.Repository;
import eric.task.Task;
import eric.task.TaskList;
import eric.ui.Ui;

/**
 * Represents an abstract base class for commands that add a new task.
 */
public abstract class AddTaskCommand extends Command {
    protected final String description;

    /**
     * Initialises an AddTaskCommand with a task description.
     *
     * @param description The description string provided by the user.
     */
    protected AddTaskCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the task addition workflow.
     *
     * @param tasks The task list to add to.
     * @param ui The user interface for displaying feedback.
     * @param repo The repository for persisting changes.
     * @return User feedback message after task is added.
     * @throws EricException If task creation or persistence fails.
     */
    @Override
    public final String execute(TaskList tasks, Ui ui, Repository repo) throws EricException {
        Task addedTask = addTask(tasks);
        assert addedTask != null : "Task should have been successfully created and returned.";
        saveTasks(tasks, repo);
        return ui.displayTaskAdded(addedTask, tasks.getSize());
    }

    /**
     * Subclasses implement this to perform the specific task creation.
     *
     * @param tasks The task list to add the task to.
     * @return The newly created Task object.
     * @throws EricException If the task description is invalid or empty.
     */
    protected abstract Task addTask(TaskList tasks) throws EricException;

    /**
     * Persists all tasks to the repository.
     *
     * @param tasks The task list to save.
     * @param repo The repository to save to.
     * @throws EricException If persistence fails.
     */
    protected void saveTasks(TaskList tasks, Repository repo) throws EricException {
        repo.save(tasks.getEveryTask());
    }
}

