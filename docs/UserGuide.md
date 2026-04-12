# User Guide - ExpensiveLeh Personal Finance Manager

ExpensiveLeh is a CLI for managing your personal finances. Users can indicate their budget and add expenses into the 
app to track their budget situation. This guide documents all features including expense management, budgeting, loans, 
bookmarks, and search functionality.

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `ExpensiveLeh.jar` from the project repository.
3. Open a terminal, navigate to the path containing `ExpensiveLeh.jar` and run `java -jar ExpensiveLeh.jar`. 

## Features

### Managing budgets : `budget`

Sets a budget for overall spending or for a specific category.

Format: `budget AMOUNT` or `budget c/CATEGORY a/AMOUNT`

*   Sets the global budget to `AMOUNT` if no category is specified.
*   Sets a category-specific budget if `c/CATEGORY a/AMOUNT` format is used.
*   Budget amounts must be positive.

Example: `budget 1000` sets the overall budget to $1000.

Output: 
```
________________________________________________________________________________
ExpensiveLeh says -> Budget of $1000.00 set successfully!
________________________________________________________________________________
```
    

Example: `budget c/Food a/300` sets the food category budget to $300.

Output:
```
________________________________________________________________________________
ExpensiveLeh says -> Budget of $300.00 set successfully for Food!
________________________________________________________________________________
```

Example: `budget c/Transport a/700` sets the transport category budget to $700.

Output:
```
________________________________________________________________________________
ExpensiveLeh says -> Budget of $700.00 set successfully for Transport!
________________________________________________________________________________
```

### Adding an expense: `add`
Adds a new expense to the expense list.

Format: `add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]`
* `CATEGORY` can be `Food`, `Transport`, `Groceries`, or `Others`.
* `NAME` can contain multiple words.
* `AMOUNT` must be a positive number.
* `DATE` is optional and defaults to today's date if not specified.

Example: `add c/Food n/Jollibee a/9.95 d/19-03-2026`

Output:
```
________________________________________________________________
ExpensiveLeh says -> Expense added successfully!
================================================
Category : Food
Name     : Jollibee
Value    : $9.95
Date     : 19-03-2026
================================================
Remaining Budget: $990.05
________________________________________________________________
```

Example: `add c/Food n/McDonald's a/12.50 d/20-03-2026`

Output:
```
________________________________________________________________
ExpensiveLeh says -> Expense added successfully!
================================================
Category : Food
Name     : McDonald's
Value    : $12.50
Date     : 20-03-2026
================================================
Remaining Budget: $977.55
________________________________________________________________
```

Example: `add c/Transport n/Plane Ticket a/200`

Output:
```
________________________________________________________________
ExpensiveLeh says -> Expense added successfully!
================================================
Category : Transport
Name     : Plane Ticket
Value    : $200.00
Date     : 30-03-2026
================================================
Remaining Budget: $777.55
________________________________________________________________
```

### Adding a loan: `add`
Adds a new loan to the loan list.    

Format: `add c/loan n/NAME a/AMOUNT [d/DD-MM-YYYY]`

Example: `add c/loan n/Jack a/100`

Output:
```
________________________________________________________________
ExpensiveLeh says -> Loan recorded successfully!
================================================
Debtor   : Jack
Amount   : $100.00
Date     : 30-03-2026
================================================
Total Owed to You: $100.00
________________________________________________________________
```
### Adding a bookmark: `bookmark`
Bookmarks an expense to the bookmark list.

Format: `bookmark INDEX`
* `INDEX` refers to the index number shown in `list expenses`.
* `INDEX` must be a positive integer.

Example: `bookmark 2`

Output:
```
________________________________________________________________
ExpensiveLeh says -> Successfully bookmarked: Food McDonald's $12.50
________________________________________________________________
```

### Adding a bookmark to expenses: `add`
Adds an existing bookmark to the expense list.

Format: `add bookmark INDEX`
* `INDEX` refers to the index number shown in `list bookmarks`.
* `INDEX` must be a positive integer.
* The expense date is automatically set as the user's local date.

Example: `add bookmark 1`

Assuming bookmark list contains:
```
________________________________________________________________
ExpensiveLeh says -> 
--- Bookmarks list ---
Index  Category     Name                 Value      Date        
1      Food         Noodles              $6.00      12-03-2026  
2      Food         McDonald's           $12.50     20-03-2026  

Use "add bookmark [index]" to add a bookmark to your expense list
________________________________________________________________
```

