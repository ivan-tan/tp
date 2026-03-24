package loans;

import seedu.duke.Expense;

import java.time.LocalDate;

public class Loan extends Expense {
    public Loan(String personName, double amount, LocalDate date) {
        super(personName, amount, date);
    }

    @Override
    public String getCategory() {
        return "loan";
    }

    @Override
    public String toString() {
        return "[OWED BY] " + description + " | $" + String.format("%.2f", amount)
                + " | " + getFormattedDate();
    }
}
