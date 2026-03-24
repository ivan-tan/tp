package seedu.duke;

import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.Map;

public class ExpenseManager {
    private final ArrayList<Expense> expenses;
    private double budget;
    private HashMap<String, Double> categoryBudgets;

    public ExpenseManager() {
        this.expenses = new ArrayList<>();
        this.budget = 0.0;
        this.categoryBudgets = new HashMap<>();
    }

    public ExpenseManager(ArrayList<Expense> expenses, double budget) {
        assert expenses != null : "Expenses list cannot be null";
        assert budget >= 0 : "Budget cannot be negative";
        this.expenses = expenses;
        this.budget = budget;
        this.categoryBudgets = new HashMap<>();
    }

    public ExpenseManager(ArrayList<Expense> expenses, double budget, HashMap<String, Double> categoryBudgets) {
        assert expenses != null : "Expenses list cannot be null";
        assert budget >= 0 : "Budget cannot be negative";
        this.expenses = expenses;
        this.budget = budget;
        this.categoryBudgets = categoryBudgets != null ? categoryBudgets : new HashMap<>();
    }

    public void addExpense(Expense expense) throws ExpensiveLehException {
        if (expense == null) {
            throw new ExpensiveLehException("Expense cannot be null.");
        }

        assert expense.getAmount() >= 0 : "Expense amount should not be negative";
        assert !Double.isNaN(expense.getAmount()) : "Expense amount should not be NaN";
        assert Double.isFinite(expense.getAmount()) : "Expense amount should be finite";

        expenses.add(expense);

        assert expenses.contains(expense) : "Expense should be added to list";
    }

    public Expense deleteExpense(int index) throws ExpensiveLehException {
        if (index < 0 || index >= expenses.size()) {
            throw new ExpensiveLehException("Expense ID " + (index + 1) + " doesn't exist.");
        }
        return expenses.remove(index);
    }

    public void editExpense(int index, String category, String name, Double value, LocalDate date)
            throws ExpensiveLehException {
        if (index < 0 || index >= expenses.size()) {
            throw new ExpensiveLehException("Expense ID " + (index + 1) + " doesn't exist.");
        }

        Expense currentExpense = expenses.get(index);
        String finalName = name != null ? name : currentExpense.getDescription();
        Double finalValue = value != null ? value : currentExpense.getAmount();
        LocalDate finalDate = date != null ? date : currentExpense.getDate();
        String finalCategory = category != null ? category : currentExpense.getCategory();

        Expense newExpense;
        switch (finalCategory.toLowerCase()) {
        case "food":
            newExpense = new Food(finalName, finalValue, finalDate);
            break;
        case "transport":
            newExpense = new Transport(finalName, finalValue, finalDate);
            break;
        case "groceries":
            newExpense = new Groceries(finalName, finalValue, finalDate);
            break;
        default:
            newExpense = new Others(finalName, finalValue, finalDate);
        }

        expenses.set(index, newExpense);
    }

    public void setBudget(double budget) throws ExpensiveLehException {
        if (budget < 0) {
            throw new ExpensiveLehException("Budget cannot be negative.");
        }

        this.budget = budget;

        assert !Double.isNaN(this.budget) : "Budget should never be NaN";
        assert Double.isFinite(this.budget) : "Budget should be a finite number";
    }

    public double getBudget() {
        return budget;
    }

    public double getRemainingBudget() {
        double totalExpenses = 0.0;
        for (Expense expense : expenses) {
            totalExpenses += expense.getAmount();
        }
        return budget - totalExpenses;
    }

    public Expense getExpense(int index) {
        return expenses.get(index);
    }

    public int getSize() {
        return expenses.size();
    }

    public boolean isEmpty() {
        return expenses.isEmpty();
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void setCategoryBudget(String category, double amount) throws ExpensiveLehException {
        if (category == null || category.trim().isEmpty()) {
            throw new ExpensiveLehException("Category cannot be empty.");
        }
        if (amount < 0) {
            throw new ExpensiveLehException("Category budget cannot be negative.");
        }

        categoryBudgets.put(category.toLowerCase(), amount);
    }

    public double getCategoryBudget(String category) {
        return categoryBudgets.getOrDefault(category.toLowerCase(), 0.0);
    }

    public double getRemainingBudgetForCategory(String category) {
        String categoryLower = category.toLowerCase();
        double budgetForCategory = categoryBudgets.getOrDefault(categoryLower, 0.0);
        double spentInCategory = 0.0;

        for (Expense expense : expenses) {
            if (expense.getCategory().equalsIgnoreCase(categoryLower)) {
                spentInCategory += expense.getAmount();
            }
        }

        return budgetForCategory - spentInCategory;
    }

    public HashMap<String, Double> getCategoryBudgets() {
        return categoryBudgets;
    }

    public Map<String, Double> getCategoryTotals() {
        Map<String, Double> map = new HashMap<>();

        for (Expense expense: expenses) {
            String category = expense.getCategory();
            double amount = expense.getAmount();

            map.put(category, map.getOrDefault(category, 0.0) + amount);
        }
        return map;
    }

    public ArrayList<Expense> searchByKeyword(String keyword) {
        ArrayList<Expense> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        
        for (Expense expense : expenses) {
            if (expense.getDescription().toLowerCase().contains(lowerKeyword) ||
                expense.getCategory().toLowerCase().contains(lowerKeyword)) {
                results.add(expense);
            }
        }
        
        return results;
    }
}
