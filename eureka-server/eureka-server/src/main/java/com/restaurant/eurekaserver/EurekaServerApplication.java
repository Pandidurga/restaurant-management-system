package com.restaurant.eurekaserver;

import org.springframework.boot.SpringApplication;  // Importing SpringApplication class for running the Spring Boot application
import org.springframework.boot.autoconfigure.SpringBootApplication;  // Importing SpringBootApplication for auto-configuration and component scanning
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;  // Importing EnableEurekaServer to enable Eureka Server functionality

/**
 * EurekaServerApplication class serves as the entry point for the Spring Boot application.
 * It sets up and runs the Eureka Server which acts as a service registry for microservices.
 */
@SpringBootApplication  // Annotation to mark this class as a Spring Boot application and enable auto-configuration and component scanning
@EnableEurekaServer  // Annotation to enable the Eureka Server functionality
public class EurekaServerApplication {

    /**
     * The main method is the entry point of the application.
     * It runs the Spring Boot application and starts the embedded server.
     *
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);  // Running the Spring Boot application
    }

}
