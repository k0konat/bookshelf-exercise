package com.kkexample.craft.domain.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kkexample.craft.data.Shelf;
import com.kkexample.craft.data.entity.Book;
import com.kkexample.craft.domain.BookshelfService;
import com.kkexample.craft.domain.requests.AddBookRequest;
import com.kkexample.craft.domain.requests.CheckoutBookRequest;
import com.kkexample.craft.exception.ConflictException;
import com.kkexample.craft.exception.ItemNotFoundException;
import com.kkexample.craft.exception.OverflowException;


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
	public void add(AddBookRequest bookRequest) throws OverflowException {
		Book book = new Book.Builder().setTitle(bookRequest.getTitle())
				.setISBN(bookRequest.getISBN())
				.setAuthor(bookRequest.getAuthor()).build();
		
		bookshelf.add(book);	
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
	public LocalDateTime checkout(CheckoutBookRequest checkoutBookRequest) throws ItemNotFoundException, ConflictException {
		LocalDateTime dt = bookshelf.checkout(checkoutBookRequest.getId(), checkoutBookRequest.getName());
		return dt;
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
