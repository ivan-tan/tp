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
        case "bye":
        case "quit":
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
                    throw new ExpensiveLehException("Missing details. Usage: "
                            + "delete expense INDEX, delete loan INDEX, or delete bookmark INDEX");
                }

                String type = partsBySpace[1];
                int deleteIndex = Integer.parseInt(partsBySpace[2]) - 1;

                if (type.equals("expense")) {
                    return new DeleteCommand(deleteIndex, "expense");
                } else if (type.equals("loan")) {
                    return new DeleteCommand(deleteIndex, "loan");
                } else if (type.equals("bookmark")) {
                    return new DeleteCommand(deleteIndex, "bookmark");
                } else {
                    throw new ExpensiveLehException("Invalid delete type. Use 'expense', 'loan', or 'bookmark'");
                }

            } catch (IndexOutOfBoundsException e) {
                throw new ExpensiveLehException("Please enter a valid integer from the list!");
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

        case "list": // either list budgets, list loans, or list expenses
            if (partsBySpace.length > 1 && partsBySpace[1].equalsIgnoreCase("budgets")) {
                return new ListBudgetsCommand();
            } else if (partsBySpace.length > 1 && partsBySpace[1].equalsIgnoreCase("loans")) {
                return new ListCommand("loans");
            } else if (partsBySpace.length > 1 && partsBySpace[1].equalsIgnoreCase("bookmarks")) {
                return new ListCommand("bookmarks");
            } else if (partsBySpace.length > 1 && partsBySpace[1].equalsIgnoreCase("expenses")) {
                return new ListCommand("expenses");
            } else {
                throw new ExpensiveLehException("List error! Please use: list expenses/budgets/loans/bookmarks");
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

        case "rank":
            if (partsBySpace.length < 2) {
                throw new ExpensiveLehException("Please specify what to rank! Example: rank expenses or rank loans");
            }

            String rankType = partsBySpace[1].toLowerCase();

            if (rankType.equals("expenses") || rankType.equals("expense")) {
                return new RankCommand("expense");
            } else if (rankType.equals("loans") || rankType.equals("loan")) {
                return new RankCommand("loan");
            } else {
                throw new ExpensiveLehException("Invalid rank category! Example: rank expenses OR rank loans");
            }

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

            if  (parts.length == 1) {
                throw new ExpensiveLehException("Missing details. Usage: "
                        + "add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]");
            }

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

            // Boolean trackers for duplicate flags
            // Boolean trackers for duplicate flags
            boolean hasCategory = false;
            boolean hasName = false;
            boolean hasAmount = false;
            boolean hasDate = false;

            for (int i = 1; i < parts.length; i++) {
                String part = parts[i];
                if (part.startsWith("c/")) {
                    if (hasCategory) {
                        throw new ExpensiveLehException("Duplicate category flag 'c/' found. "
                                + "Usage: add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]");
                    }
                    category = part.substring(2);
                    hasCategory = true;
                } else if (part.startsWith("n/")) {
                    if (hasName) {
                        throw new ExpensiveLehException("Duplicate name flag 'n/' found. "
                                + "Usage: add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]");
                    }
                    StringBuilder nameParts = new StringBuilder(part.substring(2));

                    while (i + 1 < parts.length && !parts[i + 1].startsWith("a/")
                            && !parts[i + 1].startsWith("c/") && !parts[i + 1].startsWith("d/")) {
                        nameParts.append(" ").append(parts[++i]);
                    }
                    name = nameParts.toString();
                    hasName = true;
                } else if (part.startsWith("a/")) {
                    if (hasAmount) {
                        throw new ExpensiveLehException("Duplicate amount flag 'a/' found. "
                                + "Usage: add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]");
                    }
                    amount = Double.parseDouble(part.substring(2));
                    hasAmount = true;
                } else if (part.startsWith("d/")) {
                    if (hasDate) {
                        throw new ExpensiveLehException("Duplicate date flag 'd/' found. "
                                + "Usage: add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]");
                    }
                    date = LocalDate.parse(part.substring(2),
                            java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    hasDate = true;
                }
            }

            // Date Bounds Check
            LocalDate today = LocalDate.now();
            LocalDate minDate = today.minusYears(100);
            LocalDate maxDate = today.plusYears(10);
            if (date.isBefore(minDate) || date.isAfter(maxDate)) {
                throw new ExpensiveLehException("Invalid Date! Please enter a date between "
                        + minDate.getYear() + " and " + maxDate.getYear() + ".");
            }

            // 1. Check Category first
            if (category == null || category.trim().isEmpty()) {
                throw new ExpensiveLehException(
                        "Valid CATEGORY is required. Usage: add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]");
            }

            // 2. Check Name second
            if (name == null || name.trim().isEmpty()) {
                throw new ExpensiveLehException(
                        "Valid NAME is required. Usage: add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]");
            }

            // 3. Check Amount third
            if (amount == null) {
                throw new ExpensiveLehException(
                        "AMOUNT is required. Usage: add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]");
            }
            if (amount <= 0) {
                throw new ExpensiveLehException("Expense amount must be positive!");
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
                return new AddCommand(expense, "loan");
            case "others":
                expense = new Others(name, amount, date);
                break;
            default:
                throw new ExpensiveLehException("category must be food, transport, groceries, others or loan");
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

        // Catch empty edit command
        if (parts.length == 1) {
            throw new ExpensiveLehException(
                    "Missing details. Usage: "
                    + "edit [loan/expense] INDEX [c/CATEGORY] [n/NAME] [a/AMOUNT] [d/DD-MM-YYYY]");
        }

        boolean isLoan = false;
        int editIndex;
        int argStartIndex = 2; // Default start index for arguments

        // Explicitly handle "loan", "expense", or just an index
        try {
            if (parts[1].equalsIgnoreCase("loan")) {
                isLoan = true;
                if (parts.length < 3) {
                    throw new ExpensiveLehException("Please provide a loan index to edit!");
                }
                editIndex = Integer.parseInt(parts[2]) - 1;
                argStartIndex = 3; // Shift loop start point

            } else if (parts[1].equalsIgnoreCase("expense")) {
                isLoan = false;
                if (parts.length < 3) {
                    throw new ExpensiveLehException("Please provide an expense index to edit!");
                }
                editIndex = Integer.parseInt(parts[2]) - 1;
                argStartIndex = 3; // Shift loop start point

            } else {
                // Default behavior: user just typed "edit 1" (no loan/expense keyword), default will be expense
                isLoan = false;
                editIndex = Integer.parseInt(parts[1]) - 1;
                argStartIndex = 2;
            }
        } catch (NumberFormatException e) {
            throw new ExpensiveLehException("Please enter a valid integer for the index!");
        }

        String category = null;
        String name = null;
        Double amount = null;
        LocalDate date = null;

        try {
            // Boolean trackers for duplicate flags
            boolean hasCategory = false;
            boolean hasName = false;
            boolean hasAmount = false;
            boolean hasDate = false;

            for (int i = 1; i < parts.length; i++) {
                String part = parts[i];
                if (part.startsWith("c/")) {
                    if (hasCategory) {
                        throw new ExpensiveLehException("Duplicate category flag 'c/' found. "
                                + "Usage: add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]");
                    }
                    category = part.substring(2);
                    hasCategory = true;
                } else if (part.startsWith("n/")) {
                    if (hasName) {
                        throw new ExpensiveLehException("Duplicate name flag 'n/' found. "
                                + "Usage: add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]");
                    }
                    StringBuilder nameParts = new StringBuilder(part.substring(2));

                    while (i + 1 < parts.length && !parts[i + 1].startsWith("a/")
                            && !parts[i + 1].startsWith("c/") && !parts[i + 1].startsWith("d/")) {
                        nameParts.append(" ").append(parts[++i]);
                    }
                    name = nameParts.toString();
                    hasName = true;
                } else if (part.startsWith("a/")) {
                    if (hasAmount) {
                        throw new ExpensiveLehException("Duplicate amount flag 'a/' found. "
                                + "Usage: add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]");
                    }
                    amount = Double.parseDouble(part.substring(2));
                    hasAmount = true;
                } else if (part.startsWith("d/")) {
                    if (hasDate) {
                        throw new ExpensiveLehException("Duplicate date flag 'd/' found. "
                                + "Usage: add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]");
                    }
                    date = LocalDate.parse(part.substring(2),
                            java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    hasDate = true;
                }
            }

            // Date Bounds Check (Only check if date is being edited)
            if (date != null) {
                LocalDate today = LocalDate.now();
                LocalDate minDate = today.minusYears(100);
                LocalDate maxDate = today.plusYears(1);
                if (date.isBefore(minDate) || date.isAfter(maxDate)) {
                    throw new ExpensiveLehException("Invalid Date! Please enter a date between "
                            + minDate.getYear() + " and " + maxDate.getYear() + ".");
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

            // BUG FIX 3: Catch custom exceptions first, keep generic Exception as fallback
        } catch (ExpensiveLehException e) {
            throw e;
        } catch (java.time.format.DateTimeParseException e) {
            throw new ExpensiveLehException("Invalid date format. Please use DD-MM-YYYY (e.g., 13-03-2026).");
        } catch (NumberFormatException e) {
            throw new ExpensiveLehException("Invalid amount format. Please enter a valid number.");
        } catch (Exception e) {
            throw new ExpensiveLehException("Invalid edit command format. Usage: " +
                            "edit [loan/expense] INDEX [c/CATEGORY] [n/NAME] [a/AMOUNT] [d/DD-MM-YYYY]");
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
            if (category.equalsIgnoreCase("loan")) {
                throw new ExpensiveLehException(
                        "Loans cannot have a budget. " +
                                "Only expenses (food, groceries, transport, others) can have budgets.");
            }
            if (amount == null) {
                throw new ExpensiveLehException(
                        "Amount is required. Usage: budget c/CATEGORY a/AMOUNT");
            }
            if (amount <= 0) {
                throw new ExpensiveLehException("Budget amount must be positive.");
            }

            if (category.equalsIgnoreCase("food") || category.equalsIgnoreCase("groceries")
                    || category.equalsIgnoreCase("transport")
                    || category.equalsIgnoreCase("others")) {
                return new CategoryBudgetCommand(category, amount);

            } else {
                throw new ExpensiveLehException(
                        "Please specify category of either food, groceries, transport or others");
            }
        } catch (NumberFormatException e) {
            throw new ExpensiveLehException(
                    "Invalid budget category command format. Usage: budget c/CATEGORY a/AMOUNT");
        }
    }
}
