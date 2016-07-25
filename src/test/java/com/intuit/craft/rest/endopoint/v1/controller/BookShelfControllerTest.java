package com.intuit.craft.rest.endopoint.v1.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.intuit.craft.data.entity.Book;
import com.intuit.craft.domain.BookshelfService;
import com.intuit.craft.domain.requests.CheckoutBookRequest;
import com.intuit.craft.util.TestUtils;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookShelfControllerTest {
	
	private BookShelfController bookShelfController;
	private BookshelfService bookshelfService;
	private MockMvc mockMvc;
	

    @Before
    public void doSetUp() {
    	bookshelfService = mock(BookshelfService.class);
    	bookShelfController = new BookShelfController(bookshelfService);
    	this.mockMvc = MockMvcBuilders.standaloneSetup(bookShelfController)
    			.build();   	
    }
    
    @Test
    public void get_All_ShouldReturnTwoBooks() throws Exception
    {	
		List<String> book1Author = new ArrayList<String>();
		book1Author.add("Eben Hewitt");
		Book book1 = new Book.Builder().setTitle("Cassandra: The Definitive Guide")
							.setISBN("9781491933619").setAuthor(book1Author).build();
	
		List<String> book2Author = new ArrayList<String>();
		book2Author.add("Eben Hewitt");
		Book book2 =  new Book.Builder().setTitle("Java SOA Cookbook")
						.setISBN("9780596555498").setAuthor(book2Author)
				.build();

		when(bookshelfService.getAll()).thenReturn(Arrays.asList(book1, book2));
		 	
    	int size = 2;
    	this.mockMvc.perform(get("/books"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.error").doesNotExist())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(size)))
        .andExpect(jsonPath("$[0].title", is("Cassandra: The Definitive Guide")))
        .andExpect(jsonPath("$[0].isbn", is("9781491933619")));    
        
    }
    
    
    @Test
    public void add_NewBookEntry_ShouldAddBookEntry() throws Exception {
    	
    	List<String> book1Author = new ArrayList<String>();
		book1Author.add("Eben Hewitt Charles");
		Book book1 = new Book.Builder().setTitle("Cassandra: The Definitive Guide 234")
							.setISBN("8781491933619").setAuthor(book1Author).build();
 
		this.mockMvc.perform(post("/books/book").contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(book1))
				).andExpect(status().isCreated());
		
    }
    
    @Test
    public void get_Borrowed_ShouldReturnOneBook() throws Exception
    {	
		List<String> book1Author = new ArrayList<String>();
		book1Author.add("Eben Hewitt");
		Book book1 = new Book.Builder().setTitle("Cassandra: The Definitive Guide")
							.setISBN("9781491933619").setAuthor(book1Author).build();
		
		book1.setBorrowed(true);
		book1.setBorrower("Charles");
		book1.calcualteAndSetDueDate();

		when(bookshelfService.getBorrowedItems()).thenReturn(Arrays.asList(book1));
		 	
    	int size = 1;
    	this.mockMvc.perform(get("/books/borrowed"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.error").doesNotExist())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(size)))
        .andExpect(jsonPath("$[0].title", is("Cassandra: The Definitive Guide")))
        .andExpect(jsonPath("$[0].isbn", is("9781491933619")))
        .andExpect(jsonPath("$[0].borrower", is("Charles")));    
        
    }
    
    @Test
    public void checkout_BookEntry_ShouldAddBookEntryAndCheckOutAndReturnDueDate() throws Exception {
    	List<String> book1Author = new ArrayList<String>();
		book1Author.add("Eben Hewitt Charles");
		Book book1 = new Book.Builder().setTitle("Cassandra: The Definitive Guide 234")
							.setISBN("8781491933619").setAuthor(book1Author).build();
 
		this.mockMvc.perform(post("/books/book").contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(book1))
				).andExpect(status().isCreated());
		
		CheckoutBookRequest request = new CheckoutBookRequest();
		request.setId(book1.getId());
		request.setName("Charles");
		
		this.mockMvc.perform(put("/books/book/checkout").contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(request))
				).andExpect(status().isOk());
		
    	
    }

	
}


