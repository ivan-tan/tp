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
import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    void saveAndLoad_validDataWithLoans_success() throws IOException {
        LocalDate testDate = LocalDate.now();
        Path filePath = tempDir.resolve("test_finance_data.txt");
        Storage storage = new Storage(filePath.toString());

        // 1. Prepare Expenses
        double budget = 500.0;
        ArrayList<Expense> expenses = new ArrayList<>();
        expenses.add(new Food("Chicken Rice", 4.50, testDate));
        expenses.add(new Transport("Bus", 1.20, testDate));
        expenses.add(new Groceries("Shampoo", 6.90, testDate));
        expenses.add(new Others("Pickleball racquet", 67.0, testDate));

        // Prepare Loans
        ArrayList<Loan> loans = new ArrayList<>();
        loans.add(new Loan("Jack", 50.0, testDate));
        loans.add(new Loan("Ashley", 60, testDate));
        // Save data
        storage.save(budget, expenses, loans, new HashMap<>());

        // Load all data
        Storage.StorageData loadedData = storage.load();

        // Verify Budget and Expenses
        assertEquals(budget, loadedData.budget, "Budget should match");
        assertEquals(4, loadedData.expenses.size(), "Should load 4 expenses");
        assertTrue(loadedData.expenses.get(0) instanceof Food);
        assertEquals("Bus", loadedData.expenses.get(1).getDescription());

        // 6. Verify Loans
        assertEquals(2, loadedData.loans.size(), "Should load 2 loans");
        assertEquals("Jack", loadedData.loans.get(0).getDescription(), "Loan name should match");
        assertEquals(50.0, loadedData.loans.get(0).getAmount(), "Loan amount should match");
        assertEquals("Ashley", loadedData.loans.get(1).getDescription());
        assertEquals(60.0, loadedData.loans.get(1).getAmount());
    }

    @Test
    void load_nonExistentFile_returnsEmptyData() throws IOException {
        Storage storage = new Storage("non_existent_file.txt");
        Storage.StorageData data = storage.load();

        assertEquals(0.0, data.budget);
        assertTrue(data.expenses.isEmpty());
        assertTrue(data.loans.isEmpty(), "Loans list should also be empty");
    }


}
