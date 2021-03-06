package com.kkexample.craft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;


/**
 * This is the starting point of the Spring Boot Application. The application can be started in the
 * embedded and non-embedded server mode.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    /** The Constant logger. */
    static final Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * Main method.
     *
     * @param args the arguments
     * @throws InterruptedException the interrupted exception
     */
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

    /* (non-Javadoc)
     * @see org.springframework.boot.context.web.SpringBootServletInitializer#configure(org.springframework.boot.builder.SpringApplicationBuilder)
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }


}
