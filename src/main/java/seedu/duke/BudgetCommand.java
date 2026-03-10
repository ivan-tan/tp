package seedu.duke;

public class BudgetCommand extends Command {
    private double value;

    public BudgetCommand(double value) {
        this.value = value;
    }

    @Override
    public void execute(ExpenseManager expenses, UI ui) throws ExpensiveLehException {
        boolean isNewBudget = expenses.getBudget() == 0.0;
        expenses.setBudget(value);
        if (isNewBudget) {
            ui.showMessage("Budget of $" + String.format("%.2f", value) + " set successfully!");
        } else {
            ui.showMessage("Budget updated successfully!");
        }
    }
}
