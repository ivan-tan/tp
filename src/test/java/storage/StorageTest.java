package storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import seedu.duke.Expense;
import seedu.duke.Food;
import seedu.duke.Transport;
import seedu.duke.Groceries;
import seedu.duke.Others;


import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageTest {

    @TempDir
    Path tempDir; // Automatically creates and cleans up a temp directory

    @Test
    void saveAndLoad_validData_success() throws IOException {
        Path filePath = tempDir.resolve("test_expenses.txt");
        Storage storage = new Storage(filePath.toString());

        // 1. Prepare data
        double budget = 500.0;
        ArrayList<Expense> expenses = new ArrayList<>();
        expenses.add(new Food("Chicken Rice", 4.50));
        expenses.add(new Transport("Bus", 1.20));
        expenses.add(new Groceries("Shampoo", 6.90));
        expenses.add(new Others("Pickleball racquet", 67.0));

        // 2. Save data
        storage.save(budget, expenses);

        // 3. Load data
        Storage.StorageData loadedData = storage.load();

        // 4. Verify
        assertEquals(budget, loadedData.budget, "Budget should match");
        assertEquals(4, loadedData.expenses.size(), "Should load 2 expenses");

        assertEquals("Chicken Rice", loadedData.expenses.get(0).getDescription());
        assertTrue(loadedData.expenses.get(0) instanceof Food);

        assertEquals(1.20, loadedData.expenses.get(1).getAmount());
        assertTrue(loadedData.expenses.get(1) instanceof Transport);

        assertTrue(loadedData.expenses.get(2) instanceof Groceries);
        assertTrue(loadedData.expenses.get(3) instanceof Others);
    }

    @Test
    void load_nonExistentFile_returnsEmptyData() throws IOException {
        Storage storage = new Storage("non_existent_file.txt");
        Storage.StorageData data = storage.load();

        assertEquals(0.0, data.budget);
        assertTrue(data.expenses.isEmpty());
    }
}
