package seedu.duke;

public class BudgetCommand extends Command {
    private double value;

    public BudgetCommand(double value) {
        this.value = value;
    }

    @Override
    public void execute(Managers managers, UI ui) throws ExpensiveLehException {
        ExpenseManager expenseManager = managers.getExpenseManager();

        double totalCategoryBudgets = 0.0;
        for (Double budgetAmount : expenseManager.getCategoryBudgets().values()) {
            totalCategoryBudgets += budgetAmount;
        }
        if (value < totalCategoryBudgets){
            throw new ExpensiveLehException("global budget cannot be lower than sum of category budgets!");
        }

        boolean isNewBudget = expenseManager.getBudget() == 0.0;
        expenseManager.setBudget(value);
        if (isNewBudget) {
            ui.showMessage("Budget of $" + String.format("%.2f", value) + " set successfully!");
        } else {
            ui.showMessage("Budget updated successfully!");
        }
    }
}
