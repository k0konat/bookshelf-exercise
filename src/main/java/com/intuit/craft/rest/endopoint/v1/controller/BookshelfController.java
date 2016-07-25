package com.intuit.craft.rest.endopoint.v1.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.intuit.craft.domain.BookshelfService;
import com.intuit.craft.domain.requests.AddBookRequest;
import com.intuit.craft.domain.requests.CheckoutBookRequest;
import com.intuit.craft.exception.ConflictException;
import com.intuit.craft.exception.ItemNotFoundException;
import com.intuit.craft.exception.MissingArgumentException;
import com.intuit.craft.exception.OverflowException;
import com.google.common.base.Strings;
import com.intuit.craft.data.entity.Book;
import com.intuit.craft.data.impl.BookData;

/**
 * The Class BookShelfController
 * 
 * @author k0konat.
 */
@RestController
@RequestMapping("/books")
public class BookshelfController {
	 
 	/** The Constant logger. */
 	static final Logger logger = LoggerFactory.getLogger(BookshelfController.class);
	 
 	/** The Constant ApplicationContentType. */
 	static final String ApplicationContentType = "application/json";
	 
 	/** The book shelf service. */
 	private final BookshelfService bookShelfService;
	 
	 /**
 	 * Instantiates a new book shelf controller.
 	 *
 	 * @param service the service
 	 */
 	@Autowired
	 public BookshelfController(BookshelfService service) {
		 this.bookShelfService = service;
	 }
	 
	 /**
 	 * Adds the book to the Store.
 	 *
 	 * @param book the book
 	 * @return the response entity
	 * @throws OverflowException 
 	 */
 	@RequestMapping(value = "/book", method = RequestMethod.POST)
 	HttpEntity<Void> add(@RequestBody @Valid AddBookRequest bookRequest) throws OverflowException{
	
		logger.debug("Inside create method and the size of the book store is " + BookData.bookStore.size());
		
			
		bookShelfService.add(bookRequest);
		
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	 }
	 
	 /**
 	 * Gets Book entities from a Bookstore.
     * The method returns an empty List if no data found.
 	 *
 	 * @return the Books list
 	 */
 	@RequestMapping(method = RequestMethod.GET, produces = ApplicationContentType)
 	HttpEntity<List<Book>> getAll() {		
		 return new ResponseEntity<>(bookShelfService.getAll(),HttpStatus.OK);		
	}
	  
	 /**
 	 * Gets the over due books.
 	 *
 	 * @return the over due books
 	 */
 	@RequestMapping(value="/overdue", method = RequestMethod.GET, produces = ApplicationContentType)
	HttpEntity<List<Book>> getOverDueBooks(){
		 return new ResponseEntity<>(bookShelfService.getOverDueBooks(),HttpStatus.OK);
	}
	 
	 /**
 	 *  Search. This is used to search an book by any title, author and ISBN. It is an "And" operation
 	 *
 	 * @param title the title
 	 * @param author the author
 	 * @param ISBN the isbn
 	 * @return the http entity which consists of books
 	 */
 	@RequestMapping(value = "/search",
			 method = RequestMethod.GET, produces = ApplicationContentType)
	HttpEntity<List<Book>> search(@RequestParam(value="title",required = false) String title,
			 @RequestParam(value="author",required = false) String author,
			 @RequestParam(value="ISBN", required = false) String ISBN) {
		 return new ResponseEntity<>(bookShelfService.search(title, author, ISBN),HttpStatus.OK);
	}
	 
	 /**
 	 * Gets the borrowed books.
 	 *
 	 * @return the borrowed books
 	 */
 	@RequestMapping(value="/borrowed", method = RequestMethod.GET, produces = ApplicationContentType)
	HttpEntity<List<Book>> getBorrowedBooks(){
		 return new ResponseEntity<>(bookShelfService.getBorrowedBooks(),HttpStatus.OK);
	}
	 
 	/**
	 * Checkout an book from the store
	 *
	 * @param checkoutBookRequest the checkout book requet.
 	 * @return the http entity which includes ocal date time when the book needs to be returned to the store.
 	 * @throws ConflictException 
 	 * @throws ItemNotFoundException 
 	 */
 	@RequestMapping(value="/book/checkout", method = RequestMethod.PUT)
	HttpEntity<?> checkout(@RequestBody CheckoutBookRequest checkoutBookRequest) throws ItemNotFoundException, ConflictException{
		
		logger.debug("Id of the book  " + checkoutBookRequest.getId());
		logger.debug("Name of the person to check out" + checkoutBookRequest.getName());
		LocalDateTime dt = bookShelfService.checkout(checkoutBookRequest);
		return new ResponseEntity<>(dt, HttpStatus.OK);
	
 	}
	 
	 /**
 	 * Return book.
 	 *
 	 * @param id the unique id of a book
 	 */
 	@RequestMapping(value="/book/returnBook", method = RequestMethod.PUT)
 	HttpEntity<Void> returnBook(@RequestBody String id) throws ItemNotFoundException{
		 UUID uuid = UUID.fromString(id);
		 logger.debug("Id of the book  " + id);
		
		 bookShelfService.returnBook(uuid);
		 return new ResponseEntity<>(HttpStatus.OK);
		
	 }
	 
	 /**
 	 * Gets the books on shelf.
 	 *
 	 * @return the books on shelf
 	 */
 	@RequestMapping(value="/remaining", method = RequestMethod.GET, produces = ApplicationContentType)
	 HttpEntity<List<Book>> getBooksOnShelf(){
		 return new ResponseEntity<>(bookShelfService.getBooksOnShelf(), HttpStatus.OK);
	 }
 	
 	//helper method
 	private void validate(String message, String... args) {
        if (args.length == 0) {
            throw new MissingArgumentException(message);
        }

        for (String arg : args) {
            if (Strings.isNullOrEmpty(arg)) {
                throw new MissingArgumentException(message);
            }
        }
    }
 	
 	
 	
 	

 
	 	 

}
