package com.financeanalyser;

import java.sql.SQLException;
import java.time.LocalDate;

import com.financeanalyser.dao.TransactionDAO;
import com.financeanalyser.model.Transaction;

import java.util.List;
import java.util.Scanner;


public class Main {

	private static TransactionDAO transactionDAO;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        transactionDAO = new TransactionDAO();
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
			case "1":
				addTransaction();
				break;
			case "2":
				viewTransactions();
				break;
			case "3":
				viewSummary();
				break;
			case "4":
				deleteTransaction();
				break;
			case "5":
				running = false;
				break;
			default:
				System.out.println("Invalid option, try again.");
		}
        }

        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n--- Personal Finance Analyser ---");
        System.out.println("1. Add transaction");
        System.out.println("2. View all transactions");
        System.out.println("3. View summary");
        System.out.println("4. Delete a transaction");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

	public static void addTransaction() throws SQLException {
		System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Category: ");
        String category = scanner.nextLine().trim();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("Type (INCOME/EXPENSE): ");
        Transaction.Type type = Transaction.Type.valueOf(scanner.nextLine().trim().toUpperCase());
		transactionDAO.insertTransaction(LocalDate.now(), description, category, (float) amount, type);
        System.out.println("Saved!");
    }

	 private static void viewTransactions() throws SQLException{
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    private static void viewSummary() throws SQLException{
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        double income = 0;
        double expense = 0;

        for (Transaction t : transactions) {
            if (t.getType() == Transaction.Type.INCOME) {
                income += t.getAmount();
            } else {
                expense += t.getAmount();
            }
        }

        System.out.printf("Income: %.2f%n", income);
        System.out.printf("Expenses: %.2f%n", expense);
        System.out.printf("Balance: %.2f%n", income - expense);
    }

    private static void deleteTransaction() throws SQLException {
        viewTransactions();
        System.out.print("Enter the id of the transaction to delete: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        transactionDAO.deleteTransactionById(id);
        System.out.println("Deleted (if it existed).");
    }

}
