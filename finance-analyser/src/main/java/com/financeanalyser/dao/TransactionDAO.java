package com.financeanalyser.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.financeanalyser.model.Category;
import com.financeanalyser.model.Transaction;
import com.financeanalyser.service.Categoriser;
import com.financeanalyser.util.Database;


public class TransactionDAO {
	
	private final Connection connection;
	
	public TransactionDAO() throws SQLException {
        connection = Database.connect();
    }

	public int insertTransaction(String date, String transactionType, String description, double paidOut, double paidIn, double balance) throws SQLException {
		String statement = "INSERT INTO Transactions (date, properDate, transactionType, description, paidOut, paidIn, balance, Category) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = connection.prepareStatement(statement);
		LocalDate properDate = new Transaction().convertDateFormat(date);
		Categoriser categoriser = new Categoriser();
		Category category = categoriser.categorise(description);
		ps.setString(1, date);
		ps.setString(2, properDate.toString());
		ps.setString(3, transactionType);
		ps.setString(4, description);
		ps.setDouble(5, paidOut);
		ps.setDouble(6, paidIn);
		ps.setDouble(7, balance);
		ps.setString(8, category.toString());
		ps.executeUpdate();
		
		ResultSet rs = ps.getGeneratedKeys();
		
		if(rs.next()) {
			return rs.getInt(1);
		}
		throw new SQLException("Failed to create Transaction: no generated ID returned.");
		
	}
	
	public List<Transaction> getAllTransactions() throws SQLException{
		
		List<Transaction> transactions = new ArrayList<>();
		
		String statement = "SELECT * FROM Transactions";
		
		PreparedStatement ps = connection.prepareStatement(statement);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			//create a new Transaction object from the result set
			Transaction transaction = new Transaction(
					rs.getInt("id"),
					rs.getString("date"),
					rs.getString("transactionType"),
					rs.getString("description"),
					rs.getDouble("paidOut"),
					rs.getDouble("paidIn"),
					rs.getDouble("balance")
			);
			transactions.add(transaction);
		}
		return transactions;
	}

	public List<Transaction> getAllOtherCategory() throws SQLException{
		
		List<Transaction> transactions = new ArrayList<>();
		
		String statement = "SELECT * FROM Transactions WHERE Category = 'OTHER'";
		
		PreparedStatement ps = connection.prepareStatement(statement);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			//create a new Transaction object from the result set
			Transaction transaction = new Transaction(
					rs.getInt("id"),
					rs.getString("date"),
					rs.getString("transactionType"),
					rs.getString("description"),
					rs.getDouble("paidOut"),
					rs.getDouble("paidIn"),
					rs.getDouble("balance")
			);
			transactions.add(transaction);
		}
		return transactions;
	}
	
	public int deleteTransactionById(int id) throws SQLException {
		String statement = "DELETE FROM Transactions WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setInt(1, id);
		int rowsdeleted = ps.executeUpdate();
		
		return rowsdeleted;
		
	}

	public double getTotalPaidOut() throws SQLException {
		String statment = "SELECT SUM(paidOut) FROM Transactions";
		PreparedStatement ps = connection.prepareStatement(statment);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return rs.getDouble(1);
		}
		return 0.0;
	}

	public double getTotalPaidIn() throws SQLException {
		String statment = "SELECT SUM(paidIn) FROM Transactions";
		PreparedStatement ps = connection.prepareStatement(statment);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return rs.getDouble(1);
		}
		return 0.0;
	}

	public double getBalance() throws SQLException {
		String statment = "SELECT balance FROM Transactions ORDER BY id DESC LIMIT 1";
		PreparedStatement ps = connection.prepareStatement(statment);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return rs.getDouble(1);
		}
		return 0.0;
	}

	public List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate, String sortby, int ascending) throws SQLException {
		List<Transaction> transactions = new ArrayList<>();
		boolean asc = false;
		switch(sortby) {
			case "1":
				sortby = "properDate";
				break;
			case "2":
				sortby = "paidOut";
				break;
			case "3":
				sortby = "paidIn";
				break;
		}
		switch(ascending) {
			case 1:
				asc = true;
				break;
			case 2:
				asc = false;
				break;
			
		}
		String statement = "SELECT * FROM Transactions WHERE properDate BETWEEN ? AND ? ORDER BY " + sortby + " " + (asc ? "ASC" : "DESC");
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setString(1, startDate.toString());
		ps.setString(2, endDate.toString());
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Transaction transaction = new Transaction(
					rs.getInt("id"),
					rs.getString("date"),
					rs.getString("transactionType"),
					rs.getString("description"),
					rs.getDouble("paidOut"),
					rs.getDouble("paidIn"),
					rs.getDouble("balance")
			);
			transactions.add(transaction);
		}
		return transactions;
	}

	public List<Transaction> getTransactionsByDescription(String description, String sortby, int ascending) throws SQLException {
		List<Transaction> transactions = new ArrayList<>();
		boolean asc = false;
		switch(sortby) {
			case "1":
				sortby = "properDate";
				break;
			case "2":
				sortby = "paidOut";
				break;
			case "3":
				sortby = "paidIn";
				break;
		}
		switch(ascending) {
			case 1:
				asc = true;
				break;
			case 2:
				asc = false;
				break;
			
		}
		String statement = "SELECT * FROM Transactions WHERE description LIKE ? ORDER BY " + sortby + " " + (asc ? "ASC" : "DESC");
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setString(1, "%" + description + "%");
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Transaction transaction = new Transaction(
					rs.getInt("id"),
					rs.getString("date"),
					rs.getString("transactionType"),
					rs.getString("description"),
					rs.getDouble("paidOut"),
					rs.getDouble("paidIn"),
					rs.getDouble("balance")
			);
			transactions.add(transaction);
		}
		return transactions;
	}

	public void updateTransactionCategory(Integer id, String category) throws SQLException {
		
		String statement = "UPDATE Transactions SET category = ? WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setString(1, category);
		ps.setInt(2, id);
		ps.executeUpdate();
	}


}