package seedu.duke;

public class ExitCommand extends Command {
    @Override
    public void execute(Managers managers, UI ui) throws ExpensiveLehException {
        ui.showMessage("Bye!");
    }
}
