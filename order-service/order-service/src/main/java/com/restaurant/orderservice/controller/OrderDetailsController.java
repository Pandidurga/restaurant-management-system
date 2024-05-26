package com.restaurant.orderservice.controller;

import com.restaurant.orderservice.model.OrderDetails;
import com.restaurant.orderservice.service.OrderDetailsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing order details.
 * Provides endpoints for CRUD operations on order details.
 */
@RestController
@RequestMapping("api/order-details")
public class OrderDetailsController {
	
	@Autowired
    private final OrderDetailsService orderDetailsService;

    /**
     * Constructs a new OrderDetailsController with the specified OrderDetailsService.
     * 
     * @param orderDetailsService The OrderDetailsService to be used by the controller.
     */
    public OrderDetailsController(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }
    
    /**
     * Retrieves all order details from the database.
     * 
     * @return ResponseEntity containing a list of all order details.
     */
    @GetMapping("/get-all")
    public ResponseEntity<List<OrderDetails>> getAllOrderDetails() {
        List<OrderDetails> orderDetailsList = orderDetailsService.getAllOrderDetails();
        return ResponseEntity.ok(orderDetailsList);
    }

    /**
     * Retrieves an order detail by its ID from the database.
     * 
     * @param orderDetailId The ID of the order detail to retrieve.
     * @return ResponseEntity containing the requested order detail, or NOT_FOUND status if not found.
     */
    @GetMapping("/get-by-id/{orderDetailId}")
    public ResponseEntity<OrderDetails> getOrderDetailsById(@PathVariable int orderDetailId) {
        OrderDetails orderDetails = orderDetailsService.getOrderDetailsById(orderDetailId);
        if (orderDetails != null) {
            return ResponseEntity.ok(orderDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new order detail in the database.
     * 
     * @param orderId    The ID of the associated order.
     * @param menuItemId The ID of the menu item.
     * @param quantity   The quantity of the menu item ordered.
     * @return ResponseEntity containing the created order detail, or BAD_REQUEST status if creation fails.
     */
    @PostMapping("/add")
    public ResponseEntity<OrderDetails> createOrderDetails(
            @RequestParam int orderId,
            @RequestParam int menuItemId,
            @RequestParam int quantity) {
        
        OrderDetails orderDetails = orderDetailsService.createOrderDetails(orderId, menuItemId, quantity);
        
        if (orderDetails != null) {
            return new ResponseEntity<>(orderDetails, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates an existing order detail in the database.
     * 
     * @param orderDetailId The ID of the order detail to update.
     * @param orderId       The ID of the associated order.
     * @param menuItemId    The ID of the menu item.
     * @param quantity      The quantity of the menu item ordered.
     * @return ResponseEntity containing the updated order detail, or NOT_FOUND status if the order detail does not exist.
     */
    @PutMapping("/update/{orderDetailId}")
    public ResponseEntity<OrderDetails> updateOrderDetails(@PathVariable int orderDetailId, @RequestParam int orderId, @RequestParam int menuItemId, @RequestParam int quantity) {
    	OrderDetails updatedOrderDetails = orderDetailsService.updateOrderDetails(orderDetailId, orderId, menuItemId, quantity);
        
        if (updatedOrderDetails != null) {
            return ResponseEntity.ok(updatedOrderDetails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Deletes an order detail from the database by its ID.
     * 
     * @param orderDetailId The ID of the order detail to delete.
     * @return ResponseEntity with NO_CONTENT status if deletion is successful, NOT_FOUND status otherwise.
     */
    @DeleteMapping("delete/{orderDetailId}")
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable int orderDetailId) {
        boolean deleted = orderDetailsService.deleteOrderDetails(orderDetailId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Retrieves all order details associated with a specific order from the database.
     * 
     * @param orderId The ID of the order.
     * @return ResponseEntity containing a list of order details associated with the specified order.
     */
    @GetMapping("/get-by-order-id/{orderId}")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsByOrderId(@PathVariable int orderId) {
        List<OrderDetails> orderDetails = orderDetailsService.getOrderDetailsByOrderId(orderId);
        return ResponseEntity.ok(orderDetails);
    }
}
