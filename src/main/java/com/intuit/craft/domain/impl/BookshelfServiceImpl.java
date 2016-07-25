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


/**
 * The Class BookshelfServiceImpl.
 * 
 * @author k0konat.
 */
@Service
public class BookshelfServiceImpl implements BookshelfService{
	
	/** The bookshelf. */
	private Shelf<Book> bookshelf;
	
	
	 /**
 	 * Instantiates a new bookshelf service impl.
 	 *
 	 * @param bookshelf the bookshelf
 	 */
 	@Autowired
	 public BookshelfServiceImpl(Shelf<Book> bookshelf) {
	        this.bookshelf = bookshelf;
	 }


	/* (non-Javadoc)
	 * @see com.intuit.craft.domain.BookshelfService#getAll()
	 */
	@Override
	public List<Book> getAll() {
		return  bookshelf.getAll();
	}


	/* (non-Javadoc)
	 * @see com.intuit.craft.domain.BookshelfService#add(com.intuit.craft.data.entity.Book)
	 */
	@Override
	public void add(Book item) throws OverflowException {
		bookshelf.add(item);	
	}


	/* (non-Javadoc)
	 * @see com.intuit.craft.domain.BookshelfService#remove(java.util.UUID)
	 */
	@Override
	public void remove(UUID id) throws ItemNotFoundException {
		bookshelf.remove(id);
		
	}


	/* (non-Javadoc)
	 * @see com.intuit.craft.domain.BookshelfService#checkout(java.util.UUID, java.lang.String)
	 */
	@Override
	public LocalDateTime checkout(UUID id, String name) throws ItemNotFoundException {
		return bookshelf.checkout(id, name);
	}


	/* (non-Javadoc)
	 * @see com.intuit.craft.domain.BookshelfService#returnItem(java.util.UUID)
	 */
	@Override
	public void returnBook(UUID id) throws ItemNotFoundException {
		bookshelf.returnItem(id);
		
	}


	/* (non-Javadoc)
	 * @see com.intuit.craft.domain.BookshelfService#getItemsOnShelf()
	 */
	@Override
	public List<Book> getBooksOnShelf() {
		return bookshelf.getItemsOnShelf();
	}


	/* (non-Javadoc)
	 * @see com.intuit.craft.domain.BookshelfService#getOverDueItems()
	 */
	@Override
	public List<Book> getOverDueBooks() {
		return bookshelf.getOverDueItems();
	}


	/* (non-Javadoc)
	 * @see com.intuit.craft.domain.BookshelfService#search(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Book> search(String tilte, String author, String ISBN) {
		return bookshelf.search(tilte, author, ISBN);
	}


	/* (non-Javadoc)
	 * @see com.intuit.craft.domain.BookshelfService#getBorrowedItems()
	 */
	@Override
	public List<Book> getBorrowedBooks() {
		return bookshelf.getBorrowedItems();
	}


}
