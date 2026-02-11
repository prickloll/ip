package eric;
import eric.command.Command;
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

    private String startMessage = "";

    public Eric() {
        this("./data/Eric.txt");
    }
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
            startMessage = ui.emptyListIndi();
            tasks = new TaskList();
        }

        assert tasks != null : "Task List should be initialised even if loading from the .txt file fails.";
        assert ui != null : "Ui should be initialised.";
        assert repo != null : "Repository should be initialised.";
    }

    /**
     * Generates a response based on user input.
     *
     * @param input The input given by the user.
     * @return The response to the user input.
     */
    public String getResponse(String input) {
        assert input != null : "Input to eric should not be null";
        try {
            Command c = Parser.parse(input);
            return c.execute(tasks, ui, repo);

        } catch (EricException e) {
            return ui.errorMsg(e.getMessage());
        }
    }

    /**
     * Generates the greeting message for the bot.
     *
     * @return The greeting message.
     */
    public String getGreeting() {
        if (startMessage.isEmpty()) {
            return ui.greeting();
        } else {
            return ui.greeting() + "\nNote: " + startMessage;
        }
    }
    public static void main(String[] args) {
        System.out.println("Hello!");
    }

}



