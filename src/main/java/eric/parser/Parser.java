package eric.parser;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import eric.EricException;
import eric.command.AddDeadlineCommand;
import eric.command.AddEventCommand;
import eric.command.AddTodoCommand;
import eric.command.Command;
import eric.command.DeleteCommand;
import eric.command.ExitCommand;
import eric.command.FindCommand;
import eric.command.ListCommand;
import eric.command.MarkCommand;

/**
 * Represents main logic for taking care of user inputs and calls the appropriate action.
 */
public class Parser {

    /**
     * Represents valid command strings supported by Eric
     */
    private enum CommandType {
        BYE, TODO, DEADLINE, EVENT, MARK, UNMARK, LIST, DELETE, FINDDATE, FIND, UNKNOWN
    }

    // Find command options
    private boolean isStrict;
    private boolean isToDo;
    private boolean isEvent;
    private boolean isDeadLine;
    private boolean isSorted;
    private LocalDate searchDate;

    /**
     * Initialises a Parser with default find options.
     */
    public Parser() {
        resetFindOptions();
    }

    /**
     * Resets all find options to default state.
     */
    private void resetFindOptions() {
        this.isStrict = false;
        this.isToDo = false;
        this.isEvent = false;
        this.isDeadLine = false;
        this.isSorted = false;
        this.searchDate = null;
    }

    /**
     * Takes in the user input and execute corresponding commands.
     *
     * @param userInput The string given by the user.
     * @return Command Returns the command object corresponding to the user input.
     * @throws EricException If the commands or parameters are invalid.
     */
    public Command parse(String userInput) throws EricException {
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
        case FIND:
            return configureFind(userInput);
        case UNKNOWN:
        default:
            throw new EricException("Sorry, I can't seem to handle your request!");
        }
    }

    /**
     * Determines the command from user input.
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

    /**
     * Creates a find command object based on user input and flags.
     *
     * @param input The user input.
     * @return The find command object.
     * @throws EricException The keyword or date to search for is missing.
     */
    private Command configureFind(String input) throws EricException {
        extractFindOptions(input);
        String[] keywords = validateAndExtractKeywords(input);

        return new FindCommand(keywords, searchDate,
                              isStrict, isToDo,
                              isDeadLine, isEvent,
                              isSorted);
    }

    /**
     * Extracts and stores find options from user input.
     *
     * @param input The user input containing flags.
     * @throws EricException If date format is invalid.
     */
    private void extractFindOptions(String input) throws EricException {
        resetFindOptions();

        this.isStrict = containsFlag(input, "/all");
        this.isToDo = containsFlag(input, "/todo");
        this.isEvent = containsFlag(input, "/event");
        this.isDeadLine = containsFlag(input, "/deadline");
        this.isSorted = containsFlag(input, "/sort");

        if (containsFlag(input, "/date")) {
            this.searchDate = parseDateFromInput(input);
        }
    }

    /**
     * Checks if input contains the specified flag.
     *
     * @param input The input string to check.
     * @param flag The flag to look for.
     * @return true if flag is present at word boundaries.
     */
    private boolean containsFlag(String input, String flag) {
        return input.matches(".*\\s+" + flag + "\\b.*") || input.matches("^" + flag + "\\b.*");
    }

    /**
     * Validates and extracts keywords from user input.
     *
     * @param input The user input.
     * @return Array of search keywords.
     * @throws EricException If no keywords or date provided.
     */
    private String[] validateAndExtractKeywords(String input) throws EricException {
        String cleanInput = cleanInputFlags(input);

        // Allow empty keywords if a date is provided
        if (cleanInput.isEmpty() && searchDate == null) {
            throw new EricException("Please provide a keyword/date to search for against the task list.");
        }

        // Return keywords if present, otherwise return empty array
        if (cleanInput.isEmpty()) {
            return new String[] { "" };
        }

        return cleanInput.split("\\s+");
    }

    /**
     * Takes in a user input and cleans it of the flags.
     *
     * @param input The input to clean.
     * @return The user input without the flags.
     */
    private static String cleanInputFlags(String input) {
        return input.replaceFirst("^find\\b", "")
                .replaceAll("/date\\s+\\S+", "")
                .replaceAll("\\s+/all\\b", "")
                .replaceAll("\\s+/todo\\b", "")
                .replaceAll("\\s+/deadline\\b", "")
                .replaceAll("\\s+/event\\b", "")
                .replaceAll("\\s+/sort\\b", "")
                .trim();
    }

    /**
     * Extracts the date from the user input.
     *
     * @param input The user input to extract the date from.
     * @return The LocalDate object for the date string.
     * @throws EricException The date string given is missing or in the wrong format.
     */
    private static LocalDate parseDateFromInput(String input) throws EricException {
        String[] parts = input.split("/date");

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new EricException("Please provide a date to search after the /date flag!");
        }

        String afterDateFlag = parts[1].trim();
        String dateString = afterDateFlag.split("\\s+")[0];

        // Check if user might have typo'd the flag (e.g., /dateline instead of /deadline)
        if (dateString.startsWith("line") || dateString.startsWith("event")
                || dateString.startsWith("todo")) {
            throw new EricException("Invalid flag detected. Did you mean /deadline, /event, /todo, or /all?");
        }

        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new EricException("Date to search for is not in the correct format!");
        }
    }
}
