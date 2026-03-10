package seedu.duke;

import java.time.LocalDate;

public class Groceries extends Expense {

    public Groceries(String description, double amount, LocalDate date) {
        super(description, amount, date);
    }

    public Groceries(String description, double amount) {
        super(description, amount);
    }

    @Override
    public String getCategory() {
        return "Groceries";
    }
}

