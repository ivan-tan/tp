package seedu.duke;

public abstract class Command {
    public abstract void execute(Managers managers, UI ui) throws ExpensiveLehException;
}
