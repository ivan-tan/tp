# User Guide - Expense Search Feature

ExpensiveLeh is a CLI for managing your personal finances. Users can indicate their budget and add expenses into the app to track their budget situation. This guide documents the Expense data model and Search functionality.


1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `ExpensiveLeh` from the project repository.
3. Expenses are organized into categories: Food, Transport, Groceries, and Others.
4. Each expense contains a description, amount, and date

## Features

### Searching for Expenses: `search`

Format: `search KEYWORD`
* The search is case-insensitive, so searching for "food" will match "Food"
* Partial matches are included (e.g., searching "joy" will find "Jollibee")
* Results are displayed in a formatted table with index, category, name, value, and date

Example: `search jollibee`

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

### Deleting a loan: `delete`
Deletes the specified loan.

Format: `delete loan INDEX`
* Deletes the person at the specified `INDEX`.
* The `INDEX` refers to the index number showed in the displayed loan list.
* The index must be a **positive intege**r 1,2,3 ...

Example: `delete loan 1`

Output:
`ExpensiveLeh says -> Loan ID 1 for Kim ($2333.00) deleted!`

### Managing budgets : `budget`

Sets a budget for overall spending or for a specific category.

Format: `budget AMOUNT` or `budget c/CATEGORY a/AMOUNT`

*   Sets the global budget to `AMOUNT` if no category is specified.
*   Sets a category-specific budget if `c/CATEGORY a/AMOUNT` format is used.
*   Budget amounts must be positive.

### Saving Data
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

### Listing all expenses, loans, bookmarks or budgets
Shows all existing expenses, loans or bookmarks in the list, as well as the remaining amount in the category budgets.

Format: `list expenses` OR `list loans` OR `list bookmarks` OR `list budgets`

Searches through all expenses to find items matching a keyword. The search is case-insensitive and looks through both the expense description and category.
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
1      Food         Chicken              $23.00     30-03-2026
2      Transport    Plane ticket         $123.00    30-03-2026

Remaining budget: $54.00
________________________________________________________________
```

Example: `list loans`

Output:
```
________________________________________________________________
ExpensiveLeh says -> 
--- Current Loans (Money Owed to You) ---
1    [OWED BY] John | $30.00 | 30-03-2026

