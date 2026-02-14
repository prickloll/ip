package eric.ui;
import java.util.ArrayList;

import eric.task.Task;

/** Handles all user interaction */
public class Ui {
    /**
     * Displays the empty list message.
     *
     * @return The empty list message.
     */
    public String emptyListIndi() {
        return "Configuring a new empty task list!";
    }

    /**
     * Displays the greeting message.
     *
     * @return The greeting message.
     */
    public String greeting() {
        return "Hello! I'm Eric\nWhat can I do for you?";
    }

    /**
     * Displays the goodbye message.
     *
     * @return The goodbye message.
     */
    public String bye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Shows the error message.
     *
     * @param message The error message to display.
     * @return The error message.
     */
    public String errorMsg(String message) {
        return message;
    }

    /**
     * Displays a message when a task is being added.
     *
     * @param task The task being added.
     * @param size The size of the task list currently.
     * @return The task list with the new added task.
     */
    public String displayTaskAdded(Task task, int size) {
        assert task != null : "Ui recieved a null task to display";
        assert size >= 0 : "Task list cannot be negative";
        return "Got it. I've added this task:\n" + task + "\n"
                + "Now you have " + size + " tasks in the list.";
    }

    /**
     * Displays a message when a task is marked or unmarked.
     *
     * @param task The task being marked or unmarked.
     * @param isDone The marked status.
     * @return The task that was marked.
     */
    public String displayMarked(Task task, boolean isDone) {
        String status;
        if (isDone) {
            status = "Nice I've marked this task as done:";
        } else {
            status = "OK, I've marked this task as not done yet:";
        }
        return status + "\n" + task;
    }

    /**
     * Displays a message when a task is removed.
     *
     * @param task The task being removed.
     * @param size The remaining task list size.
     * @return The message when a task is removed and the remaining task list.
     */
    public String displayDeleted(Task task, int size) {
        return "Alright, I have deleted this task:\n" + task + "\n"
                + "Currently you have " + size + " tasks left!";
    }

    /**
     * Displays the list of tasks.
     *
     * @param tasks The list of tasks to display.
     * @return The task list.
     */
    public String displayTaskList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            return "The task list is currently empty!";
        }
        String heading = "Here are the tasks in your list:\n";
        return heading + formatTaskList(tasks);
    }

    /**
     * Displays the search results.
     *
     * @param results The list of tasks that match the search.
     * @return The task list that matches the search result.
     */
    public String displaySearch(ArrayList<Task> results, String keyword) {
        if (results.isEmpty()) {
            return "No tasks on " + keyword;
        }
        String heading = "This is the list of tasks for " + keyword + ":";
        return heading + formatTaskList(results);
    }

    /**
     * Formats the array of task in a string.
     *
     * @param tasks The task to format.
     * @return The string of tasks in the task list.
     */
    private String formatTaskList(ArrayList<Task> tasks) {
        StringBuilder taskList = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            taskList.append(i + 1).append(". ").append(tasks.get(i));
            if (i < tasks.size() - 1) {
                taskList.append("\n");
            }
        }
        return taskList.toString();
    }



}
