package seedu.duke;

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
    public void execute(ExpenseManager expenses, UI ui) throws ExpensiveLehException {
        expenses.editExpense(index, category, name, value, date);
        ui.showMessage("Expense at index " + (index + 1) + " updated successfully!");
    }
}
