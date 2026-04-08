package seedu.duke;

import loans.Loan;
import loans.LoanManager;
import java.util.ArrayList;

public class SearchCommand extends Command {
    private final String keyword;

    public SearchCommand(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public void execute(Managers managers, UI ui) throws ExpensiveLehException {
        ExpenseManager expenseManager = managers.getExpenseManager();
        LoanManager loanManager = managers.getLoanManager();

        ArrayList<Expense> matchedExpenses = expenseManager.searchByKeyword(keyword);
        ArrayList<Loan> matchedLoans = loanManager.searchByKeyword(keyword);

        boolean hasExpenses = !matchedExpenses.isEmpty();
        boolean hasLoans = !matchedLoans.isEmpty();

        if (!hasExpenses && !hasLoans) {
            ui.showMessage("No expenses or loans found with keyword: '" + keyword + "'");
            return;
        }

        StringBuilder result = new StringBuilder();
        result.append("\nSearch results for '").append(keyword).append("':\n");

        // Display expenses
        if (hasExpenses) {
            result.append("\n--- Expenses ---\n");
            result.append(String.format("%-6s %-12s %-20s %-10s %-12s%n",
                    "Index", "Category", "Name", "Value", "Date"));

            for (int i = 0; i < matchedExpenses.size(); i++) {
                Expense expense = matchedExpenses.get(i);
                result.append(String.format("%-6d %-12s %-20s $%-9.2f %-12s%n",
                        i + 1,
                        expense.getCategory(),
                        expense.getDescription(),
                        expense.getAmount(),
                        expense.getFormattedDate()));
            }
        }

        // Display loans
        if (hasLoans) {
            result.append("\n--- Loans ---\n");
            result.append(String.format("%-6s %-12s %-20s %-10s %-12s%n",
                    "Index", "Category", "Name", "Value", "Date"));

            for (int i = 0; i < matchedLoans.size(); i++) {
                Loan loan = matchedLoans.get(i);
                result.append(String.format("%-6d %-12s %-20s $%-9.2f %-12s%n",
                        i + 1,
                        loan.getCategory(),
                        loan.getDescription(),
                        loan.getAmount(),
                        loan.getFormattedDate()));
            }
        }

        ui.showMessage(result.toString());
    }
}

