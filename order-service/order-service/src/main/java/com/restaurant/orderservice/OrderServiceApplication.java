package com.restaurant.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Main application class for the Order Service.
 */
@SpringBootApplication
@EntityScan(basePackages = {
    "com.restaurant.menuitemservice.model",
    "com.restaurant.orderservice.model",
    "com.restaurant.tablemanagementservice.model"
})
public class OrderServiceApplication {

    /**
     * Entry point for the Order Service application.
     * 
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
