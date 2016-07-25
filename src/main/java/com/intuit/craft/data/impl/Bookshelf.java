package com.intuit.craft.data.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.intuit.craft.data.Shelf;
import com.intuit.craft.data.entity.Book;
import com.intuit.craft.exception.ConflictException;
import com.intuit.craft.exception.ItemNotFoundException;
import com.intuit.craft.exception.OverflowException;

/**
 * The Class Bookshelf.
 *
 * @param <T> the generic type
 * 
 * @author k0konat.
 */
@Repository
@SuppressWarnings("unchecked")
public class Bookshelf<T> implements Shelf<T>{
	
	/** The Constant MAX_LIMIT. This can be loaded from properties file*/
	private static final int MAX_LIMIT = 6;
	
	/** The Constant EMPTY. */
	private static final String EMPTY = "";

	/* (non-Javadoc)
	 * @see com.intuit.craft.data.Shelf#getAll()
	 */
	@Override
	public List<T> getAll() {		
		return (List<T>) BookData.bookStore.values().stream().collect(Collectors.toList());
	}
	
	/* (non-Javadoc)
	 * @see com.intuit.craft.data.Shelf#add(java.lang.Object)
	 */
	@Override
	public  void add(T book) throws OverflowException {
		
		
		if(BookData.bookStore.size() == MAX_LIMIT)
			throw new OverflowException("Bookshelf is full");
		
		if(book != null)
		{
			synchronized(this)
			{
				if(BookData.bookStore.size() < MAX_LIMIT)
				{
					Book newBook = (Book)book;
					BookData.bookStore.put(newBook.getId(), newBook);
				}else
					throw new OverflowException("Bookshelf is full");
			}	
		}

	}
	
	/* (non-Javadoc)
	 * @see com.intuit.craft.data.Shelf#remove(java.util.UUID)
	 */
	@Override
	public void remove(UUID id) throws ItemNotFoundException{
		Book book = BookData.bookStore.get(id);
		
		if(book == null)
			throw new ItemNotFoundException("Book not found");
		

		BookData.bookStore.remove(id);

	}
	
	
	/* (non-Javadoc)
	 * @see com.intuit.craft.data.Shelf#checkout(java.util.UUID, java.lang.String)
	 * 
	 * Since multiple users can checkout a book for the bookshelf and there is a possibity of Race Conditions here, we used 
	 */
	@Override
	public LocalDateTime checkout(UUID id, String checkoutName) throws ItemNotFoundException, ConflictException {
		
		Book book = BookData.bookStore.get(id);
		
		if(book == null && book.isBorrowed())
			throw new ItemNotFoundException("Book not found");

		synchronized(this)
		{
			if(!book.isBorrowed())
			{
				book.setBorrower(checkoutName);
				book.setBorrowed(true);
				book.calcualteAndSetDueDate();
				
				return book.getDueDate();
			}
			else
				throw new ConflictException("Book is not avaible at this time for checkout");
		}


	}
	
	/* (non-Javadoc)
	 * @see com.intuit.craft.data.Shelf#returnItem(java.util.UUID)
	 * 
	 */
	@Override
	public void returnItem(UUID id) throws ItemNotFoundException{
		Book book = BookData.bookStore.get(id);	
		
		if(book == null)
			throw new ItemNotFoundException("Book not found");

		if(book.isBorrowed())
		{
			book.setBorrower(EMPTY);
			book.setBorrowed(false);
			book.setDueDate(null);	
		}			
	}


	/* (non-Javadoc)
	 * @see com.intuit.craft.data.Shelf#getItemsOnShelf()
	 */
	@Override
	public List<T> getItemsOnShelf() {
		List<Book> books = BookData.bookStore.values().stream()
									.filter(book -> !book.isBorrowed())
									.collect(Collectors.toList());
		
		return (List<T>) books;
	}
	
	
	/* (non-Javadoc)
	 * @see com.intuit.craft.data.Shelf#getOverDueItems()
	 */
	@Override
	public List<T> getOverDueItems() {
		LocalDateTime today = LocalDateTime.now();
		List<Book> books = BookData.bookStore.values().stream()
				.filter(book -> book.isBorrowed() && book.getDueDate().isBefore(today) )
				.collect(Collectors.toList());

		return (List<T>) books;
	}
	
	/* (non-Javadoc)
	 * @see com.intuit.craft.data.Shelf#find(java.util.UUID)
	 */
	@Override
	public T find(UUID id)
	{
		return (T) BookData.bookStore.values().stream()
		.filter(book -> book.getId().equals(id)).findFirst().get();
	}
	
	/* (non-Javadoc)
	 * @see com.intuit.craft.data.Shelf#search(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<T> search(String title, String author, String ISBN) {
		
		if(!StringUtils.isEmpty(title) || !StringUtils.isEmpty(ISBN) || !StringUtils.isEmpty(author) )
		{
	
			Stream<Book> books = BookData.bookStore.values().stream();
			
			if(!StringUtils.isEmpty(title))
			{
				books = books.filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()));
			}
			
			if(!StringUtils.isEmpty(ISBN))
			{
				books = books.filter(book -> book.getISBN().toLowerCase().equals(ISBN.toLowerCase()));
			}
			
			if(!StringUtils.isEmpty(author))
			{
				books = books.filter(book -> 
								book.getAuthor().stream().anyMatch(a -> a.toLowerCase().contains(author.toLowerCase())));
			}
	
			return (List<T>) books.collect(Collectors.toList());
		
		} 
		
		return new ArrayList<T>();
	}

	
	/* (non-Javadoc)
	 * @see com.intuit.craft.data.Shelf#getBorrowedItems()
	 */
	@Override
	public List<T> getBorrowedItems() {
		List<Book> books = BookData.bookStore.values().stream()
				.filter(book -> book.isBorrowed())
				.collect(Collectors.toList());

		return (List<T>) books;
	}	

}
