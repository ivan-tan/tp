package seedu.duke;

public class ExitCommand extends Command {
    @Override
    public void execute(ExpenseManager expenses, UI ui) throws ExpensiveLehException {
        ui.showMessage("Bye!");
    }
}
