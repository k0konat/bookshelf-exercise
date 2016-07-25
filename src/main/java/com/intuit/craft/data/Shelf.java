package com.intuit.craft.data;

import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

import com.intuit.craft.exception.ItemNotFoundException;
import com.intuit.craft.exception.OverflowException;

public interface Shelf<T> {
	List<T> getAll();
	void add(T item) throws OverflowException;
	void remove(UUID id) throws ItemNotFoundException;
	
	LocalDateTime checkout(UUID id, String name) throws ItemNotFoundException;
	void returnItem(UUID id);
	
	List<T> getItemsOnShelf();
	List<T> getOverDueItems();
	List<T> getBorrowedItems();
	T find(UUID id);
	
	List<T> search(String tilte, String author, String ISBN);
	
	
	
	
}

