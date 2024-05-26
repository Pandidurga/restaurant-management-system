package com.restaurant.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restaurant.orderservice.model.Order;
import com.restaurant.orderservice.service.OrderService;

import java.util.List;

/**
 * Controller class for managing orders.
 * Provides endpoints for retrieving, creating, updating, and deleting orders.
 */
@RestController
@RequestMapping("/api/orders")
//@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    /**
     * Constructs a new OrderController with the specified OrderService.
     * 
     * @param orderService The OrderService to be used by the controller.
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Retrieve all orders
    @GetMapping("/get-all")
    public ResponseEntity<List<Order>> getAllOrders() {
        // Retrieve all orders from the service layer
        List<Order> orders = orderService.getAllOrders();
        // Return a ResponseEntity with the list of orders and a status code of 200 (OK)
        return ResponseEntity.ok(orders);
    }

    // Retrieve an order by its ID
    @GetMapping("/get-by-id/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable int orderId) {
        // Retrieve the order by its ID from the service layer
        Order order = orderService.getOrderById(orderId);
        // Check if the order exists
        if (order != null) {
            // Return a ResponseEntity with the order and a status code of 200 (OK)
            return ResponseEntity.ok(order);
        } else {
            // Return a ResponseEntity with a status code of 404 (Not Found) if the order does not exist
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new order
    @PostMapping("/add")
    public ResponseEntity<Order> createOrder(@RequestParam int tableId) {
        // Create a new order using the provided table ID
        Order createdOrder = orderService.createOrder(tableId);
        // Return a ResponseEntity with the created order and a status code of 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    // Update an existing order by its ID
    @PutMapping("/update/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable int orderId, @RequestParam int tableId) {
        // Update the order with the specified ID using the provided table ID
        Order updatedOrder = orderService.updateOrder(orderId, tableId);
        // Check if the order was successfully updated
        if (updatedOrder != null) {
            // Return a ResponseEntity with the updated order and a status code of 200 (OK)
            return ResponseEntity.ok(updatedOrder);
        } else {
            // Return a ResponseEntity with a status code of 404 (Not Found) if the order does not exist
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an order by its ID
    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int orderId) {
        // Delete the order with the specified ID
        boolean deleted = orderService.deleteOrder(orderId);
        // Check if the order was successfully deleted
        if (deleted) {
            // Return a ResponseEntity with a status code of 204 (No Content)
            return ResponseEntity.noContent().build();
        } else {
            // Return a ResponseEntity with a status code of 404 (Not Found) if the order does not exist
            return ResponseEntity.notFound().build();
        }
    }
    
    // Cancel an order by its ID
    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<Order> cancelOrder(@PathVariable int orderId) {
        // Cancel the order with the specified ID
        Order canceledOrder = orderService.cancelOrder(orderId);
        // Check if the order was successfully canceled
        if (canceledOrder != null) {
            // Return a ResponseEntity with the canceled order and a status code of 200 (OK)
            return ResponseEntity.ok(canceledOrder);
        } else {
            // Return a ResponseEntity with a status code of 404 (Not Found) if the order does not exist
            return ResponseEntity.notFound().build();
        }
    }
    
    // Generate bill for an order by its ID
    @PutMapping("/generate-bill/{orderId}")
    public ResponseEntity<Order> generateBill(@PathVariable int orderId) {
        try {
            // Generate the bill for the order with the specified ID
            Order completedOrder = orderService.generateBill(orderId);
            // Return a ResponseEntity with the completed order and a status code of 200 (OK)
            return ResponseEntity.ok(completedOrder);
        } catch (RuntimeException e) {
            // Return a ResponseEntity with a status code of 404 (Not Found) if the order does not exist
            return ResponseEntity.notFound().build();
        }
    }
}
