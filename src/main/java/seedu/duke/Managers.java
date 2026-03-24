package seedu.duke;

import loans.LoanManager;

public class Managers {
    private final ExpenseManager expenseManager;
    private final LoanManager loanManager;

    public Managers(ExpenseManager em, LoanManager lm) {
        this.expenseManager = em;
        this.loanManager = lm;
    }

    public ExpenseManager getExpenseManager() {
        return expenseManager;
    }

    public LoanManager getLoanManager() {
        return loanManager;
    }
}
