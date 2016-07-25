package com.intuit.craft.domain.requests;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;

public class AddBookRequest {
	
	private String title;
	private String ISBN;
	private List<String> author;
	
	public String getTitle() {
		return title;
	}
	@Required
	public void setTitle(String title) {
		this.title = title;
	}
	public String getISBN() {
		return ISBN;
	}
	@Required
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public List<String> getAuthor() {
		return author;
	}
	
	@Required
	public void setAuthor(List<String> author) {
		this.author = author;
	}
	
	
	

}
