package seedu.duke;

import java.time.LocalDate;

public class Food extends Expense {

    public Food(String description, double amount, LocalDate date) {
        super(description, amount, date);
    }

    public Food(String description, double amount) {
        super(description, amount);
    }

    @Override
    public String getCategory() {
        return "Food";
    }
}

