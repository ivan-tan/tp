package seedu.duke;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Parses user input and converts it into executable commands.
 */
public class Parser {

    private Scanner scanner = new Scanner(System.in);

    public Command readCommand() throws ExpensiveLehException {
        System.out.print("> ");
        String line =scanner.nextLine().trim();
        String[] partsBySpace = line.split("\\s+");
        String command = partsBySpace[0].toLowerCase();

        switch (command) {
        case "exit":
            System.out.println("Bye!");
            return new ExitCommand();

        case "add":
            System.out.println("Added!");
            return new AddCommand(new Others("placeholder", 0.0, LocalDate.now()));

        case "edit":
            try {
                int editIndex = Integer.parseInt(partsBySpace[1]) - 1;
                System.out.println("Edited!");
                return new EditCommand(editIndex, null, null, null, null);
            } catch (IndexOutOfBoundsException e) {
                throw new ExpensiveLehException("Please enter a valid integer from the expense list!");
            } catch (NumberFormatException e) {
                throw new ExpensiveLehException("Please enter a valid integer!");
            }

        case "delete":
            try {
                int deleteIndex = Integer.parseInt(partsBySpace[1]) - 1;
                System.out.println("Deleted!");
                return new DeleteCommand(deleteIndex);
            } catch (IndexOutOfBoundsException e) {
                throw new ExpensiveLehException("Please enter a valid integer from the expense list!");
            } catch (NumberFormatException e) {
                throw new ExpensiveLehException("Please enter a valid integer!");
            }

        case "list":
            System.out.println("Listed!");
            return new ListCommand();

        default:
            throw new ExpensiveLehException("Unknown command. Please use enter 'help' for the user guide.");
        }
    }
}