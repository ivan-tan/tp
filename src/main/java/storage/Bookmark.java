package storage;

import seedu.duke.Expense;
import seedu.duke.Food;
import seedu.duke.Transport;
import seedu.duke.Groceries;
import seedu.duke.Others;
import seedu.duke.ExpensiveLehException;
import loans.Loan;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;

public class Bookmark {
    private final String filePath;
    private final ArrayList<Expense> bookmarks;

    public Bookmark(String filePath) {
        this.filePath = filePath;
        this.bookmarks = new ArrayList<>();
    }

    public void addBookmark(Expense expense) {
        bookmarks.add(expense);
    }

    public void removeBookmark(int index) throws ExpensiveLehException {
        if (index < 0 || index >= bookmarks.size()) {
            throw new ExpensiveLehException("Please provide a valid bookmark index to remove!");
        }
        bookmarks.remove(index);
    }

    public ArrayList<Expense> getBookmarks() {
        return bookmarks;
    }

    public Expense getBookmark(int index) throws ExpensiveLehException {
        if (index < 0 || index >= bookmarks.size()) {
            throw new ExpensiveLehException("Please provide a valid bookmark index!");
        }
        return bookmarks.get(index);
    }

    public void save() throws IOException {
        File f = new File(filePath);
        if (f.getParentFile() != null && !f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }

        FileWriter fw = new FileWriter(f);

        for (Expense e : bookmarks) {
            String type = e instanceof Loan ? "L" : e instanceof Food ? "F"
                    : e instanceof Transport ? "T" : e instanceof Groceries ? "G" : "O";
            fw.write(type + " | " + e.getDescription() + " | " + e.getAmount() + " | " + e.getDate()
                    + System.lineSeparator());
        }

        fw.close();
    }

    public void load() throws IOException {
        bookmarks.clear();

        File f = new File(filePath);
        if (!f.exists()) {
            return;
        }

        Scanner s = new Scanner(f);

        while (s.hasNextLine()) {
            String line = s.nextLine();
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(" \\| ");
            String category = parts[0];
            String description = parts[1];
            double amount = Double.parseDouble(parts[2]);
            LocalDate date = LocalDate.parse(parts[3]);

            Expense expense;

            switch (category) {
            case "L":
                expense = new Loan(description, amount, date);
                break;
            case "F":
                expense = new Food(description, amount, date);
                break;
            case "T":
                expense = new Transport(description, amount, date);
                break;
            case "G":
                expense = new Groceries(description, amount, date);
                break;
            default:
                expense = new Others(description, amount, date);
            }

            bookmarks.add(expense);
        }
        s.close();
    }
}
