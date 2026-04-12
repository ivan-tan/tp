package seedu.duke;

import storage.Bookmark;
import storage.Storage;
import loans.LoanManager;
import java.io.IOException;

public class ExpensiveLeh {

    private Parser parser = new Parser();
    private UI ui = new UI();
    private Storage storage = new Storage("data/expenses.txt");
    private Bookmark bookmark = new Bookmark("data/bookmarks.txt");
    private Managers managers;

    public void run() {
        try {
            Storage.StorageData data = storage.load();
            bookmark.load();
            
            managers = new Managers(new ExpenseManager(data.expenses, data.budget, data.categoryBudgets), 
                    new LoanManager(data.loans), bookmark);

        } catch (IOException e) {
            ui.showError("\n WARNING! Could not load save file: " + e.getMessage() + "\n");
            managers = new Managers(new ExpenseManager(), new LoanManager(), bookmark);
        }

        ui.showWelcome();

        boolean isRunning = true;
        while (isRunning) {
            try {
                Command command = parser.readCommand();
                command.execute(managers, ui);
                storage.save(managers.getExpenseManager().getBudget(), managers.getExpenseManager().getExpenses(),
                        managers.getLoanManager().getLoans(), managers.getExpenseManager().getCategoryBudgets());
                
                if (command instanceof ExitCommand) {
                    isRunning = false;
                }
            } catch (ExpensiveLehException e) {
                ui.showError(e.getMessage());
                ui.showLine();
            } catch (IOException e) {
                ui.showError("\n WARNING! Could not save data: " + e.getMessage() + '\n');
            }
        }
    }

    public static void main(String[] args) {
        new ExpensiveLeh().run();
    }
}
