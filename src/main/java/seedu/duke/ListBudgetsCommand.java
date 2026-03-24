package seedu.duke;

import java.util.HashMap;

public class ListBudgetsCommand extends Command {
    @Override
    public void execute(ExpenseManager expenses, UI ui) throws ExpensiveLehException {
        HashMap<String, Double> categoryBudgets = expenses.getCategoryBudgets();

        if (categoryBudgets.isEmpty()) {
            ui.showMessage("No category budgets set yet. Use 'budget c/CATEGORY a/AMOUNT' to set a category budget.");
            return;
        }

        StringBuilder result = new StringBuilder();
        result.append(String.format("\n%-15s %-15s %-15s%n", "Category", "Budget", "Remaining"));
        result.append("--------------------------------------------------\n");

        for (String category : categoryBudgets.keySet()) {
            double budget = categoryBudgets.get(category);
            double remaining = expenses.getRemainingBudgetForCategory(category);
            result.append(String.format("%-15s $%-14.2f $%-14.2f%n",
                    capitalize(category), budget, remaining));
        }

        ui.showMessage(result.toString());
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
