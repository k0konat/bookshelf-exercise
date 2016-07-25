package com.intuit.craft.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.intuit.craft.data.entity.Book;
import com.intuit.craft.exception.ItemNotFoundException;
import com.intuit.craft.exception.OverflowException;

@Repository
@SuppressWarnings("unchecked")
public class Bookshelf<T> implements Shelf<T>{
	
	private static final int MAX_LIMIT = 6;
	private static final String EMPTY = "";

	@Override
	public List<T> getAll() {		
		return (List<T>) BookData.bookStore.values().stream().collect(Collectors.toList());
	}
	
	@Override
	public void add(T book) throws OverflowException {
		
		if(book != null)
		{
			if(BookData.bookStore.size() < MAX_LIMIT)
			{
				Book newBook = (Book)book;
				BookData.bookStore.put(newBook.getId(), newBook);
			}
			else
			{
				throw new OverflowException("Bookshelf is full and it does not have any room to include more books");
			}
		}

	}
	
	@Override
	public void remove(UUID id) throws ItemNotFoundException{
		if(BookData.bookStore.get(id) != null)
			BookData.bookStore.remove(id);
		else
			throw new ItemNotFoundException("Book is not available at this time to remove it from the shelf");
	}
	
	
	@Override
	public LocalDateTime checkout(UUID id, String checkoutName) throws ItemNotFoundException {
		
		Book book = BookData.bookStore.get(id);
		
		if(book != null)
		{
			book.setBorrower(checkoutName);
			book.setBorrowed(true);
			book.calcualteAndSetDueDate();
			
			return book.getDueDate();	
			
		}else
			throw new ItemNotFoundException("Book was not checked out because it is not found on the shelf");		

	}
	
	@Override
	public void returnItem(UUID id) {
		Book book = BookData.bookStore.get(id);		
		if(book != null && book.isBorrowed())
		{
			book.setBorrower(EMPTY);
			book.setBorrowed(false);
			book.setDueDate(null);	
		}	
	}


	
	@Override
	public List<T> getItemsOnShelf() {
		List<Book> books = BookData.bookStore.values().stream()
									.filter(book -> !book.isBorrowed())
									.collect(Collectors.toList());
		
		return (List<T>) books;
	}
	
	
	@Override
	public List<T> getOverDueItems() {
		LocalDateTime today = LocalDateTime.now();
		List<Book> books = BookData.bookStore.values().stream()
				.filter(book -> book.isBorrowed() && book.getDueDate().isBefore(today) )
				.collect(Collectors.toList());

		return (List<T>) books;
	}
	
	@Override
	public T find(UUID id)
	{
		return (T) BookData.bookStore.values().stream()
		.filter(book -> book.getId().equals(id)).findFirst().get();
	}
	
	@Override
	public List<T> search(String title, String author, String ISBN) {
		
		if(!StringUtils.isEmpty(title) || !StringUtils.isEmpty(ISBN) || !StringUtils.isEmpty(author) )
		{
	
			Stream<Book> books = BookData.bookStore.values().stream();
			
			if(!StringUtils.isEmpty(title))
			{
				books = books.filter(book -> book.getTitle().contains(title));
			}
			
			if(!StringUtils.isEmpty(ISBN))
			{
				books = books.filter(book -> book.getISBN().equals(ISBN));
			}
			
			if(!StringUtils.isEmpty(author))
			{
				books = books.filter(book -> 
								book.getAuthor().stream().anyMatch(a -> a.contains(author)));
			}
	
			return (List<T>) books.collect(Collectors.toList());
		
		} 
		
		return new ArrayList<T>();
	}

	
	@Override
	public List<T> getBorrowedItems() {
		List<Book> books = BookData.bookStore.values().stream()
				.filter(book -> book.isBorrowed())
				.collect(Collectors.toList());

		return (List<T>) books;
	}




	
	

}
