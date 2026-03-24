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
        boolean isNewBudget = expenseManager.getCategoryBudget(category) == amount;
        if (isNewBudget) {
            ui.showMessage("Budget of $" + String.format("%.2f", amount) + " set successfully for " + category + "!");
        } else {
            ui.showMessage("Budget updated successfully for " + category + "!");
        }
    }
}
