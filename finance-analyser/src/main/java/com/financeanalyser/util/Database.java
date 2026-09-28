package com.financeanalyser.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public static final String URL = "jdbc:sqlite:src/main/resources/DBtable/finance.db";
    
	public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
        
    }
	
	
}
