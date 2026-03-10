package seedu.duke;

import java.time.LocalDate;

public class Transport extends Expense {

    public Transport(String description, double amount, LocalDate date) {
        super(description, amount, date);
    }

    public Transport(String description, double amount) {
        super(description, amount);
    }

    @Override
    public String getCategory() {
        return "Transport";
    }
}

