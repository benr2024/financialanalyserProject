# Finance Analyser

Finance Analyser is a Java-based personal finance tracking application that helps you import bank transaction data, categorise spending, review transaction history, and get a quick overview of your finances.

The project stores data in an SQLite database and provides a simple command-line interface for common finance tasks.

## Features

- Import transactions from a CSV file
- Automatically categorise transactions into predefined categories
- View all saved transactions
- Filter transactions by date range
- Search transactions by description
- View total income, expenses, and current balance
- Optional Python analysis script for spending trend insights

## Supported categories

Transactions are grouped into the following categories:

- Groceries
- Shopping
- Transport
- Bills
- Entertainment
- Restaurants
- Health
- Subscriptions
- Other

## Tech stack

- Java
- Maven
- SQLite
- Apache Commons CSV
- Python analysis script (optional) using pandas, matplotlib, and scikit-learn

## Project structure

```text
finance-analyser/
├── analysis/
│   └── analysis.py
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/financeanalyser/
│   │   │       ├── dao/
│   │   │       ├── model/
│   │   │       ├── service/
│   │   │       └── util/
│   │   └── resources/
│   │       └── DBtable/
├── pom.xml
├── .gitignore
└── README.md
```

## Prerequisites

Before running the project, make sure you have:

- Java 21 or later
- Maven 3.8+
- A local SQLite database setup for the application

## Getting started

1. Clone the repository:

```bash
git clone https://github.com/benr2024/financialanalyserProject.git
cd finance-analyser
```

2. Build the project:

```bash
mvn clean compile
```

3. Run the application from your IDE or by launching the `Main` class with Maven/Java depending on your setup.

This project is currently a command-line application, not a JavaFX desktop app.

## How to use the app

When the application starts, you will see a menu with the following options:

1. Import transactions from CSV file
2. View all transactions
3. View transactions filtered by date range
4. View transactions by transaction description
5. View analysis
6. View summary
7. Exit

### Importing CSV data

The import feature expects transaction data in CSV format, including columns similar to the following:

- Date
- Transaction type
- Description
- Paid out
- Paid in
- Balance

The CSV parser reads the file and stores the entries in the SQLite database, using the description text to assign a category when possible.

## Database

The application uses SQLite via the `sqlite-jdbc` driver, and database access is managed in the `Database` utility and DAO classes.

The SQLite database is stored under:

```text
src/main/resources/DBtable/
```

## Optional analysis script

The project includes a Python script in `analysis/analysis.py` that:

- loads transaction data from SQLite
- groups spending by category
- calculates monthly spending totals
- predicts next month's spending using a simple linear regression model

This script is optional and requires Python dependencies such as:

```bash
pip install pandas matplotlib scikit-learn
```

## Notes

- The project is currently a console-based finance tracker rather than a full web or desktop UI.
- JavaFX was considered for future UI work, but it is not part of the current functional implementation.
- The app is designed for personal budgeting and transaction review rather than advanced accounting features.
- The categorisation logic is based on transaction descriptions and can be refined over time.