Output:
```
________________________________________________________________
ExpensiveLeh says -> Expense added successfully!
================================================
Category : Food
Name     : Noodles
Value    : $6.00
Date     : 12-03-2026
================================================
Remaining Budget: $771.55
________________________________________________________________
```

### Editing an expense: `edit`
Edits an existing expense in the expense list.

Format: `edit INDEX [c/CATEGORY] [n/NAME] [a/AMOUNT] [d/DD-MM-YYYY]`

* `INDEX` refers to the index number shown in `list expenses`.
* `INDEX` must be a positive integer.
* At least one field must be provided.
* Only the fields specified will be updated.

Example: `edit 2 n/McDonald's Meal a/15.00`

Output:
```
________________________________________________________________
ExpensiveLeh says -> Expense at index 2 updated successfully!
________________________________________________________________
```

### Editing a loan: `edit`
Edits an existing loan record in the loans list.

Format: `edit loan INDEX [n/NAME] [a/AMOUNT] [d/DD-MM-YYYY]`
* Edits the existing loan at the specified INDEX. The index refers to the index number shown in the displayed loans list.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Example: `edit loan 1 n/Jonathan a/55.00`

Output:
```
________________________________________________________________
ExpensiveLeh says -> Loan at index 1 updated successfully!
________________________________________________________________
```

### Deleting an expense: `delete`
Deletes the specified expense.

Format: `delete expense INDEX`
* Deletes the expense at the specified `INDEX`.
* The `INDEX` refers to the index number showed in the displayed expense list.
* The index must be a **positive integer** 1,2,3 ...

Example: `delete expense 1`

* Assuming expense list contains:
```
________________________________________________________________
ExpensiveLeh says -> 
Index  Category     Name                 Value      Date        
1      Food         Jollibee             $9.95      19-03-2026  
2      Food         McDonald's Meal      $15.00     20-03-2026  
3      Transport    Plane Ticket         $200.00    30-03-2026  
4      Food         Noodles              $6.00      12-03-2026  

Remaining budget: $769.05
________________________________________________________________
```

Output: 
```
________________________________________________________________
ExpensiveLeh says -> 1: Food Jollibee $9.95 19-03-2026 deleted!
________________________________________________________________
```

### Deleting a loan: `delete`
Deletes the specified loan.

Format: `delete loan INDEX`
* Deletes the person at the specified `INDEX`.
* The `INDEX` refers to the index number showed in the displayed loan list.
* The index must be a **positive integer** 1,2,3 ...

Example: `delete loan 2`

* Assuming loan list contains:
```
________________________________________________________________
ExpensiveLeh says -> 
--- Current Loans (Money Owed to You) ---
1    [OWED BY] Jonathan | $55.00 | 30-03-2026
2    [OWED BY] Joseph | $100.00 | 30-03-2026

Total Owed to You: $155.00
________________________________________________________________
```

Output:
```
________________________________________________________________
ExpensiveLeh says -> Loan ID 2 for Joseph ($100.00) deleted!
________________________________________________________________
```

### Deleting a bookmark: `delete`
Deletes the specified bookmark.

Format: `delete bookmark INDEX`
* Deletes the bookmark at the specified `INDEX`.
* The `INDEX` refers to the index number showed in the displayed bookmark list.
* The index must be a **positive integer** 1,2,3 ...

Example: `delete bookmark 1`

* Assuming expense list contains:
```
________________________________________________________________
ExpensiveLeh says -> 
--- Bookmarks list ---
Index  Category     Name                 Value      Date        
1      Food         Noodles              $6.00      12-03-2026  
2      Food         McDonald's           $12.50     20-03-2026  

Use "add bookmark [index]" to add a bookmark to your expense list
________________________________________________________________
```

Output:
```
________________________________________________________________
ExpensiveLeh says -> Bookmark ID 1 for Noodles ($6.00) deleted!
________________________________________________________________
```

### Listing all expenses, loans, bookmarks or budgets: `list`
Shows all existing expenses, loans or bookmarks in the list, as well as the remaining amount in the category budgets.

