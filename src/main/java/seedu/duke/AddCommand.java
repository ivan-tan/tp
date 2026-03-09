package seedu.duke;

import java.io.IOException;

import Storage.Storage;

public class AddCommand extends Command {
    private Expense expense;

    public AddCommand(Expense expense) {
        this.expense = expense;
    }

    @Override
    public void execute(ExpenseManager expenses, Ui ui, Storage storage) throws ExpensiveLehException, IOException {
        expenses.addExpense(expense);
        storage.save(expenses.getBudget(), expenses.getExpenses());
        ui.showMessage("Expense added successfully! Category: " + expense.getCategory() + ", Name: " + expense.getDescription() + ", Value: $" + String.format("%.2f", expense.getAmount()) + ", Date: " + expense.getFormattedDate());
    }
}
