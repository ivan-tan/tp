package seedu.duke;

public class HelpCommand extends Command {
    @Override
    public void execute(ExpenseManager expenses, UI ui) throws ExpensiveLehException {
        String helpMessage = "\nTo add an expense, use 'add c/CATEGORY n/NAME a/AMOUNT [date/DATE]' "
                + "Eg: add c/Food n/Jollibee a/9.95\n"
                + "To edit an expense, use 'edit INDEX [c/CATEGORY] [n/NAME] [a/AMOUNT] [date/DATE]'\n"
                + "To list all expenses and view remaining budget, use 'list'\n"
                + "To set a global budget, use 'budget VALUE'. Eg: budget 1000\n"
                + "To set a budget for a specific category, use 'budget c/CATEGORY a/AMOUNT'. Eg: budget c/Food a/500\n"
                + "To list all category budgets and their remaining amounts, use 'list budgets'\n"
                + "To delete an expense, use 'delete INDEX'. Eg: delete 3\n"
                + "To exit the program, use 'exit'";

        ui.showMessage(helpMessage);
    }
}
