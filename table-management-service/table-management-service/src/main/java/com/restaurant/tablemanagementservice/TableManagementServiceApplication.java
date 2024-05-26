package com.restaurant.tablemanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// This annotation indicates that this is a Spring Boot application and enables auto-configuration.
public class TableManagementServiceApplication {

    // This method serves as the entry point for the application.
    public static void main(String[] args) {
        SpringApplication.run(TableManagementServiceApplication.class, args);
        // This line initializes and starts the Spring Boot application.
    }

}
