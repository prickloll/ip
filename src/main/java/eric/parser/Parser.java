package eric.parser;
import eric.EricException;
import eric.command.AddDeadlineCommand;
import eric.command.AddEventCommand;
import eric.command.AddTodoCommand;
import eric.command.Command;
import eric.command.DeleteCommand;
import eric.command.ExitCommand;
import eric.command.FindCommand;
import eric.command.FindDateCommand;
import eric.command.ListCommand;
import eric.command.MarkCommand;

/**
 * Main logic for taking care of user inputs and calls the appropriate action.
 */
public class Parser {
    /**
     * Takes in the user input and execute corresponding commands.
     * @param userInput The string given by the user.
     * @return Command Returns the command object corresponding to the user input.
     * @throws EricException If the commands or parameters are invalid.
     */
    public static Command parse(String userInput) throws EricException {
        assert userInput != null : "User input passed to parser cannot be null";
        Command command;
        if (userInput.equals("bye")) {
            command = new ExitCommand();
        } else if (userInput.startsWith("todo")) {
            command = new AddTodoCommand(userInput);
        } else if (userInput.startsWith("deadline")) {
            command = new AddDeadlineCommand(userInput);
        } else if (userInput.startsWith("event")) {
            command = new AddEventCommand(userInput);
        } else if (userInput.startsWith("mark") || userInput.startsWith("unmark")) {
            command = new MarkCommand(userInput);
        } else if (userInput.equals("list")) {
            command = new ListCommand();
        } else if (userInput.startsWith("delete")) {
            command = new DeleteCommand(userInput);
        } else if (userInput.startsWith("finddate")) {
            command = new FindDateCommand(userInput);
        } else if (userInput.startsWith("find")) {
            command = new FindCommand(userInput);
        } else {
            throw new EricException("Sorry, I can't seem to handle your request!");
        }
        assert command != null : "Parser failed to create a Command object for valid input";
        return command;
    }
}
