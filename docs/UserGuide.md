# User Guide

## Introduction

{Give a product intro}

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 17 or above installed.
1. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features 

{Give detailed description of each feature}

### Managing budgets : `budget`

Sets a budget for overall spending or for a specific category.

Format: `budget AMOUNT` or `budget c/CATEGORY a/AMOUNT`

*   Sets the global budget to `AMOUNT` if no category is specified.
*   Sets a category-specific budget if `c/CATEGORY a/AMOUNT` format is used.
*   Budget amounts must be positive.

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

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
