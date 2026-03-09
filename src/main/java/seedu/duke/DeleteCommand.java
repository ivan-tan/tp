package seedu.duke;

import java.io.IOException;

import Storage.Storage;

public class DeleteCommand extends Command {
    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(ExpenseManager expenses, Ui ui, Storage storage) throws ExpensiveLehException, IOException {
        Expense removedExpense = expenses.deleteExpense(index);
        storage.save(expenses.getBudget(), expenses.getExpenses());
        ui.showMessage((index + 1) + ": " + removedExpense.getCategory() + " " + removedExpense.getDescription() + " $" + String.format("%.2f", removedExpense.getAmount()) + " " + removedExpense.getFormattedDate() + " deleted!");
    }
}
