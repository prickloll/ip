package eric.command;

import eric.EricException;
import eric.repository.Repository;
import eric.task.Task;
import eric.task.TaskList;
import eric.ui.Ui;

/**
 * Represents a command to add and store a new todo task.
 */
public class AddTodoCommand extends Command {
    private final String description;

    public AddTodoCommand(String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     *
     * Specific to adding and storing a todo task.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Repository repo) throws EricException {
        Task addedTask = tasks.addTodo(description);
        saveTasks(tasks, repo);
        return ui.displayTaskAdded(addedTask, tasks.getSize());
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
