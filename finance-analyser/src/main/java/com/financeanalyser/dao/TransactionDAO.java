package com.financeanalyser.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.financeanalyser.model.Transaction;
import com.financeanalyser.util.Database;


public class TransactionDAO {
	
	private final Connection connection;
	
	public TransactionDAO() throws SQLException {
        connection = Database.connect();
    }

	public int insertTransaction(String date, String description, String category, float amount, String type) throws SQLException {
		String statement = "INSERT INTO Transactions (date, description, category, amount, type) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ps = connection.prepareStatement(statement);
		ps.setString(1, date);
		ps.setString(2, description);
		ps.setString(3, category);
		ps.setFloat(4, amount);
		ps.setString(5, type);
		ps.executeUpdate();
		
		ResultSet rs = ps.getGeneratedKeys();
		
		if(rs.next()) {
			return rs.getInt(1);
		}
		throw new SQLException("Failed to create Player: no generated ID returned.");
		
	}
	
	public List<Transaction> getAllTransactions() throws SQLException{
		
		List<Transaction> transactions = new ArrayList<>();
		
		String statement = "SELECT * FROM Transactions";
		
		PreparedStatement ps = connection.prepareStatement(statement);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			transactions.add(new Transaction(
					rs.getInt("id"),
					rs.getString("date"),
					rs.getString("description"),
					rs.getString("category"),
					rs.getFloat("amount"),
					rs.getString("type")));
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

}
