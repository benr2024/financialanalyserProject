package com.financeanalyser;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import com.financeanalyser.dao.TransactionDAO;
import com.financeanalyser.model.Transaction;
import com.financeanalyser.service.TransactionImportService;

import java.util.List;
import java.util.Scanner;


public class Main {

	private static TransactionDAO transactionDAO;
    private static final Scanner scanner = new Scanner(System.in);

    private static TransactionImportService transactionImportService = new TransactionImportService();

    public static void main(String[] args) throws SQLException, IOException {
    	
    	System.out.println("Working directory: " + System.getProperty("user.dir"));
        transactionDAO = new TransactionDAO();
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
			case "1":
				importTransactions();
				break;
			case "2":
				viewTransactions();
				break;
			case "3":
				viewTransactionsByDateRange();
				break;
            case "4":
                viewTransactionsByDescription();
                break;
            case "5":
                viewSummary();
                break;
			case "6":
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
        System.out.println("1. Import transactions from CSV file");
        System.out.println("2. View all transactions");
        System.out.println("3. View transactions filtered by date range");
        System.out.println("4. View transactions by transaction description");
        System.out.println("5. View summary");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

	//import transactions from CSV file
     private static void importTransactions() throws IOException, SQLException {
        System.out.print("Enter the path to the CSV file: ");
        String filePath = scanner.nextLine().trim();
        transactionImportService.isFileValid(filePath);
     }
        // Implement CSV import logic here


	 private static void viewTransactions() throws SQLException{
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        for (Transaction t : transactions) {
            System.out.println(t.toString());
        }
    }

    private static void viewSummary() throws SQLException{
        double paidIn = transactionDAO.getTotalPaidIn();
        double paidOut = transactionDAO.getTotalPaidOut();
        double balance = transactionDAO.getBalance();

        System.out.printf("Income: %.2f%n", paidIn);
        System.out.printf("Expenses: %.2f%n", paidOut);
        System.out.printf("Current Balance: %.2f%n", balance);
    }

    private static void viewTransactionsByDateRange() throws SQLException {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine().trim());
        System.out.print("Enter end date (YYYY-MM-DD): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine().trim());
        System.out.print("Enter the column to sort by (date, transactionType, description, paidOut, paidIn, balance): ");
        String sortby = scanner.nextLine().trim();
        System.out.print("Sort ascending? (true/false): ");
        boolean ascending = Boolean.parseBoolean(scanner.nextLine().trim());

        List<Transaction> transactions = transactionDAO.getTransactionsByDateRange(startDate, endDate, sortby, ascending);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found in the specified date range.");
            return;
        }
        for (Transaction t : transactions) {
            System.out.println(t.toString());
        }
    }

    private static void viewTransactionsByDescription() throws SQLException {
        System.out.print("Enter transaction description to filter by: ");
        String description = scanner.nextLine().trim();
        System.out.print("Enter the column to sort by (date, transactionType, description, paidOut, paidIn, balance): ");
        String sortby = scanner.nextLine().trim();
        System.out.print("Sort ascending? (true/false): ");
        boolean ascending = Boolean.parseBoolean(scanner.nextLine().trim());

        List<Transaction> transactions = transactionDAO.getTransactionsByDescription(description, sortby, ascending);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found with the specified description.");
            return;
        }
        for (Transaction t : transactions) {
            System.out.println(t.toString());
        }
    }



}
