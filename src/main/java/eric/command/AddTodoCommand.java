package eric.command;

import eric.EricException;
import eric.repository.Repository;
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
    public void execute(TaskList tasks, Ui ui, Repository repo) throws EricException {
        ui.displayTaskAdded(tasks.addTodo(description), tasks.getSize());
        repo.save(tasks.getEveryTask());
    }
}
