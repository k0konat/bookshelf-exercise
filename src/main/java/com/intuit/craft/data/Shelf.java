package com.intuit.craft.data;

import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

import com.intuit.craft.exception.ConflictException;
import com.intuit.craft.exception.ItemNotFoundException;
import com.intuit.craft.exception.OverflowException;


/**
 * The Interface Shelf.
 *
 * @param <T> the generic type
 * 
 * @author k0konat.
 */
public interface Shelf<T> {
	
	/**
     * Return Item entities(CD, DVD, Book) from any store.
     * The method returns an empty List if no data found
     * @return a list
     */
	List<T> getAll();
	
	
	/**
	 * Adds the Item to store
	 *
	 * @param item 
	 * @throws OverflowException when storage limit is exceeded
	 */
	void add(T item) throws OverflowException;
	
	/**
	 * Removes the item from the store
	 *
	 * @param id the unique id for the item
	 * @throws ItemNotFoundException 
	 */
	void remove(UUID id) throws ItemNotFoundException;
	
	/**
	 * Checkout an item from the store
	 *
	 * @param id , unique identifier of an item
	 * @param name , Name of the person. For simplicity, just using a String
	 * @return the local date time when the item needs to be returned to the store.
	 * @throws ItemNotFoundException
	 * @throws ConflictException 
	 */
	LocalDateTime checkout(UUID id, String name) throws ItemNotFoundException, ConflictException;
	
	/**
	 * Return the item to the store so that the item will be available for any one to check out from the system.
	 *
	 * @param id,the unique id for the item 
	 */
	void returnItem(UUID id) throws ItemNotFoundException;
	
	/**
	 * Gets the items on shelf.
	 *
	 * @return the items on shelf
	 */
	List<T> getItemsOnShelf();

	/**
	 * Gets the over due items.
	 *
	 * @return the over due items
	 */
	List<T> getOverDueItems();

	/**
	 * Gets the borrowed items.
	 *
	 * @return the borrowed items
	 */
	List<T> getBorrowedItems();

	/**
	 * Utility function to get an item by id
	 *
	 * @param id , the unique id of the item
	 * @return the item
	 */
	T find(UUID id);

	/**
	 * Search. This is used to search an item by any title, author and ISBN. It is an "And" operation
	 *
	 * @param tilte the tilte
	 * @param author the author
	 * @param ISBN the isbn
	 * @return the list
	 */
	List<T> search(String tilte, String author, String ISBN);
	
	
	
	
}