Format: `list [TYPE]` where TYPE can be `expenses`, `loans`, `bookmarks`, or `budgets`
* Use `list expenses` to display all expenses with their category, name, value, and date.
* Use `list loans` to display all recorded loans with their name, value, and date.
* Use `list bookmarks` to display all saved bookmarks with their category, name, value, and date.
* Use `list budgets` to display all the category budgets with their remaining amount.

Example: `list expenses`

Output:
```
________________________________________________________________
ExpensiveLeh says -> 
Index  Category     Name                 Value      Date        
1      Food         McDonald's Meal      $15.00     20-03-2026  
2      Transport    Plane Ticket         $200.00    30-03-2026  
3      Food         Noodles              $6.00      12-03-2026  

Remaining budget: $779.00
________________________________________________________________
```

Example: `list loans`

Output:
```
________________________________________________________________
ExpensiveLeh says -> 
--- Current Loans (Money Owed to You) ---
1    [OWED BY] Jonathan | $55.00 | 30-03-2026

Total Owed to You: $55.00
________________________________________________________________
```

Example: `list bookmarks`

Output:
```
________________________________________________________________
ExpensiveLeh says -> 
--- Bookmarks list ---
Index  Category     Name                 Value      Date        
1      Food         McDonald's           $12.50     20-03-2026  

Use "add bookmark [index]" to add a bookmark to your expense list
________________________________________________________________
```

Example: `list budgets`

Output:
```
________________________________________________________________
ExpensiveLeh says -> 
Category        Budget          Remaining      
--------------------------------------------------
Transport       $700.00         $500.00        
Food            $300.00         $279.00        

________________________________________________________________
```

### Searching for expenses and loans: `search`
Searches through both expenses and loans to find items matching a keyword

Format: `search KEYWORD`
* The search is case-insensitive, so searching for "food" will match "Food"
* Partial matches are included (e.g., searching "mc" will find "McDonald's")
* Results are displayed in separate sections for expenses and loans
* Both sections display formatted tables with index, category, name, value, and date

Example: `search mcdonald's`

Output:
```
________________________________________________________________
ExpensiveLeh says -> 

Search results for 'mcdonald's':

--- Expenses ---
Index  Category     Name                 Value      Date        
1      Food         McDonald's Meal      $15.00     20-03-2026  

________________________________________________________________
```

Example: `search john`

Output:
```
________________________________________________________________
ExpensiveLeh says -> 

Search results for 'john':

--- Loans ---
Index  Category     Name                 Value      Date        
1      Loan         John                 $50.00     20-03-2026  
2      Loan         Johnny's Pizza       $15.00     21-03-2026  

________________________________________________________________
```

Example: `search food`

Output:
```
________________________________________________________________
ExpensiveLeh says -> 

Search results for 'food':

--- Expenses ---
Index  Category     Name                 Value      Date        
1      Food         McDonald's Meal      $15.00     20-03-2026  
2      Food         Noodles              $6.00      12-03-2026  

________________________________________________________________
```

If no expenses or loans match the keyword: `search xyz`

Output:
```
________________________________________________________________
ExpensiveLeh says -> No expenses or loans found with keyword: 'xyz'
________________________________________________________________
```

### Rank expenses and loans: `rank`
Displays a visual bar chart ranking all your expense categories or who owes you the most money, from highest to lowest.

Format: `rank expenses` OR `rank loans`
* Use `rank expenses` to rank all spending by category.
* Use `rank loans` to rank all loans by amount owed.

Example: `rank expenses`

Output:
```
________________________________________________________________
ExpensiveLeh says -> Here is your spending ranked, by category: 

  1. Transport       [$  200.00] ████████████████████
  2. Food            [$   21.00] ██
________________________________________________________________
```

Example: `rank loans`

Output:
```
________________________________________________________________
ExpensiveLeh says -> Here are your loans ranked, by person: 

  1. Jack            [$  100.00] ████████████████████
  2. Jonathan        [$   55.00] ███████████
  3. Joseph          [$   23.00] ████
________________________________________________________________
```

### Help
Shows the summary of all commands and their formats

Format: `help`

* Use `help` at anytime to view the full list of commands.

Example: `help`

