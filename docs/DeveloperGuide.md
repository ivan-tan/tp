# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design 
### ExpenseManager

The `ExpenseManager` is responsible for managing expenses and budgets:

- **Expense Collection**: Maintains an `ArrayList<Expense>` of all expenses
- **Budget Tracking**: Tracks both global and category-specific budgets
- **Search Functionality**: Implements keyword-based search across expenses

Key methods include:
- `addExpense()`: Adds new expense with validation
- `deleteExpense()`: Removes expense by index with bounds checking
- `editExpense()`: Updates expense fields, allowing category changes
- `setBudget()` / `getBudget()`: Global budget management
- `setCategoryBudget()` / `getCategoryBudget()`: Category-specific budgets
- `getRemainingBudget()`: Calculates remaining global budget
- `getRemainingBudgetForCategory()`: Calculates remaining category budget
- `searchByKeyword()`: Case-insensitive search across descriptions and categories
- `getCategoryTotals()`: Get total expenses by category

![ExpenseManager Class Diagram](Diagrams/ExpenseManager.png)

*ExpenseManager class showing expense hierarchy and command relationships*
## Implementation

### Expense Management Features

The expense management system in ExpensiveLeh is implemented through the `ExpenseManager` class, which handles all expense-related operations including adding, deleting, editing, searching, and budget tracking.

#### Add Expense Feature

**Proposed Implementation**

The add expense feature is implemented through the `AddCommand` class and `ExpenseManager#addExpense()` method. The flow involves:

1. **Parser Phase**: The user input `expense add /c Food /n Lunch /v 10.50` is parsed to extract category, description, amount, and optionally date.

2. **Command Creation**: Based on the category, an appropriate `Expense` subclass is instantiated (e.g., `Food`, `Transport`, `Groceries`, `Others`).

3. **Execution**: The `AddCommand#execute()` method calls `ExpenseManager#addExpense()` to add the expense after validation.

4. **Validation**: The `ExpenseManager#addExpense()` method validates the expense object:
   - Checks that the expense is not null
   - Ensures amount is non-negative, finite, and not NaN
   - Uses assertions to verify the expense is added to the list

5. **Feedback**: The UI displays a success message with the expense details and remaining budget.

Example:

**Step 1.** The user launches the application and wants to add a new lunch expense. The user enters the command:
```
expense add /c Food /n Lunch /v 10.50
```

**Step 2.** The parser recognizes this as an add expense command and extracts:
- Category: "Food"
- Description: "Lunch"
- Amount: 10.50
- Date: Current date (default)

**Step 3.** The parser creates a `Food` expense object with these parameters and wraps it in an `AddCommand`.

**Step 4.** The `AddCommand#execute()` method is called, which invokes `ExpenseManager#addExpense(expense)`.

**Step 5.** The `ExpenseManager` validates the expense and adds it to the internal `expenses` ArrayList.

**Step 6.** The remaining budget is calculated using `getRemainingBudget()` and the UI displays:
```
Expense added successfully!
================================================
Category : Food
Name     : Lunch
Value    : $10.50
Date     : 01-04-2026
================================================
Remaining Budget: $489.50
```

The following sequence diagram shows how an add expense operation flows through the system:

![Add Expense Sequence Diagram](Diagrams/AddExpenseSequenceDiagram.png)

#### Delete Expense Feature

**Proposed Implementation**

The delete expense feature is implemented through the `DeleteCommand` class and `ExpenseManager#deleteExpense()` method. The implementation includes:

1. **Index-Based Deletion**: Expenses are deleted using a 1-based index as visible to users (internally converted to 0-based).

2. **Bounds Checking**: The `ExpenseManager#deleteExpense()` method validates the index before deletion:
   - Ensures index is non-negative
   - Ensures index is less than the expenses list size

3. **User Feedback**: The UI displays confirmation with the deleted expense's details.

**Example Usage Scenario:**

