package com.intuit.craft.rest.endopoint.v1.controller.hander;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.intuit.craft.domain.BookshelfService;
import com.intuit.craft.rest.endopoint.v1.controller.BookshelfController;
import com.intuit.craft.rest.endopoint.v1.controller.handler.ControllerExceptionHandler;
import com.intuit.craft.util.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ControllerExceptionHandlerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private BookshelfService mockBookshelfService;
	 
    @Before
    public void setup() {
        BookshelfController statusController = new BookshelfController(this.mockBookshelfService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(statusController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }
    
    @Test
    public void checkItemNotFoundExceptionsAreCaughtAndStatusCode404IsReturnedInResponse() throws Exception {
        String url = "/books/book/return";
       
        UUID uuid = UUID.randomUUID();

        when(mockBookshelfService.checkout(uuid, "UNKNOWN"))
                .thenReturn(LocalDateTime.now());

        mockMvc.perform(put(url)
        	.contentType(TestUtils.APPLICATION_JSON_UTF8)
        	.content(TestUtils.convertObjectToJsonBytes(uuid)))
                .andDo(print())
                .andExpect(status().isNotFound());

    }
}
