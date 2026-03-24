package seedu.duke;

public class CategoryBudgetCommand extends Command {
    private String category;
    private double amount;

    public CategoryBudgetCommand(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    @Override
    public void execute(ExpenseManager expenses, UI ui) throws ExpensiveLehException {
        expenses.setCategoryBudget(category, amount);
        boolean isNewBudget = expenses.getCategoryBudget(category) == amount;
        if (isNewBudget) {
            ui.showMessage("Budget of $" + String.format("%.2f", amount) + " set successfully for " + category + "!");
        } else {
            ui.showMessage("Budget updated successfully for " + category + "!");
        }
    }
}
