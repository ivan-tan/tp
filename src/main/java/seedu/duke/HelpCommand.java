package seedu.duke;

public class HelpCommand extends Command {
    @Override
    public void execute(Managers managers, UI ui) throws ExpensiveLehException {
        String helpMessage = "\nTo add an expense, use 'add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]'. Eg: add c/Food n/Chicken Rice a/9.95\n"
                + "To add a loan, use 'add c/loan n/NAME a/AMOUNT [d/DD-MM-YYYY]'. Eg: add c/loan n/John Doe a/50.00\n"
                + "To add a bookmark to expenses, use 'add bookmark INDEX'. Eg: add bookmark 1\n"
                + "To edit an expense, use 'edit INDEX [c/CATEGORY] [n/NAME] [a/AMOUNT] [d/DD-MM-YYYY]'. Eg: edit 1 a/10.00\n"
                + "To edit a loan, use 'edit loan INDEX [n/NAME] [a/AMOUNT] [d/DD-MM-YYYY]'. Eg: edit loan 1 a/100.00\n"
                + "To delete an expense, use 'delete expense INDEX'. Eg: delete expense 1\n"
                + "To delete a loan, use 'delete loan INDEX'. Eg: delete loan 1\n"
                + "To delete a bookmark, use 'delete bookmark INDEX'. Eg: delete bookmark 1\n"
                + "To set a global budget, use 'budget AMOUNT'. Eg: budget 500.00\n"
                + "To set a category budget, use 'budget c/CATEGORY a/AMOUNT'. Eg: budget c/Food a/100.00\n"
                + "To list all expenses and remaining budget, use 'list expenses' or 'list'\n"
                + "To list all loans, use 'list loans' or 'loans'\n"
                + "To list all bookmarks, use 'list bookmarks'\n"
                + "To list all category budgets, use 'list budgets'\n"
                + "To bookmark an expense, use 'bookmark INDEX'. Eg: bookmark 1\n"
                + "To search for an expense, use 'search KEYWORD'. Eg: search chicken\n"
                + "To rank expenses by amount, use 'rank expenses'\n"
                + "To rank loans by amount, use 'rank loans'\n"
                + "To view all commands, use 'help'\n"
                + "To exit the program, use 'exit'";

        ui.showMessage(helpMessage);
    }
}
