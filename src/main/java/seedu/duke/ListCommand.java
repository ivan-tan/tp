package seedu.duke;

import loans.Loan;
import loans.LoanManager;

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
        } else {
            listExpenses(manager.getExpenseManager(), ui);
        }
    }

    private void listExpenses(ExpenseManager expenses, UI ui) {
        if (expenses.isEmpty()) {
            ui.showMessage("No expense added yet");
            return;
        }

        StringBuilder result = new StringBuilder();
        result.append(String.format("\n%-6s %-12s %-20s %-10s %-12s%n", "Index", "Category", "Name", "Value", "Date"));
        for (int i = 0; i < expenses.getSize(); i++) {
            Expense expense = expenses.getExpense(i);
            result.append(String.format("%-6d %-12s %-20s $%-9.2f %-12s%n",
                    i + 1, expense.getCategory(), expense.getDescription(),
                    expense.getAmount(), expense.getFormattedDate()));
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
            result.append(String.format("%-4d %s%n", i + 1, loans.get(i).toString()));
        }
        result.append(String.format("\nTotal Owed to You: $%.2f", loanManager.getTotalAmountLent()));
        ui.showMessage(result.toString());
    }
}
