package com.collaberadigital.lms.apidocs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String API_TITLE = "LMS API Endpoints";
    private static final String API_VERSION = "1.0";
    private static final String API_DESCRIPTION = "API documentation for the LMS";
    private static final String CONTACT_NAME = "C N Balu Ramesh";
    private static final String CONTACT_EMAIL = "cnbaluramesh@gmail.com";

    /**
     * Grouped OpenAPI configuration for the library management system.
     * @return a GroupedOpenApi instance for grouping API endpoints.
     */
    @Bean
    public GroupedOpenApi libraryManagementApi() {
        return GroupedOpenApi.builder()
                .group("library-management")
                .pathsToMatch("/api/books/**", "/api/borrowers/**")
                .build();
    }

    /**
     * Custom OpenAPI configuration for the application.
     * @return an OpenAPI instance with metadata.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo());
    }

    /**
     * Builds the API Info section of the OpenAPI documentation.
     * @return an Info object with API metadata.
     */
    private Info apiInfo() {
        return new Info()
                .title(API_TITLE)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .contact(new Contact()
                        .name(CONTACT_NAME)
                        .email(CONTACT_EMAIL));
    }
}
