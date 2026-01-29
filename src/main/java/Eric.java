import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Eric {
    private static Repository repo  = new Repository("./data/Eric.txt");
    private static TaskList tasks;
    private static Ui ui;

    /**
     * Starts the chatbot and handles the main execution loop.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        ui = new Ui();
        try {
            tasks = new TaskList(repo.load());
        } catch (EricException e) {
            ui.errorMsg("  " + e.getMessage());
            tasks = new TaskList();
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
                repo.save(tasks.getEveryTask());
            } catch (EricException e) {
                ui.linebreak();
                ui.errorMsg(e.getMessage());
                ui.linebreak();
            }

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
            ui.displayTaskAdded(tasks.deleteTask(userInput), tasks.getSize());
        } else if (userInput.startsWith("find")) {
            ArrayList<Task> results = tasks.findTasksByDate(userInput);
            ui.displaySearch(results, userInput.split(" ")[1]);
        }
        else {
            throw new EricException("Sorry, I can't seem to handle your request!");
        }
    }

}



