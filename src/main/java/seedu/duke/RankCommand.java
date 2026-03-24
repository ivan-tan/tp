package seedu.duke;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class RankCommand extends Command {
    @Override
    public void execute(Managers managers, UI ui) throws ExpensiveLehException {
        ExpenseManager expenseManager = managers.getExpenseManager();
        if (expenseManager.isEmpty()) {
            ui.showMessage("No expense added yet");
            return;
        }

        Map<String, Double> unsortedTotals = expenseManager.getCategoryTotals();

        List<Map.Entry<String, Double>> rankedList = new ArrayList<>(unsortedTotals.entrySet());
        rankedList.sort((entry1, entry2)
                -> entry2.getValue().compareTo(entry1.getValue()));;

        ui.showRanking(rankedList);
    }
}
