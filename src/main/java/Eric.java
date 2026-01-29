import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Eric {
    private static Repository repo  = new Repository("./data/Eric.txt");
    private static ArrayList<Task> tasks;
    private static Ui ui;

    /**
     * Starts the chatbot and handles the main execution loop.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        ui = new Ui();
        try {
            tasks = repo.load();
        } catch (EricException e) {
            ui.errorMsg("  " + e.getMessage());
            tasks = new ArrayList<>();
        }

        if (tasks.isEmpty()) {
            ui.emptyListIndi();
        }

        ui.greeting();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("bye")) {
                ui.bye();
                break;
            }
            try {
                command(userInput);
                repo.save(tasks);
            } catch (EricException e) {
                ui.linebreak();
                ui.errorMsg(e.getMessage());
                ui.linebreak();
            }

        }


    }


    /**
     * Lists all tasks currently in the task list.
     */
    public static void listTask() {
        ui.displayTaskList(tasks);
    }


    /**
     * Marks and unmarks a task and prints a confirmation message.
     *
     * @params input The command containing the task number.
     * @throws EricException If the index is invalid or missing.
     */
    public static void setMarkUnmarked(String input) throws EricException {
        try {
            String[] inputs = input.split(" ");
            if (inputs.length < 2) {
                throw new EricException("Task number not specified!");
            }
            int index = Integer.parseInt(inputs[1]) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new EricException("Task number specified not in range of tasks available!");
            }
            boolean isMark = inputs[0].equals("mark");
            if (isMark) {
                tasks.get(index).markDone();
            } else {
                tasks.get(index).markUndone();
            }
            ui.displayMarked(tasks.get(index), isMark);

        } catch (IllegalArgumentException e) {
            throw new EricException("Enter a valid task number!");


        }
    }

    /**
     * Adds new todo task to the task list.
     *
     * @param userInput The input string from the user.
     * @return The Todo task created.
     * @throws EricException If the task description is empty.
     */
    public static void addTodo(String userInput) throws EricException {
        String[] descriptions = userInput.split(" ");
        if (descriptions.length < 2) {
            throw new EricException("The todo's description cannot be empty!");
        }
        Task t = new Todo(descriptions[1]);
        tasks.add(t);
        ui.displayTaskAdded(t, tasks.size());
    }

    /**
     * Adds new deadline task to task list.
     *
     * @param userInput The input string containing the task description and /by.
     * @return The Deadline task created.
     * @throws EricException If the task format is invalid or missing.
     */
    public static void addDeadline(String userInput) throws EricException {
        if (!userInput.contains("/by")) {
            throw new EricException("Missing /by after declaring a deadline task!");
        }
        int firstSpace = userInput.indexOf(" ");
        String[] descriptions = userInput.substring(firstSpace + 1).split(" /by");
        if (descriptions.length < 2 || descriptions[1].trim().isBlank()) {
            throw new EricException("Missing deadline after /by!");
        }
        Task t = new Deadline(descriptions[0], descriptions[1]);
        tasks.add(t);
        ui.displayTaskAdded(t, tasks.size());
    }

    /**
     * Adds new event task to task list.
     *
     * @param userInput The input string containing the task description, /from and /to.
     * @return The Event task created.
     * @throws EricException If the task format is invalid or missing.
     */
    public static void addEvent(String userInput) throws EricException {
        if (!userInput.contains("/from") || !userInput.contains("/to")) {
            throw new EricException("Event must have /from and /to identifiers!");

        }
        int firstSpace = userInput.indexOf(" ");
        String[] descriptions = userInput.substring(firstSpace + 1).split(" /from", -1);
        if (descriptions[0].trim().isEmpty()) {
            throw new EricException("Event description is empty!");
        }
        String[] dateParts = descriptions[1].split(" /to", -1);
        if (dateParts.length < 2 || dateParts[0].trim().isBlank() || dateParts[1].trim().isBlank()) {
            throw new EricException("Event missing values after /from and /to!");
        }
        Task t = new Event(descriptions[0], dateParts[0].trim(), dateParts[1].trim());
        tasks.add(t);
        ui.displayTaskAdded(t, tasks.size());
    }

    /**
     * Removes a task from the task list based on the task number given.
     *
     * @param userInput The task number to delete.
     * @throws EricException If the task number is out of bounds or invalid.
     */
    public static void deleteTask(String userInput) throws EricException {
        String[] descriptions = userInput.split(" ");
        if (descriptions.length < 2) {
            throw new EricException("Task number for deletion not specified!");
        }
        try {
            int index = Integer.parseInt(descriptions[1]) - 1;
            Task removed = tasks.remove(index);
            ui.displayDeleted(removed, tasks.size());
        } catch (IllegalArgumentException e) {
            throw new EricException("Enter a valid integer task number!");
        } catch(IndexOutOfBoundsException e) {
            throw new EricException("Task number specified not in range of tasks available!");
        }

    }

    /**
     * Displays task of a specific date that was specified.
     *
     * @param date The date string must be provided in the yyyy-MM-dd format.
     */
    public static void findDate(String date) throws EricException {
        String[] dateParts = date.split(" ");
        if (dateParts.length < 2) {
            throw new EricException("  Please specify a date you want to search for!");
        }
        try {
            LocalDate intDate = LocalDate.parse(dateParts[1].trim());
            ArrayList<Task> results = new ArrayList<>();
            for (Task t : tasks) {
                if (t instanceof Deadline && ((Deadline) t).by.equals(intDate)){
                    results.add(t);
                } else if (t instanceof Event
                        && (intDate.isAfter(((Event) t).from) || intDate.isEqual(((Event) t).from))
                        && (intDate.isBefore(((Event) t).to) || intDate.isEqual(((Event) t).to))){
                    results.add(t);
                }
            }
            ui.displaySearch(results, intDate.format(DateTimeFormatter.ofPattern("MMM d yyyy")));
        } catch (DateTimeParseException e) {
            throw new EricException("  Please use yyyy-MM-dd as the date format!");
        }
    }

    /**
     * Matches the user input to the appropriate task action.
     *
     * @param userInput The text entered by the user.
     * @throws EricException If the command or its relevant parameters are invalid.
     */
    public static void command(String userInput) throws EricException{
        if (userInput.startsWith("todo")){
            addTodo(userInput);
        } else if (userInput.startsWith("deadline")){
            addDeadline(userInput);
        } else if (userInput.startsWith("event")){
            addEvent(userInput);
        } else if (userInput.startsWith("mark") || userInput.startsWith("unmark")) {
            setMarkUnmarked(userInput);
        } else if (userInput.equals("list")) {
            listTask();
        } else if (userInput.startsWith("delete")) {
            deleteTask(userInput);
        } else if (userInput.startsWith("find")) {
            findDate(userInput);
        }
        else {
            throw new EricException("Sorry, I can't seem to handle your request!");
        }
    }

}



