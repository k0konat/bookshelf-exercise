/**
 * 
 */
package com.kkexample.craft.data.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.base.Strings;
import com.kkexample.craft.common.DateUtil;
import com.kkexample.craft.exception.MissingFieldException;

/**
 * The Class Book.
 *
 * @author k0konat
 */
@JsonDeserialize(builder = Book.Builder.class)
public final class Book extends Item {
	
	/** The author. For simplicity used as a string instead of a Person object*/
	private final List<String> author;
	
	/**
	 * Instantiates a new book.
	 *
	 * @param builder the builder
	 */
	private Book(Builder builder)
	{
		super(builder.title, builder.ISBN);		
		this.author = builder.author;	
	}
	
	/**
	 * Gets the builder.
	 *
	 * @return the builder
	 */
	public static Builder getBuilder() {
        return new Builder();
    }

	
	/* (non-Javadoc)
	 * @see com.intuit.craft.data.entity.Item#calcualteAndSetDueDate()
	 * Default behaviour has been provided for an Item but on the book, we have separate process compared to other items
	 * 
	 */
	@Override
	public LocalDateTime calcualteAndSetDueDate() {
		
		LocalDateTime dateTimeNow = LocalDateTime.now();
		LocalDateTime dueDate = DateUtil.addDays(dateTimeNow, 14);
		this.dueDate = dueDate;
		return super.dueDate;
	}
	
	/**
	 * Gets the author.For simplicity used as a string instead of a Person object
	 *
	 * @return the author
	 */
	public List<String> getAuthor() {
		return author;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		   StringBuffer sb = new StringBuffer();
		   sb.append(" id : " + this.getId());
		   sb.append(" title : " + this.getTitle());
		   sb.append(" ISBN : " + this.getISBN());
		   sb.append(" author :" + this.getAuthor());
		   sb.append(" borrower :" + this.getBorrower());
		   sb.append(" dueDate :" + (this.getDueDate()!=null? this.getDueDate().toString() : null));
		   sb.append(" isBorrowed" + this.isBorrowed());
		   return sb.toString();
	}
	
	 @Override
	 public boolean equals(Object o) {
	        // self check
	        if (this == o)
	            return true;
	        // null check
	        if (o == null)
	            return false;
	        // type check and cast
	        if (getClass() != o.getClass())
	            return false;
	        Book book = (Book) o;
	        // field comparison
	        return Objects.equals(super.id, book.getId());
	 }
	 
	 @Override
	 public int hashCode() {
	    return getId().hashCode();
	 }
	
	 /**
     * We don't have to use the builder pattern here because the constructed 
     * class has only three fields. However, I use the builder pattern 
     * in this because it makes the code a bit easier to read.Also, 
     * if you have any validation functions, 
     * it would be better to throw from a method rather than constructor.
     */
	@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
    public static class Builder {
    	
	    /** The title. */
	    private String title; 
    	
	    /** The isbn. */
	    private String ISBN;
    	
	    /** The author. For simplicity, just used string instead of Person Object */
	    private List<String> author;
    	 
    	/**
	     * Sets the title.
	     *
	     * @param title the title of the book
	     * @return the builder
	     */
	    public Builder setTitle(String title) {
    		this.title = title;
    		return this;
    	}

    	/**
	     * Sets the ISBN.
	     *
	     * @param ISBN the isbn of the book
	     * @return the builder
	     */
	    public Builder setISBN(String ISBN) {
    		this.ISBN = ISBN;
    		return this;
    	}
    	  	
    	/**
	     * Sets the author.
	     *
	     * @param author the author
	     * @return the builder
	     */
	    public Builder setAuthor(List<String> author) {
    		this.author = author;
    		return this;
    	}
    	 	    	
    	/**
	     * Creates the object for the book using builder pattern.
	     *
	     * @return the book
	     */
	    public Book build()
    	{
	    	if(Strings.isNullOrEmpty(this.title) || this.author.size() == 0  || Strings.isNullOrEmpty(this.ISBN) )
	    		throw new MissingFieldException("Missing field value");
	    	
    		return new Book(this);
    	}
   
    }
	

}
