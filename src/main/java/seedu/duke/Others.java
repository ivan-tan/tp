package seedu.duke;

import java.time.LocalDate;

public class Others extends Expense {

    public Others(String description, double amount, LocalDate date) {
        super(description, amount, date);
    }

    public Others(String description, double amount) {
        super(description, amount);
    }

    @Override
    public String getCategory() {
        return "Others";
    }
}

