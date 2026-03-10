package seedu.duke;

public class DeleteCommand extends Command {
    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(ExpenseManager expenses, UI ui) throws ExpensiveLehException {
        Expense removedExpense = expenses.deleteExpense(index);
        ui.showMessage((index + 1) + ": " + removedExpense.getCategory()
                + " " + removedExpense.getDescription()
                + " $" + String.format("%.2f", removedExpense.getAmount())
                + " " + removedExpense.getFormattedDate() + " deleted!");
    }
}
