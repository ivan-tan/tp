package seedu.duke;

public class CategoryBudgetCommand extends Command {
    private String category;
    private double amount;

    public CategoryBudgetCommand(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    @Override
    public void execute(Managers managers, UI ui) throws ExpensiveLehException {
        ExpenseManager expenseManager = managers.getExpenseManager();
        expenseManager.setCategoryBudget(category, amount);

        // Calculate total of all category budgets
        double totalCategoryBudgets = 0.0;
        for (Double budgetAmount : expenseManager.getCategoryBudgets().values()) {
            totalCategoryBudgets += budgetAmount;
        }

        // Update global budget if category budgets exceed it
        double currentGlobalBudget = expenseManager.getBudget();
        if (totalCategoryBudgets > currentGlobalBudget) {
            expenseManager.setBudget(totalCategoryBudgets);
            ui.showMessage("Budget of $" + String.format("%.2f", amount) + " set successfully for "
                    + category + "!");
            ui.showMessage("Global budget updated to $" + String.format("%.2f", totalCategoryBudgets)
                    + " as category budgets exceed previous global budget.");
        } else {
            boolean isNewBudget = expenseManager.getCategoryBudget(category) == amount;
            if (isNewBudget) {
                ui.showMessage("Budget of $" + String.format("%.2f", amount) + " set successfully for "
                        + category + "!");
            } else {
                ui.showMessage("Budget updated successfully for " + category + "!");
            }
        }
    }
}
