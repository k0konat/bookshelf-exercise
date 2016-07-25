package com.intuit.craft.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.intuit.craft.data.entity.Book;
import com.intuit.craft.domain.requests.AddBookRequest;
import com.intuit.craft.domain.requests.CheckoutBookRequest;
import com.intuit.craft.exception.ConflictException;
import com.intuit.craft.exception.ItemNotFoundException;
import com.intuit.craft.exception.OverflowException;


/**
 * The Interface BookshelfService. 
 * Its a service interface for BookShelf to address the service related responsibilities and not mixing with the Shelf interface.
 * 
 * @author k0konat.
 */
public interface BookshelfService {
	
	
	/**
     * Return Book entities from a Bookstore.
     * The method returns an empty List if no data found
     * @return a list
     */
	List<Book> getAll();
	
	/**
	 * Adds the book to Bookstore
	 *
	 * @param book 
	 * @throws OverflowException when storage limit is exceeded
	 */
	void add(AddBookRequest bookRequest) throws OverflowException;
	
	/**
	 * Removes the book from the store
	 *
	 * @param id the unique id for the book
	 * @throws ItemNotFoundException 
	 */
	void remove(UUID id) throws ItemNotFoundException;	
	
	/**
	 * Checkout an book from the store
	 *
	 * @param id , unique identifier of an book
	 * @param name , Name of the person. For simplicity, just using a String
	 * @return the local date time when the book needs to be returned to the store.
	 * @throws ItemNotFoundException
	 * @throws ConflictException 
	 */
	LocalDateTime checkout(CheckoutBookRequest checkoutBookRequest) throws ItemNotFoundException, ConflictException;
	
	/**
	 * Return the book to the store so that the item will be available for any one to check out from the system.
	 *
	 * @param id,the unique id for the item 
	 */
	void returnBook(UUID id) throws ItemNotFoundException;	
	
	/**
	 * Gets the books on shelf.
	 *
	 * @return the books on shelf
	 */
	List<Book> getBooksOnShelf();
	
	/**
	 * Gets the over due books.
	 *
	 * @return the over due books
	 */
	List<Book> getOverDueBooks();
	
	/**
	 * Search. This is used to search an item by any title, author and ISBN. It is an "And" operation
	 *
	 * @param tilte the tilte
	 * @param author the author
	 * @param ISBN the isbn
	 * @return the list
	 */
	List<Book> search(String tilte, String author, String ISBN);
	
	/**
	 * Gets the borrowed books.
	 *
	 * @return the borrowed books
	 */
	List<Book> getBorrowedBooks();
	
	
}
