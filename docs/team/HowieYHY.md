# Howie Yeo Hao Yan - Project Portfolio Page

## Overview

ExpensiveLeh is a CLI for managing your personal finances. Users can indicate their budget and add expenses into the app to track their budget situation. Features include expense management, budgeting, loans, bookmarks, and comprehensive search functionality.

## Summary of Contributions

### Code contributed

[[RepoSense Link]](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=HowieYHY&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=HowieYHY&tabRepo=AY2526S2-CS2113-W11-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code&authorshipIsIgnoredFilesChecked=false)

### Enhancements implemented

**New feature:** Designed Expense Superclass

- Designed the abstract `Expense` superclass with protected attributes (`description`, `amount`, `date`) and abstract method `getCategory()` to support polymorphic handling of different expense types.
- Implemented common methods (`getDescription()`, `getCategory()`, `getAmount()`, `getDate()`, `getFormattedDate()`) enabling all expense types to work uniformly through a single interface.
- Created concrete subclasses (`Food`, `Transport`, `Groceries`, `Others`) each implementing their specific category while inheriting common functionality, allowing new expense categories to be added without modifying existing code.
- Extended `Loan` class to also inherit from `Expense`, enabling unified handling of both expenses and loans through the common interface and allowing them to be treated interchangeably in collections and operations.

**New feature:** Search Function

- Implemented comprehensive search functionality allowing users to find both expenses and loans by keyword with case-insensitive matching across descriptions and categories (e.g., searching "chicken" finds "Chicken Rice" expense and "Chicken Dinner Catering" loan).
- Created `searchByKeyword()` method in `LoanManager` that mirrors the expense search pattern: converts keywords to lowercase, iterates through loans, and matches items where description or category contains the keyword, returning an `ArrayList<Loan>`.
- Enhanced `SearchCommand` to perform parallel searches: calls both `ExpenseManager#searchByKeyword()` and `LoanManager#searchByKeyword()` independently, then combines results into organized sections ("--- Expenses ---" and "--- Loans ---") for cohesive presentation.
- Ensured unified user experience: identical case-insensitive and partial matching behavior across both data types, consistent output formatting with index, category, name, value, and date columns, and unified error handling displaying "No expenses or loans found with keyword: 'xyz'" when neither type has matches.

### Contributions to the User Guide

- **Command documentation**, including:
  - Documented `add`, `edit`, `delete` commands for expenses, loans, and bookmarks with examples
  - Documented `list` command showing all types (expenses, loans, bookmarks, budgets) with output formatting

- **Search and Help sections**, including:
  - Documented search feature with multiple examples covering expense-only, loan-only, and mixed searches
  - Documented help command with all command formats and FAQ addressing search behavior

### Contributions to the Developer Guide

- **Core design sections**, including:
  - Designed and documented Expense Superclass with design structure, rationale, and concrete subclasses
  - Documented ExpenseManager class with key methods and responsibilities

- **Search Feature implementation**, including:
  - Documented Search Feature with dual-manager architecture, parallel execution, and unified results design
  - Created UML diagrams: `Expense.puml`, `SearchCommandDiagram.puml` showing complete search flow with both managers

### Contributions to team-based tasks

- Performed comprehensive testing and verification of the search feature across multiple scenarios
- Identified and resolved checkstyle violations to ensure code quality

### Review/mentoring contributions

- Conducted code review on the search feature implementation
- Verified documentation accuracy across User Guide and Developer Guide
- Ensured UML diagrams accurately represented implementation details
