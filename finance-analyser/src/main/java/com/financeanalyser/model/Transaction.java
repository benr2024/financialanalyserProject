package com.financeanalyser.model;
import java.time.LocalDate;

public class Transaction {
	
	private int id;
	private LocalDate date;
	private String description;
	private String category;
	private float amount;
	private Type type;
	public static enum Type {
		INCOME, EXPENSE
	}

	
	
	public Transaction(int id, LocalDate date, String description, String category, float amount, Type type) {
		super();
		this.id = id;
		this.date = date;
		this.description = description;
		this.category = category;
		this.amount = amount;
		this.type = type;
	}

	public Transaction(LocalDate date, String description, String category, float amount, Type type) {
		super();
		this.date = date;
		this.description = description;
		this.category = category;
		this.amount = amount;
		this.type = type;
	}


	public int getId() {
		return id;
	}


	public LocalDate getDate() {
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


	public Type getType() {
		return type;
	}

}
