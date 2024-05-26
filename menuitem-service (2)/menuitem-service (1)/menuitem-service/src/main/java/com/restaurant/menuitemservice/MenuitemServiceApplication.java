package com.restaurant.menuitemservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MenuitemServiceApplication is the entry point for the Spring Boot application.
 * It sets up and runs the Menu Item Service.
 */
@SpringBootApplication  // Indicates that this class is a Spring Boot application and enables auto-configuration
//@EnableEurekaClient  // This annotation is commented out; it would enable Eureka Client functionality if uncommented
public class MenuitemServiceApplication {

    /**
     * The main method is the entry point of the application.
     * It runs the Spring Boot application and starts the embedded server.
     *
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(MenuitemServiceApplication.class, args);  // Runs the Spring Boot application
    }

}
