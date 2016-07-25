package com.intuit.craft.data.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.intuit.craft.data.entity.Book;

/**
 * The Class BookData.
 *
 * @author k0konat
 */
public final class BookData {
	/**
	 * Based on the requirements, Book store can be in the class Bookshelf 
	 * but just used it in a separate object to mimic backend database. 
	 */
	public static final Map<UUID, Book> bookStore;
	
	static
	{
		//added 5 books initially to the book store. Used Builder pattern to create the book objects.
		bookStore = new ConcurrentHashMap<UUID, Book>();
		
		List<String> book1Author = new ArrayList<String>();
		book1Author.add("Eben Hewitt");
		Book book1 = new Book.Builder().setTitle("Cassandra: The Definitive Guide")
							.setISBN("9781491933619").setAuthor(book1Author).build();
		
		bookStore.put(book1.getId(), book1);
		
		List<String> book2Author = new ArrayList<String>();
		book2Author.add("Eben Hewitt");
		Book book2 =  new Book.Builder().setTitle("Java SOA Cookbook")
						.setISBN("9780596555498").setAuthor(book2Author)
				.build();
		
		bookStore.put(book2.getId(), book2);
		
		List<String> book3Author = new ArrayList<String>();
		book3Author.add("Bobby Wolf");
		book3Author.add("Gregor Hohpe");
		
		Book book3 =  new Book.Builder().setAuthor(book3Author)
				.setTitle("Enterprise Integration Patterns").setISBN("9780133065107").build();
		
		bookStore.put(book3.getId(), book3);
		
		List<String> book4Author = new ArrayList<String>();
		book4Author.add("Robert Daigneau");
		
		Book book4 =  new Book.Builder().setAuthor(book4Author)
				.setTitle("Service Design Patterns").setISBN("9780321669605").build();
		
		bookStore.put(book4.getId(), book4);
		
		List<String> book5Author = new ArrayList<String>();
		book5Author.add("Joshua Bloch");
		Book book5 =  new Book.Builder().setAuthor(book5Author)
				.setTitle("Effective Java").setISBN("9780132778046").build();
		
		bookStore.put(book5.getId(), book5);	
		
		
	}

}