Total Owed to You: $30.00
________________________________________________________________
```

Example: `list bookmarks`

Output:
```
________________________________________________________________
ExpensiveLeh says -> 
--- Bookmarks list ---
Index  Category     Name                 Value      Date        
1      Food         Noodles              $6.00      25-03-2026  
2      Food         Noodles              $6.00      25-03-2026  
3      Food         Chicken              $23.00     30-03-2026  

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
Transport       $200.00         $77.00         
Food            $50.00          $27.00         

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
To add an expense, use 'add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]'. Eg: add c/Food n/Chicken Rice a/9
To add a loan, use 'add c/loan n/NAME a/AMOUNT [d/DD-MM-YYYY]'. Eg: add c/loan n/John Doe a/50
To add a bookmark to expenses, use 'add bookmark INDEX'. Eg: add bookmark 1
To edit an expense, use 'edit INDEX [c/CATEGORY] [n/NAME] [a/AMOUNT] [d/DD-MM-YYYY]'. Eg: edit 1 a/10
To edit a loan, use 'edit loan INDEX [n/NAME] [a/AMOUNT] [d/DD-MM-YYYY]'. Eg: edit loan 1 a/100.00
To delete an expense, use 'delete expense INDEX'. Eg: delete expense 1
To delete a loan, use 'delete loan INDEX'. Eg: delete loan 1
To delete a bookmark, use 'delete bookmark INDEX'. Eg: delete bookmark 1
To mark a loan as paid, use 'paid INDEX'. Eg: paid 1
To set a global budget, use 'budget AMOUNT'. Eg: budget 500
To set a category budget, use 'budget c/CATEGORY a/AMOUNT'. Eg: budget c/Food a/100
To list all expenses and remaining budget, use 'list expenses'
To list all loans, use 'list loans'
To list all bookmarks, use 'list bookmarks'
To list all category budgets, use 'list budgets'
To bookmark an expense, use 'bookmark INDEX'. Eg: bookmark 1
To search for an expense, use 'search KEYWORD'. Eg: search chicken
To rank expenses by amount, use 'rank expenses'
To rank loans by amount, use 'rank loans'
To view all commands, use 'help'
To exit the program, use 'exit'
________________________________________________________________
```

### <u>Ranking expenses</u>
Displays a visual bar chart ranking all your spending categories from highest to lowest.

Format: `rank expenses`

Output:
```text
  ExpensiveLeh says -> Here is your spending ranked by category:

  1. XXX            [$  300.00] ████████████████████
  2. YYY            [$  150.00] ██████████
  3. AAA            [$   75.00] █████
  4. BBB            [$   10.00] █
  ```
Example: `rank expenses`

```text
  ExpensiveLeh says -> Here is your spending ranked by category:

  1. Food            [$  300.00] ████████████████████
  2. Transport       [$  150.00] ██████████
  3. Groceries       [$   75.00] █████
  4. Others          [$   10.00] █
  ```


### <u>Editing a loan</u>
Edits an existing loan record in your loans list.

Format: `edit loan INDEX [n/PERSON_NAME] [a/AMOUNT] [d/DATE]`
* Edits the existing loan at the specified INDEX. The index refers to the index number shown in the displayed loans list.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Output: Loan at index INDEX updated successfully!

Examples:
* edit loan 1 n/Jonathan a/55.00
* Loan at index 1 updated successfully!

### <u>Ranking loans</u>
Displays a visual bar chart ranking who owes you the most money, from highest to lowest.

Format: `rank loans`

Output:
```text
  1. AAA            [$  300.00] ████████████████████
  2. BBB            [$  150.00] ██████████
  3. CCC            [$   75.00] █████
  4. DDD            [$   10.00] █
  ```
Example: `rank loans`
```text
  ExpensiveLeh says -> Here are your loans ranked by person:

  1. Ali            [$  300.00] ████████████████████
  2. Bob            [$  150.00] ██████████
  3. Eli            [$   75.00] █████
  4. Lex            [$   10.00] █
  ```

### <u>Exit</u>
Exits the program.

Format: exit

### <u>Frequently Asked Questions</u>

**Q**: Is the search case-sensitive?

**A**: No, the search function is case-insensitive. Searching for "food" or "FOOD" will return the same results.

**Q**: Can I search by category?

**A**: Yes, searching for category names like "food", "transport", "groceries", or "others" will return all expenses in that category.

**Q**: What if I search for a word that matches both description and category?

**A**: All matching expenses will be returned, whether they match the description or the category.

## Command Summary
| Command                  | Format                                                       | Example                                      |
|--------------------------|--------------------------------------------------------------|----------------------------------------------|
| Add expense              | `add c/CATEGORY n/NAME a/AMOUNT [d/DD-MM-YYYY]`              | `add c/Food n/Chicken Rice a/5 d/30-03-2026` |
| Add loan                 | `add c/loan n/NAME a/AMOUNT [d/DD-MM-YYYY]`                  | `add c/loan n/John Doe a/50`                 |
| Add bookmark to expenses | `add bookmark INDEX`                                         | `add bookmark 1`                             |
| Edit expense             | `edit INDEX [c/CATEGORY] [n/NAME] [a/AMOUNT] [d/DD-MM-YYYY]` | `edit 1 n/Chicken Rice a/6`                  |
| Edit loan                | `edit loan INDEX [n/NAME] [a/AMOUNT] [d/DD-MM-YYYY]`         | `edit loan 1 a/100`                          |
| Delete expense           | `delete expense INDEX`                                       | `delete expense 1`                           |
| Delete loan              | `delete loan INDEX`                                          | `delete loan 1`                              |
| Delete bookmark          | `delete bookmark INDEX`                                      | `delete bookmark 1`                          |
| Mark loan as paid        | `paid INDEX`                                                 | `paid 1`                                     |
| Set global budget        | `budget AMOUNT`                                              | `budget 500`                                 |
| Set category budget      | `budget c/CATEGORY a/AMOUNT`                                 | `budget c/Food a/100`                        |
| List expenses            | `list expenses`                                              | `list expenses`                              |
| List loans               | `list loans`                                                 | `list loans`                                 |
| List bookmarks           | `list bookmarks`                                             | `list bookmarks`                             |
| List budgets             | `list budgets`                                               | `list budgets`                               |
| Bookmark an expense      | `bookmark INDEX`                                             | `bookmark 1`                                 |
| Search expenses          | `search KEYWORD`                                             | `search chicken`                             |
| Rank expenses            | `rank expenses`                                              | `rank expenses`                              |
| Rank loans               | `rank loans`                                                 | `rank loans`                                 |
| Help                     | `help`                                                       | `help`                                       |
| Exit                     | `exit`                                                       | `exit`                                       |

