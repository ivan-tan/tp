package storage;

import loans.Loan; // Ensure this is imported
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import seedu.duke.Expense;
import seedu.duke.Food;
import seedu.duke.Transport;
import seedu.duke.Groceries;
import seedu.duke.Others;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;


class StorageTest {

    @TempDir
    Path tempDir;

    LocalDate testDate = LocalDate.now();
    ArrayList<Loan> loans = new ArrayList<>();
    ArrayList<Expense> expenses = new ArrayList<>();
    HashMap<String,Double> categoryBudgets = new HashMap<>();
    double budget = 0;

    @Test
    void saveAndLoad_validExpenses_success() throws IOException {
        Path filePath = tempDir.resolve("test_finance_data.txt");
        Storage storage = new Storage(filePath.toString());

        expenses.add(new Food("Chicken Rice", 4.50, testDate));
        expenses.add(new Transport("Bus", 1.20, testDate));
        expenses.add(new Groceries("Shampoo", 6.90, testDate));
        expenses.add(new Others("Pickleball racquet", 67.0, testDate));

        storage.save(budget, expenses, loans, categoryBudgets);
        Storage.StorageData loadedData = storage.load();

        assertEquals(4, loadedData.expenses.size(), "Should load 4 expenses");
        assertInstanceOf(Food.class, loadedData.expenses.get(0));
        assertEquals("Chicken Rice", loadedData.expenses.get(0).getDescription());
        assertEquals(67, loadedData.expenses.get(3).getAmount());
    }

    @Test
    void saveAndLoad_validLoans_success() throws IOException {
        Path filePath = tempDir.resolve("test_loans_data.txt");
        Storage storage = new Storage(filePath.toString());
        loans.add(new Loan("Jack", 50.0, testDate));
        loans.add(new Loan("Ashley", 60, testDate));
        storage.save(budget, expenses, loans, categoryBudgets);
        Storage.StorageData loadedData = storage.load();

        assertEquals(2, loadedData.loans.size(), "Should load 2 loans");
        assertEquals("Jack", loadedData.loans.get(0).getDescription(), "Loan name should match");
        assertEquals(50.0, loadedData.loans.get(0).getAmount(), "Loan amount should match");
        assertEquals("Ashley", loadedData.loans.get(1).getDescription());
        assertEquals(60.0, loadedData.loans.get(1).getAmount());
    }

    @Test
    void saveAndLoad_categoryBudgets_success() throws IOException {
        Path filePath = tempDir.resolve("test_catBudgets_data.txt");
        Storage storage = new Storage(filePath.toString());

        ArrayList<Expense> expenses = new ArrayList<>();
        ArrayList<Loan> loans = new ArrayList<>();

        HashMap<String, Double> categoryBudgets = new HashMap<>();
        categoryBudgets.put("food", 200.0);
        categoryBudgets.put("transport", 150.0);

        storage.save(budget, expenses, loans, categoryBudgets);
        Storage.StorageData loadedData = storage.load();

        assertEquals(2, loadedData.categoryBudgets.size(), "Should load 2 categories");
        assertEquals(200.0, loadedData.categoryBudgets.get("food"), "Food budget should match");
        assertEquals(150.0, loadedData.categoryBudgets.get("transport"), "Transport budget should match");
    }

    @Test
    void load_nonExistentFile_returnsEmptyData() throws IOException {
        Storage storage = new Storage("non_existent_file.txt");
        Storage.StorageData data = storage.load();

        assertEquals(0.0, data.budget);
        assertTrue(data.expenses.isEmpty(), "Expenses list should be empty");
        assertTrue(data.loans.isEmpty(), "Loans list should be empty");
    }

    @Test
    void saveAndLoad_overallBudget_success() throws IOException {
        Path filePath = tempDir.resolve("budget_test.txt");
        Storage storage = new Storage(filePath.toString());
        double expectedBudget = 5000.50;
        storage.save(expectedBudget, expenses, loans, categoryBudgets);
        Storage.StorageData loadedData = storage.load();
        assertEquals(expectedBudget, loadedData.budget, "The loaded budget should match the saved budget.");
    }

    @Test
    void load_noFile_returnsZeroBudget() throws IOException {
        Path nonExistentPath = tempDir.resolve("does_not_exist.txt");
        Storage storage = new Storage(nonExistentPath.toString());
        Storage.StorageData loadedData = storage.load();
        assertEquals(0.0, loadedData.budget, "Budget should be 0.0 when no save file is found.");
    }

    @Test
    void load_mixedValidAndInvalid_loadsValidOnly() throws IOException {
        Path filePath = tempDir.resolve("mixed_data.txt");
        String content = "F | Chicken Rice | 4.50 | " + testDate + System.lineSeparator() +
                "CORRUPT | DATA | LINE" + System.lineSeparator() +
                "T | Bus | 1.20 | " + testDate;
        java.nio.file.Files.writeString(filePath, content);
        Storage storage = new Storage(filePath.toString());
        Storage.StorageData data = storage.load();

        assertEquals(2, data.expenses.size(), "Should load 2 valid expenses and skip 1 corrupted line");
        assertEquals("Chicken Rice", data.expenses.get(0).getDescription());
        assertEquals("Bus", data.expenses.get(1).getDescription());
    }

    @Test
    void load_negativeValues_skipsLines() throws IOException {
        Path filePath = tempDir.resolve("negative_values.txt");
        String content = "BUDGET | -100.0" + System.lineSeparator() +
                "F | Debt | -5.0 | " + testDate + System.lineSeparator() +
                "CATEGORY_BUDGET | food | -50.0";
        java.nio.file.Files.writeString(filePath, content);
        Storage storage = new Storage(filePath.toString());
        Storage.StorageData data = storage.load();

        assertEquals(0.0, data.budget, "Negative budget should be ignored");
        assertTrue(data.expenses.isEmpty(), "Negative expense should be ignored");
        assertTrue(data.categoryBudgets.isEmpty(), "Negative category budget should be ignored");
    }

    @Test
    void load_unknownExpenseType_skipsLine() throws IOException {
        Path filePath = tempDir.resolve("unknown_type.txt");
        String content = "Z | Mystery Item | 10.0 | " + testDate;
        java.nio.file.Files.writeString(filePath, content);

        Storage storage = new Storage(filePath.toString());
        Storage.StorageData data = storage.load();

        assertTrue(data.expenses.isEmpty(), "Unknown category codes should be skipped");
    }

    @Test
    void load_malformedNumberFormat_skipsLine() throws IOException {
        Path filePath = tempDir.resolve("bad_number.txt");
        // Amount is "ABC" instead of a double
        String content = "F | Expensive | ABC | " + testDate;
        java.nio.file.Files.writeString(filePath, content);

        Storage storage = new Storage(filePath.toString());
        Storage.StorageData data = storage.load();

        assertTrue(data.expenses.isEmpty(), "Malformed numbers should result in the line being skipped");
    }

}
