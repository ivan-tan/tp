# User Guide - Expense Search Feature

## Introduction

ExpensiveLeh is a CLI for managing your personal finances. Users can indicate their budget and add expenses into the app to track their budget situation. This guide documents the Expense data model and Search functionality.

## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `ExpensiveLeh` from the project repository.
3. Expenses are organized into categories: Food, Transport, Groceries, and Others.
4. Each expense contains a description, amount, and date

## Features

### Searching for Expenses: `search`

Searches through all expenses to find items matching a keyword. The search is case-insensitive and looks through both the expense description and category.

Format: `search KEYWORD`
* The search is case-insensitive, so searching for "food" will match "Food"
* Partial matches are included (e.g., searching "joy" will find "Jollibee")
* Results are displayed in a formatted table with index, category, name, value, and date

Format: `add c/loan n/NAME a/AMOUNT [date/DATE]`

Example of usage: 

`search jollibee`

ExpensiveLeh output:
```
________________________________________________________________
ExpensiveLeh says -> 
Search results for 'jollibee':
Index  Category     Name                 Value      Date
1      Food         Jollibee             $9.95      19-02-2026
________________________________________________________________
```

`search food`

ExpensiveLeh output:
```
________________________________________________________________
ExpensiveLeh says -> 
Search results for 'food':
Index  Category     Name                 Value      Date
1      Food         Jollibee             $9.95      19-02-2026
3      Food         McDonald's           $12.50     20-02-2026
________________________________________________________________
```

If no expenses match the keyword:

`search xyz`

ExpensiveLeh output:
```
________________________________________________________________
ExpensiveLeh says -> No expenses found with keyword: 'xyz'
________________________________________________________________
```

## Loans
ExpensiveLeh can keep track of people who owe you money.

### Adding a loan: `add`
Adds a new loan.

`add c/loan n/Jack a/100`

Output:
````
ExpensiveLeh says -> Loan recorded successfully!
Debtor   : Jack
Amount   : $100.00
Date     : 30-03-2026
Total Owed to You: $2433.00
________________________________________________________________
````

### Listing a loan: `list`
Lists all loans.

Format: `list loans`

Output:
````
ExpensiveLeh says -> 
--- Current Loans (Money Owed to You) ---
1    [OWED BY] Kim | $2333.00 | 24-03-2026
2    [OWED BY] Jack | $100.00 | 30-03-2026

Total Owed to You: $2433.00
________________________________________________________________
````

### Deleting a loan: `delete`
Deletes the specified loan.
Format: `delete INDEX`
* Deletes the person at the specified `INDEX`. 
* The `INDEX` refers to the index number showed in the displayed loan list.
* The index must be a **positive intege**r 1,2,3 ...

### Managing budgets : `budget`

Sets a budget for overall spending or for a specific category.

Format: `budget AMOUNT` or `budget c/CATEGORY a/AMOUNT`

*   Sets the global budget to `AMOUNT` if no category is specified.
*   Sets a category-specific budget if `c/CATEGORY a/AMOUNT` format is used.
*   Budget amounts must be positive.

`delete loan 1`

Output:
`ExpensiveLeh says -> Loan ID 1 for Kim ($2333.00) deleted!`

## Saving Data
Loans, expenses, bookmarks and budgets are automatically saved in the disk. There is no need to save manually. 

Examples:

*   `budget 1000`
    ```
    ________________________________________________________________________________
    ExpensiveLeh says -> Budget of $1000.00 set successfully!
    ________________________________________________________________________________
    ```
    Sets the overall budget to $1000.

*   `budget c/Food a/500`
    ```
    ________________________________________________________________________________
    ExpensiveLeh says -> Budget of $500.00 set successfully for Food!
    ________________________________________________________________________________
    ```
    Sets the food category budget to $500.


## FAQ

**Q**: Is the search case-sensitive?

**A**: No, the search function is case-insensitive. Searching for "food" or "FOOD" will return the same results.

**Q**: Can I search by category?

**A**: Yes, searching for category names like "food", "transport", "groceries", or "others" will return all expenses in that category.

**Q**: What if I search for a word that matches both description and category?

**A**: All matching expenses will be returned, whether they match the description or the category.

## Command Summary

* Search expenses: `search KEYWORD`
