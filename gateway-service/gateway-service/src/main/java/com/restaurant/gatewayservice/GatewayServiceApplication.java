package com.restaurant.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * GatewayServiceApplication is the entry point for the Spring Boot application.
 * It sets up and runs the Spring Cloud Gateway service.
 */
@SpringBootApplication  // Marks this class as a Spring Boot application and enables auto-configuration
public class GatewayServiceApplication {

    /**
     * The main method is the entry point of the application.
     * It runs the Spring Boot application and starts the embedded server.
     *
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);  // Runs the Spring Boot application
    }

}
