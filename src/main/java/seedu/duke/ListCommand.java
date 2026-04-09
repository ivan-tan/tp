package seedu.duke;

import loans.Loan;
import loans.LoanManager;
import storage.Bookmark;

import java.util.ArrayList;

public class ListCommand extends Command {
    private final String listType;

    // Constructor to specify what to list
    public ListCommand(String listType) {
        this.listType = listType;
    }

    // Default constructor for standard 'list'
    public ListCommand() {
        this.listType = "expenses";
    }

    @Override
    public void execute(Managers manager, UI ui) throws ExpensiveLehException {
        if (listType.equalsIgnoreCase("loans")) {
            listLoans(manager.getLoanManager(), ui);
        } else if (listType.equalsIgnoreCase("bookmarks")) {
            listBookmarks(manager.getBookmark(), ui);
        } else {
            listExpenses(manager.getExpenseManager(), ui);
        }
    }

    private void listExpenses(ExpenseManager expenses, UI ui) {
        if (expenses.isEmpty()) {
            ui.showMessage("No expenses added yet");
            return;
        }

        StringBuilder result = new StringBuilder();
        result.append(String.format("\n%-6s %-12s %-20s %-10s %-12s%n", "Index", "Category", "Name", "Value", "Date"));
        for (int i = 0; i < expenses.getSize(); i++) {
            Expense expense = expenses.getExpense(i);
            result.append(formatExpenseRow(i + 1, expense));
        }
        result.append(String.format("%nRemaining budget: $%.2f", expenses.getRemainingBudget()));
        ui.showMessage(result.toString());
    }

    private void listLoans(LoanManager loanManager, UI ui) {
        if (loanManager.isEmpty()) {
            ui.showMessage("No loans recorded!");
            return;
        }

        StringBuilder result = new StringBuilder();
        result.append("\n--- Current Loans (Money Owed to You) ---\n");
        ArrayList<Loan> loans = loanManager.getLoans();

        for (int i = 0; i < loans.size(); i++) {
            // Replace the old toString() call with the new formatted row
            result.append(formatLoanRow(i + 1, loans.get(i)));
        }

        result.append(String.format("\nTotal Owed to You: $%.2f", loanManager.getTotalAmountLent()));
        ui.showMessage(result.toString());
    }

    private void listBookmarks(Bookmark bookmark, UI ui) {
        ArrayList<Expense> bookmarks = bookmark.getBookmarks();

        if (bookmarks.isEmpty()) {
            ui.showMessage("No bookmarks added yet");
            return;
        }

        StringBuilder result = new StringBuilder();
        result.append("\n--- Bookmarks list ---\n");
        result.append(String.format("%-6s %-12s %-20s %-10s %-12s%n", "Index", "Category", "Name", "Value", "Date"));
        for (int i = 0; i < bookmarks.size(); i++) {
            Expense expense = bookmarks.get(i);
            result.append(formatExpenseRow(i + 1, expense));
        }
        result.append("\nUse \"add bookmark [index]\" to add a bookmark to your expense list");

        ui.showMessage(result.toString());
    }

    private String formatExpenseRow(int index, Expense expense) {
        StringBuilder row = new StringBuilder();
        String name = expense.getDescription();
        int maxNameLength = 20;

        if (name.length() <= maxNameLength) {
            // Normal single line execution
            row.append(String.format("%-6d %-12s %-20s $%-9.2f %-12s%n",
                    index, expense.getCategory(), name,
                    expense.getAmount(), expense.getFormattedDate()));
        } else {
            // Multi-line execution for long strings
            // First line prints normal data
            row.append(String.format("%-6d %-12s %-20s $%-9.2f %-12s%n",
                    index, expense.getCategory(), name.substring(0, maxNameLength),
                    expense.getAmount(), expense.getFormattedDate()));

            // Subsequent lines print empty spaces for Index, Category, Value, and Date
            int startIndex = maxNameLength;
            while (startIndex < name.length()) {
                int endIndex = Math.min(startIndex + maxNameLength, name.length());
                String chunk = name.substring(startIndex, endIndex);
                row.append(String.format("%-6s %-12s %-20s%n", "", "", chunk));
                startIndex += maxNameLength;
            }
        }
        return row.toString();
    }

    private String formatLoanRow(int index, Loan loan) {
        StringBuilder row = new StringBuilder();
        String name = loan.getDescription();
        int maxNameLength = 20;

        // Assuming you want to format it nicely like expenses
        if (name.length() <= maxNameLength) {
            row.append(String.format("%-4d Debtor: %-20s | Amount: $%-9.2f | Date: %-12s%n",
                    index, name, loan.getAmount(),
                    loan.getDate().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        } else {
            // First line
            row.append(String.format("%-4d Debtor: %-20s | Amount: $%-9.2f | Date: %-12s%n",
                    index, name.substring(0, maxNameLength), loan.getAmount(),
                    loan.getDate().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

            // Subsequent lines (padding the left side to align with the name column)
            int startIndex = maxNameLength;
            while (startIndex < name.length()) {
                int endIndex = Math.min(startIndex + maxNameLength, name.length());
                String chunk = name.substring(startIndex, endIndex);
                row.append(String.format("%-4s         %-20s%n", "", chunk));
                startIndex += maxNameLength;
            }
        }
        return row.toString();
    }
}
