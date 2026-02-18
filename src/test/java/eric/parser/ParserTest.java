package eric.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

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
 * Tests for Parser.parse() method covering various command types and edge cases.
 */
public class ParserTest {

    @Test
    public void parse_todoCommand_success() throws EricException {
        Parser parser = new Parser();
        Command cmd = parser.parse("todo buy groceries");
        assertInstanceOf(AddTodoCommand.class, cmd);
    }

    @Test
    public void parse_deadlineCommand_success() throws EricException {
        Parser parser = new Parser();
        Command cmd = parser.parse("deadline submit assignment /by 2026-02-20");
        assertInstanceOf(AddDeadlineCommand.class, cmd);
    }

    @Test
    public void parse_eventCommand_success() throws EricException {
        Parser parser = new Parser();
        Command cmd = parser.parse("event meeting /from 2026-02-20 /to 2026-02-21");
        assertInstanceOf(AddEventCommand.class, cmd);
    }

    @Test
    public void parse_markCommand_success() throws EricException {
        Parser parser = new Parser();
        Command cmd = parser.parse("mark 1");
        assertInstanceOf(MarkCommand.class, cmd);
    }

    @Test
    public void parse_unmarkCommand_success() throws EricException {
        Parser parser = new Parser();
        Command cmd = parser.parse("unmark 1");
        assertInstanceOf(MarkCommand.class, cmd);
    }

    @Test
    public void parse_deleteCommand_success() throws EricException {
        Parser parser = new Parser();
        Command cmd = parser.parse("delete 1");
        assertInstanceOf(DeleteCommand.class, cmd);
    }

    @Test
    public void parse_listCommand_success() throws EricException {
        Parser parser = new Parser();
        Command cmd = parser.parse("list");
        assertInstanceOf(ListCommand.class, cmd);
    }

    @Test
    public void parse_byeCommand_success() throws EricException {
        Parser parser = new Parser();
        Command cmd = parser.parse("bye");
        assertInstanceOf(ExitCommand.class, cmd);
    }

    @Test
    public void parse_findCommand_success() throws EricException {
        Parser parser = new Parser();
        Command cmd = parser.parse("find homework");
        assertInstanceOf(FindCommand.class, cmd);
    }

    @Test
    public void parse_findCommandWithFlags_success() throws EricException {
        Parser parser = new Parser();
        Command cmd = parser.parse("find homework /todo /sort");
        assertInstanceOf(FindCommand.class, cmd);
    }

    @Test
    public void parse_findCommandWithDate_success() throws EricException {
        Parser parser = new Parser();
        Command cmd = parser.parse("find /date 2026-02-20");
        assertInstanceOf(FindCommand.class, cmd);
    }

    @Test
    public void parse_invalidCommand_exceptionThrown() {
        Parser parser = new Parser();
        EricException exception = assertThrows(EricException.class, () -> {
            parser.parse("invalid command here");
        });
        assertEquals("Sorry, I can't seem to handle your request!", exception.getMessage());
    }

    @Test
    public void parse_unknownCommand_exceptionThrown() {
        Parser parser = new Parser();
        EricException exception = assertThrows(EricException.class, () -> {
            parser.parse("xyz something");
        });
        assertEquals("Sorry, I can't seem to handle your request!", exception.getMessage());
    }

    @Test
    public void parse_emptyInput_exceptionThrown() {
        Parser parser = new Parser();
        EricException exception = assertThrows(EricException.class, () -> {
            parser.parse("");
        });
        assertEquals("Sorry, I can't seem to handle your request!", exception.getMessage());
    }

    @Test
    public void parse_caseInsensitive_success() throws EricException {
        Parser parser = new Parser();
        Command cmd1 = parser.parse("TODO buy milk");
        assertInstanceOf(AddTodoCommand.class, cmd1);

        Command cmd2 = parser.parse("LIST");
        assertInstanceOf(ListCommand.class, cmd2);

        Command cmd3 = parser.parse("BYE");
        assertInstanceOf(ExitCommand.class, cmd3);
    }

    @Test
    public void parse_findCommandWithMultipleFlags_success() throws EricException {
        Parser parser = new Parser();
        Command cmd = parser.parse("find project /deadline /sort /all");
        assertInstanceOf(FindCommand.class, cmd);
    }

    @Test
    public void parse_multipleParserInstances_independent() throws EricException {
        Parser parser1 = new Parser();
        Parser parser2 = new Parser();

        Command cmd1 = parser1.parse("todo task 1");
        Command cmd2 = parser2.parse("todo task 2");

        assertInstanceOf(AddTodoCommand.class, cmd1);
        assertInstanceOf(AddTodoCommand.class, cmd2);
    }

    @Test
    public void parse_findWithInvalidDate_exceptionThrown() {
        Parser parser = new Parser();
        EricException exception = assertThrows(EricException.class, () -> {
            parser.parse("find /date 2026-13-50");
        });
        assertEquals("Date to search for is not in the correct format!", exception.getMessage());
    }

    @Test
    public void parse_findWithValidDate_success() throws EricException {
        Parser parser = new Parser();
        Command cmd = parser.parse("find /date 2026-02-20");
        assertInstanceOf(FindCommand.class, cmd);
    }

    @Test
    public void parse_findWithoutKeyword_exceptionThrown() {
        Parser parser = new Parser();
        EricException exception = assertThrows(EricException.class, () -> {
            parser.parse("find /todo");
        });
        assertEquals("Please provide a keyword/date to search for against the task list.",
                exception.getMessage());
    }
}

