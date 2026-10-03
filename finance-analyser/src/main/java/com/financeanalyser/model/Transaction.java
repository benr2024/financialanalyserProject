package com.financeanalyser.model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.financeanalyser.service.Categoriser;

public class Transaction {
	
	private int id;
    private String date;
    private LocalDate properDate;
    private String transactionType;
    private String description;
    private double paidOut;
    private double paidIn;
    private double balance;
    private Category category;

    public Transaction(int id, String date, String transactionType, String description, double paidOut, double paidIn, double balance) {
        this.id = id;
        this.date = date;
        this.properDate = convertDateFormat(date);
        this.transactionType = transactionType;
        this.description = description;
        this.paidOut = paidOut;
        this.paidIn = paidIn;
        this.balance = balance;
        this.category = Categoriser.categorise(description);
    }

	public Category getCategory() {
		return category;
	}

	public Transaction() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public String getDate() {
		return date;
	}
	
	public String getTransactionType() {
		return transactionType;
	}

	public String getDescription() {
		return description;
	}
	
	public LocalDate convertDateFormat(String date) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM uuuu", Locale.ENGLISH);
	    return LocalDate.parse(date.trim(), formatter);
	}

	public double getPaidOut() {
		return paidOut;
	}

	public double getPaidIn() {
		return paidIn;
	}

	public double getBalance() {
		return balance;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", date=" + date + ", properDate=" + properDate + ", transactionType="
				+ transactionType + ", description=" + description + ", paidOut=" + paidOut + ", paidIn=" + paidIn
				+ ", balance=" + balance + ", category=" + category + "]";
	}

	
    
    
	

}
