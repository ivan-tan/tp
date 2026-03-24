package storage;

import seedu.duke.Expense;
import seedu.duke.Food;
import seedu.duke.Transport;
import seedu.duke.Groceries;
import seedu.duke.Others;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;


public class Storage {
    private String filePath;

    public static class StorageData {
        public final double budget;
        public final ArrayList<Expense> expenses;
        public final HashMap<String, Double> categoryBudgets;

        public StorageData(double budget, ArrayList<Expense> expenses) {
            this.budget = budget;
            this.expenses = expenses;
            this.categoryBudgets = new HashMap<>();
        }

        public StorageData(double budget, ArrayList<Expense> expenses, HashMap<String, Double> categoryBudgets) {
            this.budget = budget;
            this.expenses = expenses;
            this.categoryBudgets = categoryBudgets != null ? categoryBudgets : new HashMap<>();
        }
    }

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void save(double budget, ArrayList<Expense> expenses, HashMap<String, Double> categoryBudgets)
            throws IOException {
        File f = new File(filePath);
        if (f.getParentFile() != null && !f.getParentFile().exists()) {
            boolean created = f.getParentFile().mkdirs();
            if (!created) {
                throw new IOException("Failed to create directory: " + f.getParentFile());
            }
        }

        FileWriter fw = new FileWriter(f);
        fw.write("BUDGET | " + budget + System.lineSeparator());

        for (String category : categoryBudgets.keySet()) {
            double amount = categoryBudgets.get(category);
            fw.write("CATEGORY_BUDGET | " + category.toLowerCase() + " | " + amount + System.lineSeparator());
        }

        for (Expense e : expenses) {
            String type = e instanceof Food ? "F"
                    : e instanceof Transport ? "T"
                    : e instanceof Groceries ? "G" : "O";
            String line = type + " | " + e.getDescription() + " | " + e.getAmount();
            fw.write(line + System.lineSeparator());
        }
        fw.close();
    }

    public StorageData load() throws IOException {
        ArrayList<Expense> loadedExpenses = new ArrayList<>();
        double loadedBudget = 0.0;
        HashMap<String, Double> loadedCategoryBudgets = new HashMap<>();

        File f = new File(filePath);
        if (!f.exists()) {
            return new StorageData(loadedBudget, loadedExpenses, loadedCategoryBudgets);
        }

        Scanner s = new Scanner(f);
        while (s.hasNextLine()) {
            String line = s.nextLine();
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(" \\| ");

            if (parts[0].equals("BUDGET")) {
                loadedBudget = Double.parseDouble(parts[1]);

                if (loadedBudget < 0) {
                    throw new IOException("Invalid budget in file: budget cannot be negative");
                }

                assert !Double.isNaN(loadedBudget) : "Loaded budget should never be NaN";
                assert Double.isFinite(loadedBudget) : "Loaded budget should be finite";

                continue;
            }

            if (parts[0].equals("CATEGORY_BUDGET")) {
                String category = parts[1];
                double amount = Double.parseDouble(parts[2]);

                if (amount < 0) {
                    throw new IOException("Invalid category budget in file: category budget cannot be negative");
                }

                loadedCategoryBudgets.put(category.toLowerCase(), amount);
                continue;
            }

            String category = parts[0];
            String description = parts[1];
            double amount = Double.parseDouble(parts[2]);

            Expense expense;
            switch (category) {
            case "F":
                expense = new Food(description, amount);
                break;
            case "T":
                expense = new Transport(description, amount);
                break;
            case "G":
                expense = new Groceries(description, amount);
                break;
            default:
                expense = new Others(description, amount);
                break;
            }
            loadedExpenses.add(expense);
        }
        s.close();
        return new StorageData(loadedBudget, loadedExpenses, loadedCategoryBudgets);
    }
}
