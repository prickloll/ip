package eric.parser;
import eric.ui.Ui;
import eric.repository.Repository;
import eric.task.TaskList;
import eric.EricException;
import eric.task.Task;
import java.util.ArrayList;


public class Parser {
    public static boolean parse(String userInput, TaskList tasks, Ui ui, Repository repo) throws EricException {
        if (userInput.equals("bye")) {
            ui.bye();
            return true;
        }

        if (userInput.startsWith("todo")){
            ui.displayTaskAdded(tasks.addTodo(userInput), tasks.getSize());
        } else if (userInput.startsWith("deadline")){
            ui.displayTaskAdded(tasks.addDeadline(userInput), tasks.getSize());
        } else if (userInput.startsWith("event")){
            ui.displayTaskAdded(tasks.addEvent(userInput), tasks.getSize());
        } else if (userInput.startsWith("mark") || userInput.startsWith("unmark")) {
            boolean isMark = userInput.startsWith("mark");
            ui.displayMarked(tasks.setMarkUnmarked(userInput), isMark);
        } else if (userInput.equals("list")) {
            ui.displayTaskList(tasks.getEveryTask());
        } else if (userInput.startsWith("delete")) {
            ui.displayDeleted(tasks.deleteTask(userInput), tasks.getSize());
        } else if (userInput.startsWith("find")) {
            ArrayList<Task> results = tasks.findTasksByDate(userInput);
            ui.displaySearch(results, userInput.split(" ")[1]);
        }
        else {
            throw new EricException("Sorry, I can't seem to handle your request!");
        }

        repo.save(tasks.getEveryTask());
        return false;
    }
}
