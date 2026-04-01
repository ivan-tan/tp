package seedu.duke;

import loans.Loan;
import loans.LoanManager;
import storage.Bookmark;

public class DeleteCommand extends Command {
    private final int index;
    private final String type; // "expense" or "loan"

    public DeleteCommand(int index, String type) {
        this.index = index;
        this.type = type;
    }

    @Override
    public void execute(Managers manager, UI ui) throws ExpensiveLehException {
        if (type.equalsIgnoreCase("loan")) {
            deleteLoan(manager.getLoanManager(), ui);
        } else if (type.equalsIgnoreCase("bookmark")) {
            deleteBookmark(manager.getBookmark(), ui);
        } else {
            deleteExpense(manager.getExpenseManager(), ui);
        }
    }

    private void deleteExpense(ExpenseManager expenses, UI ui) throws ExpensiveLehException {
        // reuse your existing logic
        Expense removedExpense = expenses.deleteExpense(index);
        ui.showMessage((index + 1) + ": " + removedExpense.getCategory()
                + " " + removedExpense.getDescription()
                + " $" + String.format("%.2f", removedExpense.getAmount())
                + " " + removedExpense.getFormattedDate() + " deleted!");
    }

    private void deleteLoan(LoanManager loanManager, UI ui) throws ExpensiveLehException {
        // Logic for deleting from LoanManager
        Loan removedLoan = loanManager.deleteLoan(index);
        ui.showMessage("Loan ID " + (index + 1) + " for " + removedLoan.getDescription()
                + " ($" + String.format("%.2f", removedLoan.getAmount()) + ") deleted!");
    }

    private void deleteBookmark(Bookmark bookmark, UI ui) throws ExpensiveLehException {
        Expense removedBookmark = bookmark.getBookmark(index);
        bookmark.removeBookmark(index);

        ui.showMessage("Bookmark ID " + (index + 1) + " for " + removedBookmark.getDescription()
                + " ($" + String.format("%.2f", removedBookmark.getAmount()) + ") deleted!");
    }
}
