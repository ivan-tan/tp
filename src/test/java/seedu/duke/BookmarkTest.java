package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import storage.Bookmark;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class BookmarkTest {

    private Managers managers;
    private UI ui;
    private ExpenseManager expenseManager;
    private Bookmark bookmark;

    @BeforeEach
    void setUp() {
        expenseManager = new ExpenseManager();
        bookmark = new Bookmark("data/test-bookmarks.txt");
        managers = new Managers(expenseManager, new loans.LoanManager(), bookmark);
        ui = new UI();
    }

    @AfterEach
    void tearDown() {
        File file = new File("data/test-bookmarks.txt");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void saveAndLoad_foodPersistedCorrectly() throws Exception {
        bookmark.addBookmark(new Food("Chicken Rice", 5.00, LocalDate.of(2026, 1, 1)));
        bookmark.save();

        Bookmark loaded = new Bookmark("data/test-bookmarks.txt");
        loaded.load();

        assertEquals(1, loaded.getBookmarks().size());
        assertEquals("Chicken Rice", loaded.getBookmarks().get(0).getDescription());
        assertEquals(5.00, loaded.getBookmarks().get(0).getAmount());
        assertEquals(LocalDate.of(2026, 1, 1), loaded.getBookmarks().get(0).getDate());
        assertInstanceOf(Food.class, loaded.getBookmarks().get(0));
    }

    @Test
    void saveAndLoad_transportPersistedCorrectly() throws Exception {
        bookmark.addBookmark(new Transport("Bus", 1.50, LocalDate.of(2026, 1, 1)));
        bookmark.save();

        Bookmark loaded = new Bookmark("data/test-bookmarks.txt");
        loaded.load();

        assertEquals(1, loaded.getBookmarks().size());
        assertEquals("Bus", loaded.getBookmarks().get(0).getDescription());
        assertEquals(1.50, loaded.getBookmarks().get(0).getAmount());
        assertEquals(LocalDate.of(2026, 1, 1), loaded.getBookmarks().get(0).getDate());
        assertInstanceOf(Transport.class, loaded.getBookmarks().get(0));
    }

    @Test
    void saveAndLoad_groceriesPersistedCorrectly() throws Exception {
        bookmark.addBookmark(new Groceries("Eggs", 3.00, LocalDate.of(2026, 1, 1)));
        bookmark.save();

        Bookmark loaded = new Bookmark("data/test-bookmarks.txt");
        loaded.load();

        assertEquals(1, loaded.getBookmarks().size());
        assertEquals("Eggs", loaded.getBookmarks().get(0).getDescription());
        assertEquals(3.00, loaded.getBookmarks().get(0).getAmount());
        assertEquals(LocalDate.of(2026, 1, 1), loaded.getBookmarks().get(0).getDate());
        assertInstanceOf(Groceries.class, loaded.getBookmarks().get(0));
    }

    @Test
    void saveAndLoad_othersPersistedCorrectly() throws Exception {
        bookmark.addBookmark(new Others("Misc", 2.00, LocalDate.of(2026, 1, 1)));
        bookmark.save();

        Bookmark loaded = new Bookmark("data/test-bookmarks.txt");
        loaded.load();

        assertEquals(1, loaded.getBookmarks().size());
        assertEquals("Misc", loaded.getBookmarks().get(0).getDescription());
        assertEquals(2.00, loaded.getBookmarks().get(0).getAmount());
        assertEquals(LocalDate.of(2026, 1, 1), loaded.getBookmarks().get(0).getDate());
        assertInstanceOf(Others.class, loaded.getBookmarks().get(0));
    }

    @Test
    void saveAndLoad_multipleExpenseTypes_allPersistedCorrectly() throws Exception {
        bookmark.addBookmark(new Food("Chicken Rice", 5.00, LocalDate.of(2026, 1, 1)));
        bookmark.addBookmark(new Transport("Bus", 1.50, LocalDate.of(2026, 1, 2)));
        bookmark.addBookmark(new Groceries("Eggs", 3.00, LocalDate.of(2026, 1, 3)));
        bookmark.addBookmark(new Others("Misc", 2.00, LocalDate.of(2026, 1, 4)));
        bookmark.save();

        Bookmark loaded = new Bookmark("data/test-bookmarks.txt");
        loaded.load();

        assertEquals(4, loaded.getBookmarks().size());
        assertInstanceOf(Food.class, loaded.getBookmarks().get(0));
        assertInstanceOf(Transport.class, loaded.getBookmarks().get(1));
        assertInstanceOf(Groceries.class, loaded.getBookmarks().get(2));
        assertInstanceOf(Others.class, loaded.getBookmarks().get(3));
    }

    @Test
    void execute_validIndex_bookmarksExpense() throws ExpensiveLehException {
        Expense expense = new Food("Chicken Rice", 5.00, LocalDate.now());
        expenseManager.addExpense(expense);

        BookmarkCommand command = new BookmarkCommand(0);
        command.execute(managers, ui);

        ArrayList<Expense> bookmarks = bookmark.getBookmarks();
        assertEquals(1, bookmarks.size());
        assertEquals("Chicken Rice", bookmarks.get(0).getDescription());
        assertEquals(5.00, bookmarks.get(0).getAmount());
        assertInstanceOf(Food.class, bookmarks.get(0));
    }

    @Test
    void execute_invalidIndex_throwsExpensiveLehException() {
        BookmarkCommand command = new BookmarkCommand(0);
        assertThrows(ExpensiveLehException.class, () -> command.execute(managers, ui));
    }

    @Test
    void execute_negativeIndex_throwsExpensiveLehException() {
        BookmarkCommand command = new BookmarkCommand(-1);
        assertThrows(ExpensiveLehException.class, () -> command.execute(managers, ui));
    }

    @Test
    void execute_multipleExpenses_bookmarksCorrectOne() throws ExpensiveLehException {
        expenseManager.addExpense(new Food("Chicken Rice", 5.00, LocalDate.now()));
        expenseManager.addExpense(new Transport("Bus", 1.50, LocalDate.now()));
        expenseManager.addExpense(new Food("Mee Goreng", 4.00, LocalDate.now()));

        BookmarkCommand command = new BookmarkCommand(1);
        command.execute(managers, ui);

        assertEquals("Bus", bookmark.getBookmarks().get(0).getDescription());
    }

    @Test
    void removeBookmark_validIndex_removesCorrectly() throws ExpensiveLehException {
        bookmark.addBookmark(new Food("Chicken Rice", 5.00, LocalDate.now()));
        bookmark.removeBookmark(0);
        assertEquals(0, bookmark.getBookmarks().size());
    }

    @Test
    void removeBookmark_negativeIndex_throwsExpensiveLehException() {
        assertThrows(ExpensiveLehException.class, () -> bookmark.removeBookmark(-1));
    }

    @Test
    void removeBookmark_indexTooLarge_throwsExpensiveLehException() {
        assertThrows(ExpensiveLehException.class, () -> bookmark.removeBookmark(1));
    }

    @Test
    void getBookmark_validIndex_returnsCorrectExpense() throws ExpensiveLehException {
        bookmark.addBookmark(new Food("Chicken Rice", 5.00, LocalDate.now()));
        assertEquals("Chicken Rice", bookmark.getBookmark(0).getDescription());
        assertEquals(5.00, bookmark.getBookmark(0).getAmount());
        assertInstanceOf(Food.class, bookmark.getBookmark(0));
    }

    @Test
    void getBookmark_negativeIndex_throwsExpensiveLehException() {
        assertThrows(ExpensiveLehException.class, () -> bookmark.getBookmark(-1));
    }

    @Test
    void getBookmark_indexTooLarge_throwsExpensiveLehException() {
        assertThrows(ExpensiveLehException.class, () -> bookmark.getBookmark(1));
    }
}
