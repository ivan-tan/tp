package seedu.duke;

import java.io.IOException;

import Storage.Storage;

public class BudgetCommand extends Command {
    private double value;

    public BudgetCommand(double value) {
        this.value = value;
    }

    @Override
    public void execute(ExpenseManager expenses, Ui ui, Storage storage) throws ExpensiveLehException, IOException {
        boolean isNewBudget = expenses.getBudget() == 0.0;
        expenses.setBudget(value);
        storage.save(expenses.getBudget(), expenses.getExpenses());
        if (isNewBudget) {
            ui.showMessage("Budget of $" + String.format("%.2f", value) + " set successfully!");
        } else {
            ui.showMessage("Budget updated successfully!");
        }
    }
}
