package seedu.duke;

import Storage.Storage;

import java.io.IOException;
import java.time.LocalDate;

public class EditCommand extends Command {
    private int index;
    private String category;
    private String name;
    private Double value;
    private LocalDate date;

    public EditCommand(int index, String category, String name, Double value, LocalDate date) {
        this.index = index;
        this.category = category;
        this.name = name;
        this.value = value;
        this.date = date;
    }

    @Override
    public void execute(ExpenseManager expenses, Ui ui, Storage storage) throws ExpensiveLehException, IOException {
        expenses.editExpense(index, category, name, value, date);
        storage.save(expenses.getBudget(), expenses.getExpenses());
        ui.showMessage("Expense at index " + (index + 1) + " updated successfully!");
    }
}
