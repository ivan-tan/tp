package seedu.duke;

public class HelpCommand extends Command {
    @Override
    public void execute(ExpenseManager expenses, UI ui) throws ExpensiveLehException {
        String helpMessage = "To add an expense, use 'add cat/CATEGORY n/NAME a/AMOUNT [date/DATE]' "
                + "Eg: add cat/Food n/Jollibee val/9.95\n"
                + "To edit an expense, use 'edit INDEX [cat/CATEGORY] [n/NAME] [val/VALUE] [date/DATE]'\n"
                + "To list all expenses and view remaining budget, use 'list'\n"
                + "To set a budget, use 'budget VALUE'. Eg: budget 1000\n"
                + "To delete an expense, use 'delete INDEX'. Eg: delete 3\n"
                + "To exit the program, use 'exit'";

        ui.showMessage(helpMessage);
    }
}
