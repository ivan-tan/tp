package seedu.duke;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class RankCommand extends Command {
    @Override
    public void execute(ExpenseManager expenses, UI ui) throws ExpensiveLehException {
        if (expenses.isEmpty()) {
            ui.showMessage("No expense added yet");
            return;
        }

        Map<String, Double> unsortedTotals = expenses.getCategoryTotals();

        List<Map.Entry<String, Double>> rankedList = new ArrayList<>(unsortedTotals.entrySet());
        rankedList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));;

        ui.showRanking(rankedList);
    }
}
