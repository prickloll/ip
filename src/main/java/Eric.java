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
        boolean exit = false;
        while (!exit) {
            String userInput = scanner.nextLine();
            try {
                exit = Parser.parse(userInput, tasks, ui, repo);

            } catch (EricException e) {
                ui.linebreak();
                ui.errorMsg(e.getMessage());
                ui.linebreak();
            }

        }
    }

}



