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
     * Represents valid command strings supported by Eric
     */
    private enum CommandType {
        BYE, TODO, DEADLINE, EVENT, MARK, UNMARK, LIST, DELETE, FINDDATE, FIND, UNKNOWN
    }
    /**
     * Takes in the user input and execute corresponding commands.
     * @param userInput The string given by the user.
     * @return Command Returns the command object corresponding to the user input.
     * @throws EricException If the commands or parameters are invalid.
     */
    public static Command parse(String userInput) throws EricException {
        CommandType command = getCommandType(userInput);
        switch (command) {
        case BYE:
            return new ExitCommand();
        case TODO:
            return new AddTodoCommand(userInput);
        case DEADLINE:
            return new AddDeadlineCommand(userInput);
        case EVENT:
            return new AddEventCommand(userInput);
        case MARK:
        case UNMARK:
            return new MarkCommand(userInput);
        case LIST:
            return new ListCommand();
        case DELETE:
            return new DeleteCommand(userInput);
        case FINDDATE:
            return new FindDateCommand(userInput);
        case FIND:
            return configureFind(userInput);
        case UNKNOWN:
        default:
            throw new EricException("Sorry, I can't seem to handle your request!");
        }
    }

    /**
     * Determine the command from user input.
     *
     * @param userInput The command the user entered.
     * @return The type of command associated to the user input.
     */
    private static CommandType getCommandType(String userInput) {
        String commandWord = userInput.split(" ")[0].toLowerCase();
        try {
            return CommandType.valueOf(commandWord.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CommandType.UNKNOWN;
        }
    }

    private static Command configureFind(String input) throws EricException {
        //for strict searching
        boolean isStrict = input.contains("/all");

        //Detect filter for different events
        boolean isToDo = input.contains("/todo");
        boolean isEvent = input.contains("/event");
        boolean isDeadLine = input.contains("/deadline");
        boolean isSorted = input.contains("/sort");

        //Remove all flags from input
        String cleanInput = input.replace("find", "")
                .replace("\\s+/all\\b", "")
                .replace("\\s+/todo\\b", "")
                .replace("\\s+/deadline\\b", "")
                .replace("\\s+/event\\b", "")
                .replace("\\s+/sort\\b", "")
                .trim();

        if (cleanInput.isEmpty()) {
            throw new EricException("Please provide a keyword to search for against the task list.");
        }

        String[] keywords = cleanInput.split("\\s+");
        return new FindCommand(keywords, isStrict, isToDo, isEvent, isDeadLine, isSorted);


    }
}
