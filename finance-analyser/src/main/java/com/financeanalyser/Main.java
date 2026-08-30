package com.financeanalyser;

import java.sql.Connection; 
import java.sql.SQLException;

import com.financeanalyser.dao.TransactionDAO;
import com.financeanalyser.util.Database;


public class Main {

    public static void main(String[] args) {
    	
    	try (Connection connection = Database.connect()) {

            System.out.println("Database connected!");
    	
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        
    }
     

}