Output:
```
________________________________________________________________
ExpensiveLeh says ->
To add an expense, use 'add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]'. Eg: add c/Food n/Chicken Rice a/9.95
To add a loan, use 'add c/loan n/NAME a/AMOUNT [d/DD-MM-YYYY]'. Eg: add c/loan n/John Doe a/50.00
To add a bookmark to expenses, use 'add bookmark INDEX'. Eg: add bookmark 1
To edit an expense, use 'edit INDEX [c/CATEGORY] [n/NAME] [a/AMOUNT] [d/DD-MM-YYYY]'. Eg: edit 1 a/10.00
To edit a loan, use 'edit loan INDEX [n/NAME] [a/AMOUNT] [d/DD-MM-YYYY]'. Eg: edit loan 1 a/100.00
To delete an expense, use 'delete expense INDEX'. Eg: delete expense 1
To delete a loan, use 'delete loan INDEX'. Eg: delete loan 1
To delete a bookmark, use 'delete bookmark INDEX'. Eg: delete bookmark 1
To set a global budget, use 'budget AMOUNT'. Eg: budget 500.00
To set a category budget, use 'budget c/CATEGORY a/AMOUNT'. Eg: budget c/Food a/100.00
To list all expenses and remaining budget, use 'list expenses' 
To list all loans, use 'list loans' 
To list all bookmarks, use 'list bookmarks'
To list all category budgets, use 'list budgets'
To bookmark an expense, use 'bookmark INDEX'. Eg: bookmark 1
To search for expenses and loans, use 'search KEYWORD'. Eg: search chicken
To rank expenses by amount, use 'rank expenses'
To rank loans by amount, use 'rank loans'
To view all commands, use 'help'
To exit the program, use 'exit'
________________________________________________________________
```

### Saving Data
Loans, expenses, bookmarks and budgets are automatically saved in the disk. There is no need to save manually.

### Exit
Exits the program.

Format: `exit`

Output:
```
________________________________________________________________
ExpensiveLeh says -> Bye!
________________________________________________________________
```

### Frequently Asked Questions

**Q**: Is the search case-sensitive?

**A**: No, the search function is case-insensitive. Searching for "food" or "FOOD" will return the same results.

**Q**: Can I search by category?

**A**: Yes, searching for category names like "food", "transport", "groceries", or "others" will return all expenses in that category. Searching "loan" will return all loans.

**Q**: What if I search for a word that matches both description and category?

**A**: All matching expenses and loans will be returned, whether they match the description or the category.

## Command Summary

```

| Command                  | Format                                                       | Example                                         |
|--------------------------|--------------------------------------------------------------|-------------------------------------------------|
| Add expense              | `add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]`              | `add c/Food n/Chicken Rice a/9.95 d/30-03-2026` |
| Add loan                 | `add c/loan n/NAME a/AMOUNT [d/DD-MM-YYYY]`                  | `add c/loan n/John Doe a/50.00`                 |
| Add bookmark to expenses | `add bookmark INDEX`                                         | `add bookmark 1`                                |
| Edit expense             | `edit INDEX [c/CATEGORY] [n/NAME] [a/AMOUNT] [d/DD-MM-YYYY]` | `edit 1 n/Chicken Rice a/10.00`                 |
| Edit loan                | `edit loan INDEX [n/NAME] [a/AMOUNT] [d/DD-MM-YYYY]`         | `edit loan 1 a/100.00`                          |
| Delete expense           | `delete expense INDEX`                                       | `delete expense 1`                              |
| Delete loan              | `delete loan INDEX`                                          | `delete loan 1`                                 |
| Delete bookmark          | `delete bookmark INDEX`                                      | `delete bookmark 1`                             |
| Set global budget        | `budget AMOUNT`                                              | `budget 500.00`                                 |
| Set category budget      | `budget c/CATEGORY a/AMOUNT`                                 | `budget c/Food a/100.00`                        |
| List expenses            | `list expenses`                                              | `list expenses`                                 |
| List loans               | `list loans`                                                 | `list loans`                                    |
| List bookmarks           | `list bookmarks`                                             | `list bookmarks`                                |
| List budgets             | `list budgets`                                               | `list budgets`                                  |
| Bookmark an expense      | `bookmark INDEX`                                             | `bookmark 1`                                    |
| Search expenses          | `search KEYWORD`                                             | `search chicken`                                |
| Rank expenses            | `rank expenses`                                              | `rank expenses`                                 |
| Rank loans               | `rank loans`                                                 | `rank loans`                                    |
| Help                     | `help`                                                       | `help`                                          |
| Exit                     | `exit`                                                       | `exit`                                          |

```