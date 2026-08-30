package com.financeanalyser.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    
    
	public static Connection connect() throws SQLException {
		final String URL = "jdbc:sqlite:" + "src/main/resources/finance.db";
        return DriverManager.getConnection(URL);
        
    }
	
	
	
}
