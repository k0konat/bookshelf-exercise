package com.intuit.craft.data.entity;

import java.time.LocalDateTime;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

public abstract class Item {
	
	private final UUID id;	
	private final String title;
	private final String ISBN;
	
	protected String borrower;
	@JsonFormat(pattern = "yyyy::MM::dd::KK:mm a")
	protected LocalDateTime dueDate;
	protected Boolean borrowed = false;
	
	Item(String title,
			String ISBN)
	{
		this.id = UUID.randomUUID();
		this.title = title;
		this.ISBN = ISBN;		
	}

	//book, cd, music, dvd has different due dates
	public LocalDateTime calcualteAndSetDueDate()
	{
		LocalDateTime dateTimeNow = LocalDateTime.now();
		LocalDateTime dueDate = com.intuit.craft.common.DateUtil.addDays(dateTimeNow, 21);
		this.dueDate = dueDate;
		return this.dueDate;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public Boolean isBorrowed() {
		return borrowed;
	}

	public void setBorrowed(Boolean borrowed) {
		this.borrowed = borrowed;
	}

	public UUID getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}


	public String getISBN() {
		return ISBN;
	}	
	
}
