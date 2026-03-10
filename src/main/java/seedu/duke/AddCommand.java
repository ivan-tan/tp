package seedu.duke;

public class AddCommand extends Command {
    private Expense expense;

    public AddCommand(Expense expense) {
        this.expense = expense;
    }

    @Override
    public void execute(ExpenseManager expenses, UI ui) throws ExpensiveLehException {
        expenses.addExpense(expense);
        ui.showMessage("Expense added successfully! Category: " + expense.getCategory()
                + ", Name: " + expense.getDescription()
                + ", Value: $" + String.format("%.2f", expense.getAmount())
                + ", Date: " + expense.getFormattedDate());
    }
}
