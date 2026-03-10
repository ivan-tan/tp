package seedu.duke;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Expense {
    protected String description;
    protected double amount;
    protected LocalDate date;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Expense(String description, double amount, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Expense(String description, double amount) {
        this(description, amount, LocalDate.now());
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getFormattedDate() {
        return date.format(FORMATTER);
    }

    public abstract String getCategory();

    @Override
    public String toString() {
        return getCategory() + " | " + description + " | $" + String.format("%.2f", amount)
                + " | " + getFormattedDate();
    }
}
