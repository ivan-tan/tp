package storage;

import loans.Loan;
import seedu.duke.Expense;
import seedu.duke.Food;
import seedu.duke.Transport;
import seedu.duke.Groceries;
import seedu.duke.Others;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Storage {
    private static Logger logger = Logger.getLogger("logger");
    private String filePath;

    public static class StorageData {
        public final double budget;
        public final ArrayList<Expense> expenses;
        public final ArrayList<Loan> loans;
        public final HashMap<String, Double> categoryBudgets;

        public StorageData(double budget, ArrayList<Expense> expenses, ArrayList<Loan> loans,
                           HashMap<String, Double> categoryBudgets) {
            this.budget = budget;
            this.expenses = expenses;
            this.loans = loans;
            this.categoryBudgets = categoryBudgets != null ? categoryBudgets : new HashMap<>();
        }
    }

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void save(double budget, ArrayList<Expense> expenses, ArrayList<Loan> loans,
                     HashMap<String, Double> categoryBudgets) throws IOException {

        File f = new File(filePath);
        if (f.getParentFile() != null && !f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }

        FileWriter fw = new FileWriter(f);
        fw.write("BUDGET | " + budget + System.lineSeparator());

        for (String category : categoryBudgets.keySet()) {
            double amount = categoryBudgets.get(category);
            fw.write("CATEGORY_BUDGET | " + category.toLowerCase() + " | " + amount + System.lineSeparator());
        }

        for (Expense e : expenses) {
            String type = e instanceof Food ? "F" : e instanceof Transport ? "T" : e instanceof Groceries ? "G" : "O";
            fw.write(type + " | " + e.getDescription() + " | " + e.getAmount() + " | " + e.getDate()
                    + System.lineSeparator());
        }

        // Save Loans
        for (Loan l : loans) {
            fw.write("L | " + l.getDescription() + " | " + l.getAmount() + " | " + l.getDate()
                    + System.lineSeparator());
        }
        fw.close();
    }

    public StorageData load() throws IOException {
        ArrayList<Expense> loadedExpenses = new ArrayList<>();
        ArrayList<Loan> loadedLoans = new ArrayList<>();
        double loadedBudget = 0.0;
        HashMap<String, Double> loadedCategoryBudgets = new HashMap<>();

        File f = new File(filePath);
        if (!f.exists()) {
            return new StorageData(loadedBudget, loadedExpenses, loadedLoans, loadedCategoryBudgets);
        }

        try (Scanner s = new Scanner(f)) {
            int lineCount = 0;
            while (s.hasNextLine()) {
                lineCount++;
                String line = s.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    if (!line.contains(" | ")) {
                        throw new IOException("Missing separator ' | '. Line deleted");
                    }

                    String[] parts = line.split(" \\| ");

                    if (parts[0].equals("BUDGET")) {
                        validatePartsCount(parts, 2);
                        loadedBudget = Double.parseDouble(parts[1]);
                        if (loadedBudget < 0) {
                            loadedBudget = 0.0;
                            throw new IOException("Negative budget. Budget auto set to 0");
                        }
                        continue;
                    }

                    if (parts[0].equals("CATEGORY_BUDGET")) {
                        validatePartsCount(parts, 3);
                        String category = parts[1].toLowerCase();
                        double amount = Double.parseDouble(parts[2]);
                        if (amount < 0) {
                            throw new IOException("Negative category budget. Category budget deleted.");
                        }
                        loadedCategoryBudgets.put(category, amount);
                        continue;
                    }

                    validatePartsCount(parts, 4);
                    String category = parts[0];
                    String description = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    LocalDate date = LocalDate.parse(parts[3]);

                    if (amount < 0) {
                        throw new IOException("Negative amount. Line deleted");
                    }

                    if (category.equals("L")) {
                        loadedLoans.add(new Loan(description, amount, date));
                    } else {
                        loadedExpenses.add(createExpense(category, description, amount, date, lineCount));
                    }

                } catch (Exception e) {
                    logger.log(Level.WARNING, " Corrupted line " + lineCount + ": " + e.getMessage());
                }
            }
        }
        return new StorageData(loadedBudget, loadedExpenses, loadedLoans, loadedCategoryBudgets);
    }

    private void validatePartsCount(String[] parts, int expected) throws IOException {
        if ((parts.length < expected)|| (parts.length > expected)) {
            throw new IOException("Incorrect number of '|' provided. Line deleted.");
        }
    }

    private Expense createExpense(String type, String desc, double amt, LocalDate date, int line) throws IOException {
        switch (type) {
        case "F":
            return new Food(desc, amt, date);
        case "T":
            return new Transport(desc, amt, date);
        case "G":
            return new Groceries(desc, amt, date);
        case "O":
            return new Others(desc, amt, date);
        default:
            throw new IOException("Unknown category code '" + type + "'");
        }
    }
}
