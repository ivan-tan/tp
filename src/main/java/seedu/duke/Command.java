package seedu.duke;

public abstract class Command {
    public abstract void execute(ExpenseManager expenses, UI ui) throws ExpensiveLehException;
}
