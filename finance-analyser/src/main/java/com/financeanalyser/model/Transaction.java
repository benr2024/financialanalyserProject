package com.financeanalyser.model;

public class Transaction {
	
	private int id;
	private String date;
	private String description;
	private String category;
	private float amount;
	private String type;
	
	
	public Transaction(int id, String date, String description, String category, float amount, String type) {
		super();
		this.id = id;
		this.date = date;
		this.description = description;
		this.category = category;
		this.amount = amount;
		this.type = type;
	}


	public int getId() {
		return id;
	}


	public String getDate() {
		return date;
	}


	public String getDescription() {
		return description;
	}


	public String getCategory() {
		return category;
	}


	public float getAmount() {
		return amount;
	}


	public String getType() {
		return type;
	}

}
