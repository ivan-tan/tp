# Kanakorn Suk-ieam - Project Portfolio Page

## Overview

ExpensiveLeh is a CLI for managing your personal finances. Users can indicate their budget and add expenses into the app to track their budget situation. Features include expense management, budgeting, loans, bookmarks, and search functionality.

## Summary of Contributions

### Code contributed
[[RepoSense Link]](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=kanakornmek&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=KanakornMek&tabRepo=AY2526S2-CS2113-W11-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements implemented

**Feature: Implemented ExpenseManager with Budget Tracking**

I implemented a comprehensive expense management system that allows users to track their spending against budget limits.The implementation includes:

- Expense management with add, delete, edit, and search operations
- A polymorphic expense class hierarchy supporting different expense types
- Integration with the budget system for real-time tracking

**Feature: Budget and Category Budget Management**

Created a budgeting system that supports both global and category-specific budgets. This enhancement involved:

- Created `BudgetCommand` and `CategoryBudgetCommand` classes following the Command pattern
- Implemented `ListBudgetsCommand` to display all category budgets with remaining amounts
- Added validation to prevent negative budgets and empty category names
- Implemented automatic global budget adjustment when category budgets sum exceeds it

**Feature: Budget Violation Warnings**

Enhanced the user experience with budget violation warnings. This feature helps users stay within their financial limits by:

- Adding warning notifications when adding or editing expenses that would exceed budget limits
- Displaying clear messages about remaining budget amounts

### Bug fixes

During the PE-D phase, I actively fixed multiple reported bugs and issues to improve product quality and user experience:

- **Fix #132**: Removed contradictory statement about budget persistence in the Developer Guide

- **Fix #141**: Added budget violation warnings for add and edit expense commands and fixed category budget output format

- **Fix #128 and #130**: Reject duplicate `c/` and `a/` fields in budget command

- **Fix #140**: Notify user when invalid category is converted to "Others" in edit command

- **Fix #146**: Reject invalid format without `n/` and `a/` prefixes in edit command

- **Fix #113**: Reject NaN and Infinity values in amount validation

- **Budget Enhancement**: Update global budget when category budgets sum exceeds it

### Contributions to the User Guide

- Documented budget management features including:
  - Setting global budgets (`budget AMOUNT`)
  - Setting category-specific budgets (`budget c/CATEGORY a/AMOUNT`)
  - Listing all category budgets (`list budgets`)
- Documented expense management features including:
  - Adding expenses
  - Editing expenses
  - Deleting expenses
- Documented budget violation warnings feature
- Documented exit commands (`bye` and `quit`)

### Contributions to the Developer Guide

**Design Section:**
- Added documentation for the ExpenseManager component
- Created class diagrams showing the Expense superclass hierarchy
- Simplified Expense Manager class diagram for clarity

**Implementation Section:**
- Wrote the implementation details of expense management features:
  - Add Expense Feature with sequence diagrams
  - Delete Expense Feature with sequence diagrams
  - Edit Expense Feature with sequence diagrams
  - Budget Tracking Feature implementation details
- Included example usage scenarios for each feature
- Added budget manual testing instructions
- Examples of the diagrams added:
![ExpenseManager.png](../Diagrams/ExpenseManager.png)
![AddExpenseSequenceDiagram.png](../Diagrams/AddExpenseSequenceDiagram.png)


### Contributions to team-based tasks

- Set up GitHub team organization and repository
- Created and managed project issues on GitHub
- Actively fixes issues the team reported.

### Review/mentoring contributions

- Regularly reviewed team pull requests. Some examples: 
  - PR #[24](https://github.com/AY2526S2-CS2113-W11-3/tp/pull/24)
  - PR #[34](https://github.com/AY2526S2-CS2113-W11-3/tp/pull/34)
  - PR #[44](https://github.com/AY2526S2-CS2113-W11-3/tp/pull/44)
  - PR #[49](https://github.com/AY2526S2-CS2113-W11-3/tp/pull/49)
- Provided suggestions for improving the project architecture and user experience