package seedu.duke;

import Storage.Storage;
import java.io.IOException;

public class ExpensiveLeh {

    private Parser parser = new Parser();
    private UI ui = new UI();
    private Storage storage = new Storage("data/expenses.txt");
    private ExpenseManager expenseManager;

    public void run() {
        try {
            Storage.StorageData data = storage.load();
            expenseManager = new ExpenseManager(data.expenses, data.budget);
        } catch (IOException e) {
            ui.showError("Could not load save file: " + e.getMessage());
            expenseManager = new ExpenseManager();
        }

        ui.showWelcome();

        boolean isRunning = true;
        while (isRunning) {
            try {
                Command command = parser.readCommand();
                command.execute(expenseManager, ui);
                storage.save(expenseManager.getBudget(), expenseManager.getExpenses());
                if (command instanceof ExitCommand) {
                    isRunning = false;
                }
            } catch (ExpensiveLehException e) {
                ui.showError(e.getMessage());
                ui.showLine();
            } catch (IOException e) {
                ui.showError("Could not save data: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new ExpensiveLeh().run();
    }
}
