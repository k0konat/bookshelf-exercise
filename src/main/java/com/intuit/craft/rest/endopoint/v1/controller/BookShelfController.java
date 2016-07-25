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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.intuit.craft.domain.BookshelfService;
import com.intuit.craft.domain.requests.CheckoutBookRequest;
import com.intuit.craft.exception.ItemNotFoundException;
import com.intuit.craft.exception.OverflowException;
import com.intuit.craft.data.BookData;
import com.intuit.craft.data.entity.Book;

@RestController
@RequestMapping("/books")
public class BookShelfController {
	 static final Logger logger = LoggerFactory.getLogger(BookShelfController.class);
	 static final String ApplicationContentType = "application/json";
	 private final BookshelfService bookShelfService;
	 
	 @Autowired
	 public BookShelfController(BookshelfService service) {
		 this.bookShelfService = service;
	 }
	 
	 @RequestMapping(value = "/book", method = RequestMethod.POST)
	 @ResponseStatus(HttpStatus.CREATED)
	 ResponseEntity<Void> create(@RequestBody @Valid Book book){
		 try {
			logger.debug("Inside create method and the size of the book store is " + BookData.bookStore.size());
			bookShelfService.add(book);
		} catch (OverflowException e) {		
			return new ResponseEntity<>(HttpStatus.INSUFFICIENT_STORAGE);
		}
		 return new ResponseEntity<Void>(HttpStatus.CREATED);
	 }
	 
	 @RequestMapping(method = RequestMethod.GET, produces = ApplicationContentType)
	 HttpEntity<List<Book>> getAll() {		
		 return new ResponseEntity<>(bookShelfService.getAll(),HttpStatus.OK);		
	 }
	  
	 @RequestMapping(value="/overdue", method = RequestMethod.GET, produces = ApplicationContentType)
	 HttpEntity<List<Book>> getOverDueItems(){
		 return new ResponseEntity<>(bookShelfService.getOverDueItems(),HttpStatus.OK);
	 }
	 
	 @RequestMapping(value = "/search",
			 method = RequestMethod.GET, produces = ApplicationContentType)
	 HttpEntity<List<Book>> searchTitle(@RequestParam(value="title",required = false) String title,
			 @RequestParam(value="author",required = false) String author,
			 @RequestParam(value="ISBN", required = false) String ISBN) {
		 return new ResponseEntity<>(bookShelfService.search(title, author, ISBN),HttpStatus.OK);
	 }
	 
	 @RequestMapping(value="/borrowed", method = RequestMethod.GET, produces = ApplicationContentType)
	 HttpEntity<List<Book>> getBorrowedItems(){
		 return new ResponseEntity<>(bookShelfService.getBorrowedItems(),HttpStatus.OK);
	 }
	 
	 @RequestMapping(value="/book/checkout", method = RequestMethod.PUT)
	 HttpEntity<?> checkout(@RequestBody CheckoutBookRequest checkoutBookRequest){
		
		 try {
			 LocalDateTime dt = bookShelfService.checkout(checkoutBookRequest.getId(), checkoutBookRequest.getName());
			 return new ResponseEntity<>(dt, HttpStatus.OK);
		 } catch (ItemNotFoundException e) {	 
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
		
	 }
	 
	 @RequestMapping(value="/book/returnItem", method = RequestMethod.PUT)
	 void returnItem(@RequestBody String id){
		 UUID uuid = UUID.fromString(id);
		 bookShelfService.returnItem(uuid);
	 }
	 
	 @RequestMapping(value="/remaining", method = RequestMethod.GET, produces = ApplicationContentType)
	 HttpEntity<List<Book>> getItemsOnShelf(){
		 return new ResponseEntity<>(bookShelfService.getItemsOnShelf(), HttpStatus.OK);
	 }
	 	 

}
