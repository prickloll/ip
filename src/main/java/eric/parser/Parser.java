package eric.parser;
import java.util.ArrayList;

import eric.EricException;
import eric.repository.Repository;
import eric.task.Task;
import eric.task.TaskList;
import eric.ui.Ui;

/**
 * Main logic for taking care of user inputs and calls the appropriate action.
 */
public class Parser {
    /**
     * Takes in the user input and execute corresponding commands.
     * @param userInput The string given by the user.
     * @param tasks The list of task to be modified or queried against.
     * @param ui The user interface for bot display.
     * @param repo The storage for the modified task list.
     * @return true if the application terminates, false otherwise.
     * @throws EricException If the commands or parameters are invalid.
     */
    public static boolean parse(String userInput, TaskList tasks, Ui ui, Repository repo) throws EricException {
        if (userInput.equals("bye")) {
            ui.bye();
            return true;
        }

        if (userInput.startsWith("todo")) {
            ui.displayTaskAdded(tasks.addTodo(userInput), tasks.getSize());
        } else if (userInput.startsWith("deadline")) {
            ui.displayTaskAdded(tasks.addDeadline(userInput), tasks.getSize());
        } else if (userInput.startsWith("event")) {
            ui.displayTaskAdded(tasks.addEvent(userInput), tasks.getSize());
        } else if (userInput.startsWith("mark") || userInput.startsWith("unmark")) {
            boolean isMark = userInput.startsWith("mark");
            ui.displayMarked(tasks.setMarkUnmarked(userInput), isMark);
        } else if (userInput.equals("list")) {
            ui.displayTaskList(tasks.getEveryTask());
        } else if (userInput.startsWith("delete")) {
            ui.displayDeleted(tasks.deleteTask(userInput), tasks.getSize());
        } else if (userInput.startsWith("finddate")) {
            ArrayList<Task> results = tasks.findTasksByDate(userInput);
            ui.displaySearch(results, userInput.split(" ")[1]);
        }
        else if (userInput.startsWith("find")) {
            ArrayList<Task> results = tasks.findTasksByKeyword(userInput);
            ui.displaySearch(results, "keyword: " + userInput.split(" ")[1]);
        }
        else {
            throw new EricException("Sorry, I can't seem to handle your request!");
        }

        repo.save(tasks.getEveryTask());
        return false;
    }
}
