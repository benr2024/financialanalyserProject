package com.financeanalyser.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.financeanalyser.dao.TransactionDAO;
import com.financeanalyser.model.Transaction;

import java.io.FileReader;
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.sql.SQLException;

public class TransactionImportService {

    public boolean isFileValid(String filePath) throws IOException, SQLException {
    	String cleanFilePath = filePath.replace("\"", "");
        if (cleanFilePath != null && cleanFilePath.toLowerCase().endsWith(".csv")){
            readCSV(cleanFilePath);
        } else {
            throw new IllegalArgumentException("File does not exist or has an invalid format. Please provide a CSV file.");
        }
        return false;
    }

    private void readCSV(String filePath) throws IOException, SQLException {
        List<Transaction> transactions = new ArrayList<>();
        StringBuilder sb = new StringBuilder(); 

        try(FileReader reader = new FileReader(filePath)){
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            boolean headerFound = false;
            while ((line = bufferedReader.readLine()) != null){
                if(!headerFound){
                    if(line.contains("Date")){
                        headerFound = true;
                        sb.append(line).append("\n");
                    }
                } else {
                sb.append(line).append("\n");
                }
            }
        }
            

        try (CSVParser csvParser = new CSVParser(new StringReader(sb.toString()), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            TransactionDAO transactionDAO = new TransactionDAO();

            for (CSVRecord record : csvParser) {
                String date = (record.get("Date"));
                String transactionType = record.get("Transaction type");
                String description = record.get("Description");
                double paidOut = parseAmount(record.get("Paid out"));
                double paidIn = parseAmount(record.get("Paid in"));
                double balance = parseAmount(record.get("Balance"));

                transactionDAO.insertTransaction(date, transactionType, description, paidOut, paidIn, balance);
            }
        }
    }
    
    private double parseAmount(String value) {
        if (value == null || value.isEmpty()) {
            return 0.0;
        }
        String cleaned = value.trim()
        		.replace(",", "")
                .replace("£", "")
                .replace("$", "")
                .replace("€", "")
                .trim();
       if(cleaned.isEmpty()) {
    	   return 0.0;
       }
        return Double.parseDouble(cleaned);
    }
 }
