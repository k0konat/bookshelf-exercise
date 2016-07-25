package com.intuit.craft.domain.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intuit.craft.data.Shelf;
import com.intuit.craft.data.entity.Book;
import com.intuit.craft.domain.BookshelfService;
import com.intuit.craft.exception.ItemNotFoundException;
import com.intuit.craft.exception.OverflowException;



@Service
public class BookshelfServiceImpl implements BookshelfService{
	
	private Shelf<Book> bookshelf;
	
	
	 @Autowired
	 public BookshelfServiceImpl(Shelf<Book> bookshelf) {
	        this.bookshelf = bookshelf;
	 }


	@Override
	public List<Book> getAll() {
		return  bookshelf.getAll();
	}


	@Override
	public void add(Book item) throws OverflowException {
		bookshelf.add(item);
		
	}


	@Override
	public void remove(UUID id) throws ItemNotFoundException {
		bookshelf.remove(id);
		
	}


	@Override
	public LocalDateTime checkout(UUID id, String name) throws ItemNotFoundException {
		return bookshelf.checkout(id, name);
	}


	@Override
	public void returnItem(UUID id) {
		bookshelf.returnItem(id);
		
	}


	@Override
	public List<Book> getItemsOnShelf() {
		return bookshelf.getItemsOnShelf();
	}


	@Override
	public List<Book> getOverDueItems() {
		return bookshelf.getOverDueItems();
	}


	@Override
	public List<Book> search(String tilte, String author, String ISBN) {
		return bookshelf.search(tilte, author, ISBN);
	}


	@Override
	public List<Book> getBorrowedItems() {
		return bookshelf.getBorrowedItems();
	}


}
