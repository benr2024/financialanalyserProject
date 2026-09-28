package com.financeanalyser.model;
import java.time.LocalDate;

public class Transaction {
	
	private int id;
    private String date;
    private LocalDate properDate;
    private String transactionType;
    private String description;
    private double paidOut;
    private double paidIn;
    private double balance;

    public Transaction(int id, String date, String transactionType, String description, double paidOut, double paidIn, double balance) {
        this.id = id;
        this.date = date;
        this.properDate = convertDateFormat(date);
        this.transactionType = transactionType;
        this.description = description;
        this.paidOut = paidOut;
        this.paidIn = paidIn;
        this.balance = balance;
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
		String month = date.substring(3, 5);
		
		switch (month) {
		case "Jan":
			month = "01";
			break;
		case "Feb":
			month = "02";
			break;
		case "Mar":
			month = "03";
			break;
		case "Apr":
			month = "04";
			break;
		case "May":
			month = "05";
			break;
		case "Jun":
			month = "06";
			break;
		case "Jul":
			month = "07";
			break;
		case "Aug":
			
			month = "08";
			break;
		case "Sep":
			month = "09";
			break;
		case "Oct":
			month = "10";
			break;
		case "Nov":
			month = "11";
			break;
		case "Dec":
			month = "12";
			break;
					
		}
		LocalDate formatedDate = LocalDate.parse(date.substring(0,1) + month + date.substring(7, 10));
		return formatedDate;
		
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

	
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", date=" + date + ", transactionType=" + transactionType + ", description="
				+ description + ", paidOut=" + paidOut + ", paidIn=" + paidIn + ", balance=" + balance + "]";
	}
    
    
	

}
