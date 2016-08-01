package com.kkexample.craft.apidocs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration for Swagger.
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {    
	 
 	/**
 	 * Returns a new Docket for configuring Swagger.
 	 *
 	 * @return a new Docket object
 	 */
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())   
          .paths(Predicates.not(PathSelectors.regex("/error")))                         
          .build().apiInfo(apiInfo());  
    }
    
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Bookshelf Service",
                "This is a rest service built by using Spring-Boot.",
                "API TOS",
                "konate1kk@gmail.com",
                "Kishore Konate",
                 null, null
        );
        return apiInfo;
    }
    
   
}
