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
        // 1. Prepare Expenses
        expenses.add(new Food("Chicken Rice", 4.50, testDate));
        expenses.add(new Transport("Bus", 1.20, testDate));
        expenses.add(new Groceries("Shampoo", 6.90, testDate));
        expenses.add(new Others("Pickleball racquet", 67.0, testDate));
        // Save data
        storage.save(budget, expenses, loans, categoryBudgets);
        // Load all data
        Storage.StorageData loadedData = storage.load();
        // Verify Expenses
        assertEquals(4, loadedData.expenses.size(), "Should load 4 expenses");
        assertInstanceOf(Food.class, loadedData.expenses.get(0));
        assertEquals("Chicken Rice", loadedData.expenses.get(0).getDescription());
        assertEquals(67, loadedData.expenses.get(3).getAmount());
    }

    @Test
    void saveAndLoad_validLoans_success() throws IOException {
        Path filePath = tempDir.resolve("test_loans_data.txt");
        Storage storage = new Storage(filePath.toString());
        // Prepare Loans
        loans.add(new Loan("Jack", 50.0, testDate));
        loans.add(new Loan("Ashley", 60, testDate));
        // Save data
        storage.save(budget, expenses, loans, categoryBudgets);
        // Load all data
        Storage.StorageData loadedData = storage.load();
        // 6. Verify Loans
        assertEquals(2, loadedData.loans.size(), "Should load 2 loans");
        assertEquals("Jack", loadedData.loans.get(0).getDescription(), "Loan name should match");
        assertEquals(50.0, loadedData.loans.get(0).getAmount(), "Loan amount should match");
        assertEquals("Ashley", loadedData.loans.get(1).getDescription());
        assertEquals(60.0, loadedData.loans.get(1).getAmount());
    }

    @Test
    void saveAndLoad_categoryBudgets_success() throws IOException {
        // Arrange
        Path filePath = tempDir.resolve("test_catBudgets_data.txt");
        Storage storage = new Storage(filePath.toString());

        double overallBudget = 1000.0;
        ArrayList<Expense> expenses = new ArrayList<>();
        ArrayList<Loan> loans = new ArrayList<>();

        HashMap<String, Double> categoryBudgets = new HashMap<>();
        categoryBudgets.put("food", 200.0);
        categoryBudgets.put("transport", 150.0);

        // Act
        storage.save(budget, expenses, loans, categoryBudgets);
        Storage.StorageData loadedData = storage.load();

        // Assert
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
        // Arrange: Create storage in a temporary file
        Path filePath = tempDir.resolve("budget_test.txt");
        Storage storage = new Storage(filePath.toString());

        double expectedBudget = 5000.50;
        // Act: Save and then load the data
        storage.save(expectedBudget, expenses, loans, categoryBudgets);
        Storage.StorageData loadedData = storage.load();

        // Assert: Verify the budget was preserved
        assertEquals(expectedBudget, loadedData.budget, "The loaded budget should match the saved budget.");
    }

    @Test
    void load_noFile_returnsZeroBudget() throws IOException {
        // Arrange: Use a path that definitely doesn't exist
        Path nonExistentPath = tempDir.resolve("does_not_exist.txt");
        Storage storage = new Storage(nonExistentPath.toString());

        // Act: Attempt to load
        Storage.StorageData loadedData = storage.load();

        // Assert: Your code returns 0.0 if the file doesn't exist
        assertEquals(0.0, loadedData.budget, "Budget should be 0.0 when no save file is found.");
    }

}
