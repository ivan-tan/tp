package seedu.duke;

import java.util.ArrayList;

public class SearchCommand extends Command {
    private final String keyword;

    public SearchCommand(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public void execute(Managers managers, UI ui) throws ExpensiveLehException {
        ExpenseManager expenseManager = managers.getExpenseManager();
        ArrayList<Expense> matchedExpenses = expenseManager.searchByKeyword(keyword);

        if (matchedExpenses.isEmpty()) {
            ui.showMessage("No expenses found with keyword: '" + keyword + "'");
            return;
        }

        StringBuilder result = new StringBuilder();
        result.append("\nSearch results for '").append(keyword).append("':\n");
        result.append(String.format("%-6s %-12s %-20s %-10s %-12s%n", "Index", "Category", "Name", "Value", "Date"));
        
        for (int i = 0; i < matchedExpenses.size(); i++) {
            Expense expense = matchedExpenses.get(i);
            result.append(String.format("%-6d %-12s %-20s $%-9.2f %-12s%n",
                    i + 1,
                    expense.getCategory(),
                    expense.getDescription(),
                    expense.getAmount(),
                    expense.getFormattedDate()));
        }
        
        ui.showMessage(result.toString());
    }
}

