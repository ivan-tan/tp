package loans;

import seedu.duke.ExpensiveLehException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the list of loans to others
 */
public class LoanManager {
    private final ArrayList<Loan> loans;

    public LoanManager() {
        this.loans = new ArrayList<>();
    }

    public LoanManager(ArrayList<Loan> loans) {
        assert loans != null : "Loans list cannot be null";
        this.loans = loans;
    }

    public void addLoan(Loan loan) throws ExpensiveLehException {
        if (loan == null) {
            throw new ExpensiveLehException("Loan entry cannot be null.");
        }

        assert loan.getAmount() >= 0 : "Debt amount should not be negative";

        loans.add(loan);
    }

    public Loan deleteLoan(int index) throws ExpensiveLehException {
        if (index < 0 || index >= loans.size()) {
            throw new ExpensiveLehException("Debt ID " + (index + 1) + " doesn't exist.");
        }
        return loans.remove(index);
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public boolean isEmpty() {
        return loans.isEmpty();
    }

    public double getTotalAmountLent() {
        double total = 0.0;
        for (Loan loan : loans) {
            total += loan.getAmount();
        }
        return total;
    }

    public Map<String, Double> getPersonTotals() {
        Map<String, Double> map = new HashMap<>();

        for (Loan loan : loans) {
            String person = loan.getDescription();
            double amount = loan.getAmount();

            map.put(person, map.getOrDefault(person, 0.0) + amount);
        }

        return map;
    }

    public void editLoan(int index, String name, Double value, LocalDate date) throws ExpensiveLehException {
        if (index < 0 || index >= loans.size()) {
            throw new ExpensiveLehException("Loan ID " + (index + 1) + " doesn't exist yet");
        }

        Loan currentLoan = loans.get(index);

        String finalName = name != null ? name: currentLoan.getDescription();
        Double finalValue = value != null ? value: currentLoan.getAmount();
        LocalDate finalDate = date != null ? date: currentLoan.getDate();

        Loan newLoan = new Loan(finalName, finalValue, finalDate);
        loans.set(index, newLoan);
    }
}
