package com.intuit.craft.data.impl;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.intuit.craft.data.Shelf;
import com.intuit.craft.data.entity.Book;
import com.intuit.craft.exception.OverflowException;

/**
 * The Class BookshelfTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ShelfConfig.class,loader=AnnotationConfigContextLoader.class)

public class BookshelfTest {
	
	 
	 /** The Constant AUTHOR. */
 	public static final String AUTHOR = "Eben Hewitt";
	 
 	/** The Constant TITLE. */
 	public static final String TITLE = "Java SOA Cookbook";
	 
 	/** The Constant ISBN. */
 	public static final String ISBN = "9780596555498";
	 
	 /** The Constant AUTHOR1. */
 	public static final String AUTHOR1 = "Bobby Wolf";
	 
 	/** The Constant TITLE1. */
 	public static final String TITLE1 = "Enterprise Integration Patterns";
	 
	 /** The Constant TITLE2. */
 	public static final String TITLE2 = "Service Design Patterns";
	 
	 /** The Constant NEW_BOOK_AUTHOR. */
	 public static final String NEW_BOOK_AUTHOR = "Roger Federar";
	 
 	/** The Constant NEW_BOOK_TITLE. */
 	public static final String NEW_BOOK_TITLE = "Tennis";
	 
 	/** The Constant NEW_BOOK_ISBN. */
 	public static final String NEW_BOOK_ISBN = "3780596555498";
	 
	 /** The Constant CHECKOUT_PERSON. */
	 public static final String CHECKOUT_PERSON = "Kishore Konate";
	 
	 /** The bookshelf. */
 	@Autowired
	 private Shelf<Book> bookshelf;
 
	 /**
 	 * Test add book.
 	 *
 	 * @throws OverflowException the overflow exception
 	 */
 	@Test
	 public void testAddBook() throws OverflowException
	 {
		 List<String> author = new ArrayList<String>();	 
		 author.add(NEW_BOOK_AUTHOR);	
		 
		 Book book = new Book.Builder().setTitle(NEW_BOOK_TITLE)
				 .setISBN(NEW_BOOK_ISBN).setAuthor(author).build();
		 
		 bookshelf.add(book);
	 
		 List<Book> books = bookshelf.search(NEW_BOOK_TITLE,NEW_BOOK_AUTHOR, NEW_BOOK_ISBN);
		 Assert.assertNotNull(books);
		 
		 books.forEach(x -> System.out.println(x.toString()));
		 assertEquals(book.getId(), books.get(0).getId());
 
	 }
//		void remove(UUID id) throws ItemNotFoundException;	
//		void returnItem(UUID id);		
//	 	List<T> getOverDueItems();
	
	 /**
 * Test checkout.
 */
@Test
 	 public void testCheckout()
 	 {
		 UUID id = getId();	
		 
 		 LocalDateTime dt =  bookshelf.checkout(id, CHECKOUT_PERSON);
 		 assertEquals(dt.toLocalDate(), LocalDate.now().plusDays(14));
		 
 	 }
	 
	 /**
 	 * Gets the id.
 	 *
 	 * @return the id
 	 */
 	public UUID getId()
	 {
		 List<Book> books = bookshelf.getItemsOnShelf();
		 if(books != null)
		 {
			 return books.get(0).getId();
		 }
		 return null;
	 }
	 
	 /**
 	 * Test search by ISBN.
 	 */
 	@Test
	 public void testSearchByISBN()
	 {
		 List<Book> books = bookshelf.search("","",ISBN);
		
		 books.forEach( x -> System.out.println(x.toString()));
		 Assert.assertNotNull("Search books not null" , books);
		 
		 
	 }
	 
	 /**
 	 * Test items on shelf.
 	 */
 	@Test
	 public void testItemsOnShelf() {
		
		 List<Book> books = bookshelf.getItemsOnShelf();
		 for (Book book : books) {
			System.out.println(book.getTitle());
		}
		 
		 assertEquals(5, books.size());
	 }
	 
	 
	 
	 /**
 	 * Test search by title.
 	 */
 	@Test
	 public void testSearchByTitle()
	 {
		 List<Book> books = bookshelf.search(TITLE,"","");
		 books.forEach(x -> System.out.println(x.toString()));
		 Assert.assertNotNull("Search books not null" , books);
		 
		 
	 }
	 
	 /**
 	 * Test search by author.
 	 */
 	@Test
	 public void testSearchByAuthor()
	 {
		 List<Book> foundBooks = bookshelf.search("",AUTHOR,"");

		 Assert.assertNotNull("Search books not null" , foundBooks);
		 
		 
	 }
	 
	
	 /**
 	 * Test search by empty title author ISBN.
 	 */
 	@Test
	 public void testSearchByEmptyTitleAuthorISBN()
	 {
		 List<Book> foundBooks = bookshelf.search("","","");

		 assertEquals(0, foundBooks.size());
		 
		 
	 }
	 
	 /**
 	 * Test search by title author.
 	 */
 	@Test
	 public void testSearchByTitleAuthor()
	 {
		 List<Book> foundBooks = bookshelf.search(TITLE1,AUTHOR1,"");

		 Assert.assertNotNull("Search books not null" , foundBooks);	 
		 
	 }
	 
	 /**
 	 * Test search by title and mismatched author.
 	 */
 	@Test
	 public void testSearchByTitleAndMismatchedAuthor()
	 {
		 List<Book> foundBooks = bookshelf.search(TITLE2,AUTHOR1,"");
		 assertEquals(0, foundBooks.size());	 
		 
	 }
	 
	    


}
