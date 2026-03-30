package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class ParserTest {

    private Parser parser;

    @BeforeEach
    public void setUp() {
        parser = new Parser();
    }

    /*
     * Test for exit command
     */
    @Test
    public void parseExitCommand_parsedCorrectly() throws ExpensiveLehException {
        final String input = "exit\n";
        final Command result = parse(input);
        assertInstanceOf(ExitCommand.class, result);
    }

    /*
     * Test for list command
     */
    @Test
    public void parseListCommand_parsedCorrectly() throws ExpensiveLehException {
        final String input = "list expenses\n";
        final Command result = parse(input);
        assertInstanceOf(ListCommand.class, result);
    }

    /*
     * Test for help command
     */
    @Test
    public void parseHelpCommand_parsedCorrectly() throws ExpensiveLehException {
        final String input = "help\n";
        final Command result = parse(input);
        assertInstanceOf(HelpCommand.class, result);
    }

    /*
     * Tests for budget command
     */
    @Test
    public void parseBudgetCommand_validAmount_returnsBudgetCommand() throws ExpensiveLehException {
        final String input = "budget 67\n";
        final Command result = parse(input);
        assertInstanceOf(BudgetCommand.class, result);
    }

    @Test
    public void parseBudgetCommand_invalidAmount_throwsException() {
        final String input = "budget invalid\n";
        assertThrows(ExpensiveLehException.class, () -> parse(input));
    }

    /*
     * Tests for add command
     */
    @Test
    public void parseAddCommand_validArgs_returnsAddCommand() throws ExpensiveLehException {
        final String input = "add c/food n/burger a/5.50\n";
        final Command result = parse(input);
        assertInstanceOf(AddCommand.class, result);
    }

    @Test
    public void parseAddCommand_missingName_throwsException() {
        final String input = "add c/food a/5.50\n";
        assertThrows(ExpensiveLehException.class, () -> parse(input));
    }

    /*
     * Tests for delete command
     */
    @Test
    public void parseDeleteExpenseCommand_validIndex() throws ExpensiveLehException {
        final String input = "delete expense 1\n";
        final Command result = parse(input);
        assertInstanceOf(DeleteCommand.class, result);
    }

    @Test
    public void parseDeleteBookmarkCommand_validIndex() throws ExpensiveLehException {
        final String input = "delete bookmark 1\n";
        final Command result = parse(input);
        assertInstanceOf(DeleteCommand.class, result);
    }

    @Test
    public void parseDeleteCommand_invalidIndex_throwsException() {
        final String input = "delete X\n";
        assertThrows(ExpensiveLehException.class, () -> parse(input));
    }

    /*
     * Test for edit command
     */
    @Test
    public void parseEditCommand_validArgs_returnsEditCommand() throws ExpensiveLehException {
        final String input = "edit 1 n/coffee\n";
        final Command result = parse(input);
        assertInstanceOf(EditCommand.class, result);
    }

    /*
     * Test for unknown command
     */
    @Test
    public void parse_unknownCommand_throwsException() {
        final String input = "INVALID INPUT\n";
        assertThrows(ExpensiveLehException.class, () -> parse(input));
    }

    /*
     * Utility method
     */
    private Command parse(String input) throws ExpensiveLehException {
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
        parser = new Parser();
        return parser.readCommand();
    }
}
