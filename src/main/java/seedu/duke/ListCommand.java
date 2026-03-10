package seedu.duke;

public class ListCommand extends Command {
    @Override
    public void execute(ExpenseManager expenses, UI ui) throws ExpensiveLehException {
        if (expenses.isEmpty()) {
            ui.showMessage("No expense added yet");
            return;
        }

        StringBuilder result = new StringBuilder();
        result.append(String.format("%-6s %-12s %-20s %-10s %-12s%n", "Index", "Category", "Name", "Value", "Date"));
        for (int i = 0; i < expenses.getSize(); i++) {
            Expense expense = expenses.getExpense(i);
            result.append(String.format("%-6d %-12s %-20s $%-9.2f %-12s%n",
                    i + 1,
                    expense.getCategory(),
                    expense.getDescription(),
                    expense.getAmount(),
                    expense.getFormattedDate()));
        }
        result.append(String.format("%nRemaining budget: $%.2f", expenses.getRemainingBudget()));
        ui.showMessage(result.toString().trim());
    }
}
