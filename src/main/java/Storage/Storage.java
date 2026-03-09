package Storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
/* How to use:
Pass filepath to Storage. Initialise a variable storage.storageData data = storage.load().
data will contain a double budget, and an Arraylist<Expense>.
For example,
            Storage.StorageData data = storage.load();
            double budget = data.budget;
            ArrayList<Expense> expenses = data.expenses;
 */
public class Storage {
    private String filePath;

    public static class StorageData {
        public final double budget;
        public final ArrayList<Expense> expenses;

        public StorageData(double budget, ArrayList<Expense> expenses) {
            this.budget = budget;
            this.expenses = expenses;
        }
    }

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void save(double budget, ArrayList<Expense> expenses) throws IOException {
        File f = new File(filePath);
        if (f.getParentFile() != null && !f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }

        FileWriter fw = new FileWriter(f);
        fw.write("BUDGET | " + budget + System.lineSeparator());

        for (Expense e : expenses) {
            // Food = F, Transport = T, Groceries = G, Others = O
            String type = e instanceof Food ? "F" : e instanceof Transport ? "T" : e instanceof Groceries ? "G" : "O";
            String line = type + " | " + e.getDescription() + " | " + e.getAmount();
            fw.write(line + System.lineSeparator());
        }
        fw.close();
    }

    public StorageData load() throws IOException {
        ArrayList<Expense> loadedExpenses = new ArrayList<>();
        double loadedBudget = 0.0;

        File f = new File(filePath);
        if (!f.exists()) return new StorageData(loadedBudget, loadedExpenses);

        Scanner s = new Scanner(f);
        while (s.hasNextLine()) {
            String line = s.nextLine();
            if (line.trim().isEmpty()) continue;

            String[] parts = line.split(" \\| ");

            if (parts[0].equals("BUDGET")) {
                loadedBudget = Double.parseDouble(parts[1]);
                continue;
            }

            // Logic to recreate specific subclasses
            String category = parts[0];
            String description = parts[1];
            double amount = Double.parseDouble(parts[2]);

            Expense expense = null;
            switch (category) {
            case "Food":
                expense = new Food(description, amount);
                break;
            case "Transport":
                expense = new Transport(description, amount);
                break;
            case "Groceries":
                expense = new Groceries(description, amount);
                break;
            default:
                expense = new Others(description, amount);
                break;
            }

            if (expense != null) {
                loadedExpenses.add(expense);
            }
        }
        s.close();
        return new StorageData(loadedBudget, loadedExpenses);
    }
}
