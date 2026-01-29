package eric.ui;
import java.util.Scanner;
import java.util.ArrayList;
import eric.task.Task;

/** Handles all user interaction */
public class Ui {
    private final Scanner scanner;

    /**
     * Initialises the Ui object with a Scanner.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next line of user input.
     *
     * @return The line that was read.
     */
    public String readInput() {
        return scanner.nextLine().trim();
    }

    public void linebreak() {
        String line = "----------------------------------------------------";
        System.out.println(line);
    }

    /**
     * Displays the empty list message.
     */
    public void emptyListIndi() {
        System.out.println("  Configuring a new empty task list!");
    }

    /**
     * Displays the greeting message.
     */
    public void greeting() {
        linebreak();
        System.out.println("Hello! I'm Eric\nWhat can I do for you?");
        linebreak();
    }

    /**
     * Displays the goodbye message.
     */
    public void bye() {
        linebreak();
        System.out.println("  Bye. Hope to see you again soon!");
        linebreak();
    }

    /**
     * Shows the error message.
     *
     * @param message The error message to display.
     */
    public void errorMsg(String message) {
        System.out.println(message);
    }

    /**
     * Displays a message when a task is being added.
     *
     * @param t The task being added.
     * @param size The size of the task list currently.
     */
    public void displayTaskAdded(Task t, int size) {
        linebreak();
        System.out.println(" Got it. I've added this task:");
        System.out.println("    " + t);
        System.out.println("  Now you have " + size + " tasks in the list.");
        linebreak();
    }

    /**
     * Displays a message when a task is marked or unmarked.
     *
     * @param t The task being marked or unmarked.
     * @param isDone The marked status.
     */
    public void displayMarked(Task t, boolean isDone) {
        linebreak();
        if (isDone) {
            System.out.println("  Nice I've marked this task as done:");
        } else {
            System.out.println("  OK, I've marked this task as not done yet:");
        }
        System.out.println("    " + t);
        linebreak();
    }

    /**
     * Displays a message when a task is removed.
     *
     * @param t The task being removed.
     * @param size The remaining task list size.
     */
    public void displayDeleted(Task t, int size) {
        linebreak();
        System.out.println("  Alright, I have deleted this task:");
        System.out.println("    "+ t);
        System.out.println("  Currently you have " + size + " tasks left!");
        linebreak();
    }

    /**
     * Displays the list of tasks.
     *
     * @param tasks The list of tasks to display.
     */
    public void displayTaskList(ArrayList<Task> tasks) {
        linebreak();
        if (tasks.isEmpty()) {
            System.out.println("  The task list is currently empty!");
        } else {
            System.out.println("  Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("  " + (i+1) + ". " + tasks.get(i));
            }
        }
        linebreak();
    }

    /**
     * Displays the search results.
     *
     * @param results The list of tasks that match the search.
     * @param date The formatted date string.
     */
    public void displaySearch(ArrayList<Task> results, String date) {
        linebreak();
        if (results.isEmpty()) {
            System.out.println("  No tasks on " + date);
        } else {
            System.out.println("  This is the list of tasks on " + date + ":");
            for (Task t : results) {
                System.out.println("    " + t);
            }
        }
        linebreak();
    }



}
