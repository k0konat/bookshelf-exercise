package com.intuit.craft.data.entity;

import java.time.LocalDateTime;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * The Class Item. This class is used to represent any item in the Library like Book, Music CD, DVD video etc.
 * 
 * @author k0konat.
 */
public abstract class Item {
	
	/** The id. Unique Identifier*/
	protected final UUID id;	
	
	/** The title. */
	private final String title;
	
	/** The isbn. */
	private final String ISBN;
	
	/** The borrower. For simplicity used String instead of Person object */
	protected String borrower;
	
	/** The due date. */
	@JsonFormat(pattern = "yyyy::MM::dd::KK:mm a")
	protected LocalDateTime dueDate;
	
	/** The borrowed. */
	protected Boolean borrowed = false;
	
	/**
	 * Instantiates a new item.
	 *
	 * @param title the title
	 * @param ISBN the isbn
	 */
	Item(String title,
			String ISBN)
	{
		this.id = UUID.randomUUID();
		this.title = title;
		this.ISBN = ISBN;		
	}

	/**
	 * Calcualte and set due date.
	 * Book, CD, Music, DVD has different due dates
	 * @return the local date time
	 */
	
	public LocalDateTime calcualteAndSetDueDate()
	{
		LocalDateTime dateTimeNow = LocalDateTime.now();
		LocalDateTime dueDate = com.intuit.craft.common.DateUtil.addDays(dateTimeNow, 21);
		this.dueDate = dueDate;
		return this.dueDate;
	}

	/**
	 * Gets the borrower.
	 *
	 * @return the borrower
	 */
	public String getBorrower() {
		return borrower;
	}

	/**
	 * Sets the borrower.
	 *
	 * @param borrower the new borrower
	 */
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	/**
	 * Gets the due date.
	 *
	 * @return the due date
	 */
	public LocalDateTime getDueDate() {
		return dueDate;
	}

	/**
	 * Sets the due date.
	 *
	 * @param dueDate the new due date
	 */
	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * Checks if is borrowed.
	 *
	 * @return the boolean
	 */
	public Boolean isBorrowed() {
		return borrowed;
	}

	/**
	 * Sets the borrowed.
	 *
	 * @param borrowed the new borrowed
	 */
	public void setBorrowed(Boolean borrowed) {
		this.borrowed = borrowed;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * Gets the isbn.
	 *
	 * @return the isbn
	 */
	public String getISBN() {
		return ISBN;
	}	
	
}
