package com.financeanalyser;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import com.financeanalyser.dao.TransactionDAO;
import com.financeanalyser.model.Category;
import com.financeanalyser.model.Transaction;
import com.financeanalyser.service.Categoriser;
import com.financeanalyser.service.TransactionImportService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {

	private static TransactionDAO transactionDAO;
    private static final Scanner scanner = new Scanner(System.in);

    private static TransactionImportService transactionImportService = new TransactionImportService();

    public static void main(String[] args) throws SQLException, IOException {

        
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
                viewanalysis();
                break;
            case "6":
                viewSummary();
                break;
			case "7":
				running = false;
				break;
			default:
				System.out.println("Invalid option, try again.");
		}
        }

        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n--- Personal Finance Analysy er ---");
        System.out.println("1. Import transactions from CSV file");
        System.out.println("2. View all transactions");
        System.out.println("3. View transactions filtered by date range");
        System.out.println("4. View transactions by transaction description");
        System.out.println("5. View analysis");
        System.out.println("6. View summary");
        System.out.println("7. Exit");
        System.out.print("Choose an option: ");
    }

	//import transactions from CSV file
     private static void importTransactions() throws IOException, SQLException {
        System.out.print("Enter the path to the CSV file: ");
        String filePath = scanner.nextLine().trim();
        transactionImportService.isFileValid(filePath);


        Map<String, Category> categorisedDescriptions = new HashMap<>();
        List<Transaction> otherTransactions = transactionDAO.getAllOtherCategory();
        for (Transaction t : otherTransactions) {

            if(categorisedDescriptions.containsKey(t.getDescription())) {
                Category category = categorisedDescriptions.get(t.getDescription());
                t.setCategory(category);
                //update the category in the database
                transactionDAO.updateTransactionCategory(t.getId(), category.toString());

            }else{

                System.out.println(t.getDescription() + " transaction couldnt be catgorised");
                System.out.print("(1) Groceries");
                System.out.print("(2) Shopping");
                System.out.print("(3) Transport");
                System.out.print("(4) Bills");
                System.out.print("(5) Entertainment");
                System.out.print("(6) Restaurants");
                System.out.print("(7) Health");
                System.out.print("(8) Subscriptions");
                System.out.print("(9) Other");
                String category = scanner.nextLine().trim();
               
                Categoriser.convertFromOtherToCategory(category, t);

                //update the category in the database
                transactionDAO.updateTransactionCategory(t.getId(), t.getCategory().toString());

                categorisedDescriptions.put(t.getDescription(), t.getCategory());

            }

        }
    }

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

    private static void viewanalysis() throws IOException {
        String scriptPath = System.getProperty("user.dir") + "/analysis/analysis.py";
        String pythonPath = System.getProperty("user.dir") + "/venv/Scripts/python.exe"; 
        ProcessBuilder processBuilder = new ProcessBuilder(pythonPath, scriptPath);
        processBuilder.inheritIO();
        Process process = processBuilder.start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

        
    private static void viewTransactionsByDateRange() throws SQLException {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine().trim());
        System.out.print("Enter end date (YYYY-MM-DD): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine().trim());
        System.out.print("Enter the column to sort by (Date(1), Paid out(2), Paid in(3)): ");
        String sortby = scanner.nextLine().trim();
        System.out.print("Sort ascending? (Asceding(1)/Descending(2)): ");
        int ascending = Integer.parseInt(scanner.nextLine().trim());

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
        System.out.print("Enter the column to sort by (Date(1), Paid out(2), Paid in(3)): ");
        String sortby = scanner.nextLine().trim();
        System.out.print("Sort ascending? (Asceding(1)/Descending(2)): ");
        int ascending = Integer.parseInt(scanner.nextLine().trim());

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
