package com.kkexample.craft.rest.endopoint.v1.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.kkexample.craft.data.entity.Book;
import com.kkexample.craft.domain.BookshelfService;
import com.kkexample.craft.domain.requests.CheckoutBookRequest;
import com.kkexample.craft.rest.endopoint.v1.controller.BookshelfController;
import com.kkexample.craft.util.TestUtils;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Class BookShelfControllerTest.
 */
public class BookshelfControllerTest {
	
	/** The book shelf controller. */
	private BookshelfController bookShelfController;
	
	/** The bookshelf service. */
	private BookshelfService bookshelfService;
	
	/** The mock mvc. */
	private MockMvc mockMvc;
	

    /**
     * Do set up.
     */
    @Before
    public void doSetUp() {
    	bookshelfService = mock(BookshelfService.class);
    	bookShelfController = new BookshelfController(bookshelfService);
    	this.mockMvc = MockMvcBuilders.standaloneSetup(bookShelfController)
    			.build();   	
    }
    
    /**
     * Gets the all should return two books.
     *
     * @return the all should return two books
     * @throws Exception the exception
     */
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
    	.andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.error").doesNotExist())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(size)))
        .andExpect(jsonPath("$[0].title", is("Cassandra: The Definitive Guide")))
        .andExpect(jsonPath("$[0].isbn", is("9781491933619")));    
        
    }
    
    
    /**
     * Adds the new book entry should add book entry.
     *
     * @throws Exception the exception
     */
    @Test
    public void add_NewBookEntry_ShouldAddBookEntry() throws Exception {
    	
    	List<String> book1Author = new ArrayList<String>();
		book1Author.add("Eben Hewitt Charles");
		Book book1 = new Book.Builder().setTitle("Cassandra: The Definitive Guide 234")
							.setISBN("8781491933619").setAuthor(book1Author).build();
 
		this.mockMvc.perform(post("/books/book").contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(book1))
				)
		.andDo(print())
		.andExpect(status().isCreated());
		
    }
    
    /**
     * Gets the borrowed should return one book.
     *
     * @return the borrowed should return one book
     * @throws Exception the exception
     */
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

		when(bookshelfService.getBorrowedBooks()).thenReturn(Arrays.asList(book1));
		 	
    	int size = 1;
    	this.mockMvc.perform(get("/books/borrowed"))
    	.andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.error").doesNotExist())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(size)))
        .andExpect(jsonPath("$[0].title", is("Cassandra: The Definitive Guide")))
        .andExpect(jsonPath("$[0].isbn", is("9781491933619")))
        .andExpect(jsonPath("$[0].borrower", is("Charles")));    
        
    }
    
    /**
     * Checkout book entry should add book entry and check out and return due date.
     *
     * @throws Exception the exception
     */
    @Test
    public void checkout_BookEntry_ShouldAddBookEntryAndCheckOutAndReturnDueDate() throws Exception {
    	List<String> book1Author = new ArrayList<String>();
		book1Author.add("Eben Hewitt Charles");
		Book book1 = new Book.Builder().setTitle("Cassandra: The Definitive Guide 234")
							.setISBN("8781491933619").setAuthor(book1Author).build();
 
		this.mockMvc.perform(post("/books/book").contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(book1))
				)
		.andDo(print())
		.andExpect(status().isCreated());
		
		CheckoutBookRequest request = new CheckoutBookRequest();
		request.setId(book1.getId());
		request.setName("Charles");
		
		this.mockMvc.perform(put("/books/book/checkout").contentType(TestUtils.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(request))
				).andExpect(status().isOk());
		
    	
    }

	
}


