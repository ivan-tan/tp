package seedu.duke;

import loans.Loan;

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
                    throw new ExpensiveLehException(
                            "Please provide a budget amount or use 'budget c/CATEGORY a/AMOUNT'!");
                }

                // Check if it's a category budget command
                if (line.contains("c/") && line.contains("a/")) {
                    return parseBudgetCategoryCommand(line);
                }

                // Default to global budget command
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
                if (partsBySpace.length < 3) {
                    throw new ExpensiveLehException("Usage: delete expense INDEX, delete bookmark INDEX, " +
                            "delete loan INDEX");
                }

                String type = partsBySpace[1];
                int deleteIndex = Integer.parseInt(partsBySpace[2]) - 1;

                if (type.equals("expense")) {
                    return new DeleteCommand(deleteIndex, "expense");
                } else if (type.equals("bookmark")) {
                    return new DeleteCommand(deleteIndex, "bookmark");
                } else if (type.equals("loan")) {
                    return new DeleteCommand(deleteIndex, "loan");
                } else {
                    throw new ExpensiveLehException("Invalid delete type. Use 'expense' or 'bookmark' or 'loan'");
                }

            } catch (IndexOutOfBoundsException e) {
                throw new ExpensiveLehException("Please enter a valid integer from the specified list!");
            } catch (NumberFormatException e) {
                throw new ExpensiveLehException("Please enter a valid integer!");
            }

        case "bookmark":
            try {
                int bookmarkIndex = Integer.parseInt(partsBySpace[1]) - 1;
                return new BookmarkCommand(bookmarkIndex);
            } catch (IndexOutOfBoundsException e) {
                throw new ExpensiveLehException("Please enter a valid integer from the expense list!");
            } catch (NumberFormatException e) {
                throw new ExpensiveLehException("Please enter a valid integer!");
            }

        case "list":
            if (partsBySpace.length > 1 && partsBySpace[1].equalsIgnoreCase("budgets")) {
                return new ListBudgetsCommand();
            } else if (partsBySpace.length > 1 && partsBySpace[1].equalsIgnoreCase("bookmarks")) {
                return new ListCommand("bookmarks");
            } else if (partsBySpace.length > 1 && partsBySpace[1].equalsIgnoreCase("loans")) {
                return new ListCommand("loans");
            } else if (partsBySpace.length > 1 && partsBySpace[1].equalsIgnoreCase("expenses")) {
                return new ListCommand("expenses");
            } else {
                throw new ExpensiveLehException("Usage: list expenses, bookmarks, loans or budgets");
            }

        case "search":
            try {
                if (partsBySpace.length < 2) {
                    throw new ExpensiveLehException("Please provide a keyword to search for!");
                }
                String keyword = line.substring(line.indexOf("search") + 6).trim();
                return new SearchCommand(keyword);
            } catch (Exception e) {
                throw new ExpensiveLehException("Search error: " + e.getMessage());
            }

        case "rank": // either ranks loans or ranks expenses
            if (partsBySpace.length > 1 && partsBySpace[1].equalsIgnoreCase("loans")) {
                return new RankCommand("Loan");
            }
            return new RankCommand("expense");

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

            if (parts.length > 1 && parts[1].equals("bookmark")) {
                if (parts.length < 3) {
                    throw new ExpensiveLehException("Please enter a bookmark index!");
                }
                try {
                    int bookmarkIndex = Integer.parseInt(parts[2]) - 1;
                    return new AddCommand(bookmarkIndex, "bookmark");
                } catch (NumberFormatException e) {
                    throw new ExpensiveLehException("Please enter a valid integer!");
                }
            }

            for (int i = 1; i < parts.length; i++) {
                String part = parts[i];
                if (part.startsWith("c/")) {
                    category = part.substring(2);
                } else if (part.startsWith("n/")) {
                    StringBuilder nameParts = new StringBuilder(part.substring(2));

                    while (i + 1 < parts.length && !parts[i + 1].startsWith("a/")) {
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
            case "loan":
                expense = new Loan(name, amount, date);
                return new AddCommand((Loan) expense, "loan");
            default:
                expense = new Others(name, amount, date);
            }

            return new AddCommand(expense, "expense");

        } catch (ExpensiveLehException e) {
            throw e;
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
            throw new ExpensiveLehException("Please provide an index to edit!");
        }

        boolean isLoan = false;
        int editIndex;
        int argStartIndex = 2; // Default start index for arguments

        // Check if the user is trying to edit a loan
        if (parts[1].equalsIgnoreCase("loan")) {
            isLoan = true;
            if (parts.length < 3) {
                throw new ExpensiveLehException("Please provide a loan index to edit!");
            }
            try {
                editIndex = Integer.parseInt(parts[2]) - 1;
                argStartIndex = 3; // Shift the loop start point since "loan" took up a spot
            } catch (NumberFormatException e) {
                throw new ExpensiveLehException("Please enter a valid integer for the loan index!");
            }
        } else {
            // Default expense behavior
            try {
                editIndex = Integer.parseInt(parts[1]) - 1;
            } catch (NumberFormatException e) {
                throw new ExpensiveLehException("Please enter a valid integer for the expense index!");
            }
        }

        String category = null;
        String name = null;
        Double amount = null;
        LocalDate date = null;

        try {
            for (int i = argStartIndex; i < parts.length; i++) {
                String part = parts[i];
                if (part.startsWith("c/")) {
                    category = part.substring(2);
                } else if (part.startsWith("n/")) {
                    StringBuilder nameParts = new StringBuilder(part.substring(2));

                    while (i + 1 < parts.length && !parts[i + 1].startsWith("a/")
                            && !parts[i + 1].startsWith("c/") && !parts[i + 1].startsWith("d/")) {
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
                throw new ExpensiveLehException("Amount must be positive.");
            }

            // Pass flag to EditCommand
            String type = isLoan ? "loan" : "expense";
            return new EditCommand(editIndex, category, name, amount, date, type);

        } catch (java.time.format.DateTimeParseException e) {
            throw new ExpensiveLehException("Invalid date format. Please use DD-MM-YYYY (e.g., 13-03-2026).");
        } catch (NumberFormatException e) {
            throw new ExpensiveLehException("Invalid amount format. Please enter a valid number.");
        } catch (Exception e) {
            throw new ExpensiveLehException(
                    "Invalid edit command format. Usage: " +
                            "edit [loan] INDEX [c/CATEGORY] [n/NAME] [a/AMOUNT] [d/DD-MM-YYYY]");
        }
    }

    private Command parseBudgetCategoryCommand(String line) throws ExpensiveLehException {
        String category = null;
        Double amount = null;

        try {
            String[] parts = line.split("\\s+");
            for (int i = 1; i < parts.length; i++) {
                String part = parts[i];
                if (part.startsWith("c/")) {
                    category = part.substring(2);
                } else if (part.startsWith("a/")) {
                    amount = Double.parseDouble(part.substring(2));
                }
            }

            if (category == null || category.trim().isEmpty()) {
                throw new ExpensiveLehException(
                        "Category is required. Usage: budget c/CATEGORY a/AMOUNT");
            }
            if (amount == null) {
                throw new ExpensiveLehException(
                        "Amount is required. Usage: budget c/CATEGORY a/AMOUNT");
            }
            if (amount <= 0) {
                throw new ExpensiveLehException("Budget amount must be positive.");
            }

            return new CategoryBudgetCommand(category, amount);

        } catch (NumberFormatException e) {
            throw new ExpensiveLehException("Invalid amount format. Please enter a valid number.");
        } catch (Exception e) {
            throw new ExpensiveLehException(
                    "Invalid budget category command format. Usage: budget c/CATEGORY a/AMOUNT");
        }
    }
}
