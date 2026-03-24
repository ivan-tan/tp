package seedu.duke;

public class BudgetCommand extends Command {
    private double value;

    public BudgetCommand(double value) {
        this.value = value;
    }

    @Override
    public void execute(Managers managers, UI ui) throws ExpensiveLehException {
        ExpenseManager expenseManager = managers.getExpenseManager();
        boolean isNewBudget = expenseManager.getBudget() == 0.0;
        expenseManager.setBudget(value);
        if (isNewBudget) {
            ui.showMessage("Budget of $" + String.format("%.2f", value) + " set successfully!");
        } else {
            ui.showMessage("Budget updated successfully!");
        }
    }
}