**Step 1.** The user lists expenses and sees:
```
1. Food | Lunch | $10.50 | 01-04-2026
2. Transport | MRT fare | $2.00 | 01-04-2026
```

**Step 2.** The user decides to delete the first expense and enters:
```
expense delete 1
```

**Step 3.** The parser creates a `DeleteCommand` with index 0 (converted from 1).

**Step 4.** The `DeleteCommand#execute()` calls `ExpenseManager#deleteExpense(0)`.

**Step 5.** The `ExpenseManager` validates the index (0 < 2, so valid) and removes the expense at index 0.

**Step 6.** The UI displays:
```
1: Food Lunch $10.50 01-04-2026 deleted!
```

The following sequence diagram shows how a delete expense operation flows through the system:

![Delete Expense Sequence Diagram](Diagrams/DeleteExpenseSequenceDiagram.png)

#### Edit Expense Feature

**Proposed Implementation**

The edit expense feature is implemented through the `EditCommand` class and `ExpenseManager#editExpense()` method. Key characteristics:

1. **Partial Updates**: Users can edit any combination of fields (category, description, amount, date). Unspecified fields retain their original values.

2. **Category Change Support**: When editing the category, a new expense object of the appropriate type is created and replaces the original.

3. **Validation**: The index is validated before editing, and assertions ensure data integrity.

**Example Usage Scenario:**

**Step 1.** The user wants to change the amount of expense at index 2 from $5.00 to $6.00:
```
expense edit 2 /v 6.00
```

**Step 2.** The `EditCommand#execute()` retrieves the current expense and creates a new one with the updated amount.

**Step 3.** The expense is replaced in the list using `expenses.set(index, newExpense)`.

**Step 4.** The UI confirms the update.

![Edit Expense Sequence Diagram](Diagrams/EditExpenseSequenceDiagram.png)

#### Budget Tracking Feature

**Proposed Implementation**

The budget tracking system supports both global and category-specific budgets:

1. **Global Budget**: Stored as a `double budget` field in `ExpenseManager`. The `getRemainingBudget()` method calculates:
   ```
   remainingBudget = budget - sum(all expense amounts)
   ```

2. **Category Budgets**: Stored in a `HashMap<String, Double>` mapping category names to budget limits. The `getRemainingBudgetForCategory()` method calculates:
   ```
   remainingCategoryBudget = categoryBudget - sum(expenses in that category)
   ```

3. **Case Insensitivity**: All category comparisons and keys are stored and compared in lowercase for consistency.

#### Design Considerations

**Aspect: Budget Storage**

**Alternative 1 (current choice):** Store global budget as `double` and category budgets as `HashMap<String, Double>`.
- *Pros*: Simple, straightforward implementation, easy to query
- *Cons*: No data persistence (budgets are not saved to files)

**Alternative 2:** Create a `Budget` class with methods for all budget operations.
- *Pros*: Encapsulates budget logic, easier to extend, allows persistence integration
- *Cons*: More complex design, additional class to maintain



## Product scope
### Target user profile

Busy students who want to manage their spending

### Value proposition

Students who are busy require an easy and convenient way to manage their finances. Our product serves as an easy way for
them to track their expenses so that they do not overspend their budgets.

## User Stories

| Version | As a ...  | I want to ...                                               | So that I can ...                                                  |
|---------|-----------|-------------------------------------------------------------|--------------------------------------------------------------------|
| v1.0    | user      | see my past expenses                                        | track my total expenditure                                         |
| v1.0    | user      | log an expense using a single command                       | record expenses quickly without navigating through multiple inputs |
| v2.0    | user      | add people who owe me money                                 | remember to chase them to return my money                          |
| v2.0    | user      | mark people who have returned money owed                    | stop chasing them for money                                        |
| v2.0    | user      | know my remaining budget immediately after logging expenses | know how much money I have saved                                   |
| v2.0    | lazy user | bookmark frequent expenses and add them                     | log them easily without typing everything out                      |

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
