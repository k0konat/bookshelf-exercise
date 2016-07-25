This is a Spring Boot POC exposing a REST API for the library exercise

### Quick Start
    
* Running the app
    ```
    mvn spring-boot:run    
    ```
    Open the browser at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* Running the tests
    ```
    mvn test
    ```

#### Swagger UI
* Access the swagger UI at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* Bookshelf Controller : Provides the following operations from the bookshelf
	* Gets all the books 
	* Gets the overdue books
	* Gets the borrowed books
	* Gets the books on shelf which are not checked out
	* Adds a book
	* Checkout a book
	* Return a book
	* Can search the bookshelf(library) with title, author and ISBN.
	





