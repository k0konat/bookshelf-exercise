/**
 * 
 */
package com.intuit.craft.data.entity;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.intuit.craft.common.DateUtil;

/**
 * @author k0konat
 *
 */
@JsonDeserialize(builder = Book.Builder.class)
public final class Book extends Item {
	
	private final List<String> author;
	
	private Book(Builder builder)
	{
		
		super(builder.title, builder.ISBN);		
		this.author = builder.author;
			
		
	}
	
	public static Builder getBuilder() {
        return new Builder();
    }

	
	@Override
	public LocalDateTime calcualteAndSetDueDate() {
		
		LocalDateTime dateTimeNow = LocalDateTime.now();
		LocalDateTime dueDate = DateUtil.addDays(dateTimeNow, 14);
		this.dueDate = dueDate;
		return super.dueDate;
	}
	
	public List<String> getAuthor() {
		return author;
	}
	
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
	
	
	 /**
     * We don't have to use the builder pattern here because the constructed 
     * class has only five fields. However, I use the builder pattern 
     * in this because it makes the code a bit easier to read.
     */
	@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
    public static class Builder {
    	private String title; 
    	private String ISBN;
    	private List<String> author;
    	 
    	public Builder setTitle(String title) {
    		this.title = title;
    		return this;
    	}

    	public Builder setISBN(String ISBN) {
    		this.ISBN = ISBN;
    		return this;
    	}
    	  	
    	public Builder setAuthor(List<String> author) {
    		this.author = author;
    		return this;
    	}
    	 	    	
    	public Book build()
    	{
    		//add validation for required fields
    		return new Book(this);
    	}
	
 
    }
	

}
