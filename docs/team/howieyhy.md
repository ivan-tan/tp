# Howie Yeo Hao Yu - Project Portfolio Page

## Overview

ExpensiveLeh is a CLI for managing your personal finances. Users can indicate their budget and add expenses into the app to track their budget situation. Features include expense management, budgeting, loans, bookmarks, and comprehensive search functionality.

## Summary of Contributions

### Code contributed

[[RepoSense Link]](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=HowieYHY&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=HowieYHY&tabRepo=AY2526S2-CS2113-W11-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code&authorshipIsIgnoredFilesChecked=false)

### Enhancements implemented

**New feature:** Designed Expense Superclass
- Created abstract `Expense` superclass with polymorphic support for different expense types (Food, Transport, Groceries, Others)
- Implemented common methods enabling all expense types to work uniformly through a single interface
- Extended `Loan` class to inherit from `Expense`, enabling unified handling of both expenses and loans

**New feature:** Search Function
- Implemented comprehensive search functionality for expenses and loans by keyword with case-insensitive matching
- Created `searchByKeyword()` method in `LoanManager` mirroring the expense search pattern
- Enhanced `SearchCommand` to perform parallel searches and combine results into organized sections

**Bug Fix:** Search Result Index Mismatch
- Fixed bug where search results displayed incorrect indices, causing delete/edit commands to target wrong expenses
- Modified `SearchCommand` to display actual expense indices

**Enhancement:** UML Diagram Standardization
- Audited all UML class diagrams and updated 8 diagrams to comply with standard UML 2.0 notation
- Replaced custom relationship labels with standard UML arrows and added proper multiplicity notation

**Enhancement:** Expense Class Hierarchy Diagram Redesign
- Redesigned Expense.puml for improved readability with 50% larger scale and reorganized structure

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
