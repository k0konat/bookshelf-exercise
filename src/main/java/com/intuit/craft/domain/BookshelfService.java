package com.intuit.craft.domain;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.intuit.craft.data.entity.Book;
import com.intuit.craft.exception.ItemNotFoundException;
import com.intuit.craft.exception.OverflowException;

public interface BookshelfService {
	
	
	List<Book> getAll();
	void add(Book item) throws OverflowException;
	void remove(UUID id) throws ItemNotFoundException;	
	LocalDateTime checkout(UUID id, String name) throws ItemNotFoundException;
	void returnItem(UUID id);	
	List<Book> getItemsOnShelf();
	List<Book> getOverDueItems();
	List<Book> search(String tilte, String author, String ISBN);
	List<Book> getBorrowedItems();
	
	
}
