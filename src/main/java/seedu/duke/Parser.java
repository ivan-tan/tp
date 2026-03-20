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
        if (!scanner.hasNextLine()) {
            return new ExitCommand();
        }
        String line = scanner.nextLine().trim();
        String[] partsBySpace = line.split("\\s+");
        String command = partsBySpace[0].toLowerCase();

        switch (command) {
        case "exit":
            return new ExitCommand();

        case "budget":
            try {
                if (partsBySpace.length < 2) {
                    throw new ExpensiveLehException("Please provide a budget amount!");
                }
                double budgetAmount = Double.parseDouble(partsBySpace[1]);
                if (budgetAmount <= 0) {
                    throw new ExpensiveLehException("Budget must be a positive number!");
                }
                return new BudgetCommand(budgetAmount);
            } catch (NumberFormatException e) {
                throw new ExpensiveLehException("Please enter a valid budget amount!");
            }

        case "add":
            return parseAddCommand(line);

        case "edit":
            return parseEditCommand(line);

        case "delete":
            try {
                int deleteIndex = Integer.parseInt(partsBySpace[1]) - 1;
                return new DeleteCommand(deleteIndex);
            } catch (IndexOutOfBoundsException e) {
                throw new ExpensiveLehException("Please enter a valid integer from the expense list!");
            } catch (NumberFormatException e) {
                throw new ExpensiveLehException("Please enter a valid integer!");
            }

        case "list":
            return new ListCommand();

        case "help":
            return new HelpCommand();

        default:
            throw new ExpensiveLehException("Unknown command. Please use enter 'help' for the user guide.");
        }
    }

    private Command parseAddCommand(String line) throws ExpensiveLehException {
        String category = null;
        String name = null;
        Double amount = null;
        LocalDate date = LocalDate.now();

        try {
            String[] parts = line.split("\\s+");
            for (int i = 1; i < parts.length; i++) {
                String part = parts[i];
                if (part.startsWith("c/")) {
                    category = part.substring(2);
                } else if (part.startsWith("n/")) {
                    StringBuilder nameParts = new StringBuilder(part.substring(2));

                    while (i + 1 < parts.length && !parts[i+1].startsWith("a/")) {
                        nameParts.append(" ").append(parts[++i]);
                    }
                    name = nameParts.toString();
                } else if (part.startsWith("a/")) {
                    amount = Double.parseDouble(part.substring(2));
                } else if (part.startsWith("d/")) {
                    date = LocalDate.parse(part.substring(2),
                            java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                }
            }

            if (name == null || name.trim().isEmpty()) {
                throw new ExpensiveLehException(
                        "Expense name cannot be empty. Usage: add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]");
            }
            if (amount == null) {
                throw new ExpensiveLehException(
                        "Expense amount is required. Usage: add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]");
            }
            if (amount <= 0) {
                throw new ExpensiveLehException("Expense amount must be positive.");
            }
            if (category == null || category.trim().isEmpty()) {
                category = "Others";
            }

            Expense expense;
            switch (category.toLowerCase()) {
            case "food":
                expense = new Food(name, amount, date);
                break;
            case "transport":
                expense = new Transport(name, amount, date);
                break;
            case "groceries":
                expense = new Groceries(name, amount, date);
                break;
            default:
                expense = new Others(name, amount, date);
            }

            return new AddCommand(expense);

        } catch (java.time.format.DateTimeParseException e) {
            throw new ExpensiveLehException("Invalid date format. Please use DD-MM-YYYY (e.g., 13-03-2026).");
        } catch (NumberFormatException e) {
            throw new ExpensiveLehException("Invalid amount format. Please enter a valid number.");
        } catch (Exception e) {
            throw new ExpensiveLehException(
                    "Invalid add command format. Usage: add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]");
        }
    }

    private Command parseEditCommand(String line) throws ExpensiveLehException {
        String[] parts = line.split("\\s+");

        if (parts.length < 2) {
            throw new ExpensiveLehException("Please provide an expense index to edit!");
        }

        int editIndex;
        try {
            editIndex = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new ExpensiveLehException("Please enter a valid integer for the expense index!");
        }

        String category = null;
        String name = null;
        Double amount = null;
        LocalDate date = null;

        try {
            for (int i = 2; i < parts.length; i++) {
                String part = parts[i];
                if (part.startsWith("c/")) {
                    category = part.substring(2);
                } else if (part.startsWith("n/")) {
                    StringBuilder nameParts = new StringBuilder(part.substring(2));

                    while (i + 1 < parts.length && !parts[i+1].startsWith("a/")) {
                        nameParts.append(" ").append(parts[++i]);
                    }
                    name = nameParts.toString();
                } else if (part.startsWith("a/")) {
                    amount = Double.parseDouble(part.substring(2));
                } else if (part.startsWith("d/")) {
                    date = LocalDate.parse(part.substring(2),
                            java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                }
            }

            if (category == null && name == null && amount == null && date == null) {
                throw new ExpensiveLehException(
                        "Please specify at least one field to edit: c/CATEGORY, n/NAME, a/AMOUNT, or d/DD-MM-YYYY");
            }

            if (amount != null && amount <= 0) {
                throw new ExpensiveLehException("Expense amount must be positive.");
            }

            return new EditCommand(editIndex, category, name, amount, date);

        } catch (java.time.format.DateTimeParseException e) {
            throw new ExpensiveLehException("Invalid date format. Please use DD-MM-YYYY (e.g., 13-03-2026).");
        } catch (NumberFormatException e) {
            throw new ExpensiveLehException("Invalid amount format. Please enter a valid number.");
        } catch (Exception e) {
            throw new ExpensiveLehException(
                    "Invalid edit command format. Usage: edit INDEX [c/CATEGORY] [n/NAME] [a/AMOUNT] [d/DD-MM-YYYY]");
        }
    }
}
