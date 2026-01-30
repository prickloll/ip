package eric;
import eric.parser.Parser;
import eric.repository.Repository;
import eric.task.TaskList;
import eric.ui.Ui;

/**
 * Main entry point for Eric chatbot.
 */
public class Eric {
    private static Repository repo;
    private static TaskList tasks;
    private static Ui ui;

    /**
     * Initialises the bot and load existing tasks if any.
     *
     * @param filePath Path to the text file.
     */
    public Eric(String filePath) {
        ui = new Ui();
        repo = new Repository(filePath);
        try {
            tasks = new TaskList(repo.load());
        } catch (EricException e) {
            ui.emptyListIndi();
            tasks = new TaskList();
        }
    }

    /**
     * Starts the main execution for Eric
     */
    public void run() {
        ui.greeting();
        boolean isExit = false;
        while (!isExit) {

            try {
                String userInput = ui.readInput();
                eric.command.Command c = Parser.parse(userInput);
                c.execute(tasks, ui, repo);
                isExit = c.isExit();

            } catch (EricException e) {
                ui.errorMsg(e.getMessage());
            }

        }
    }
    /**
     * Starts the chatbot and handles the main execution loop.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        new Eric("./data/Eric.txt").run();
    }

}



