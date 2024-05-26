package com.restaurant.orderservice.service;

import java.util.List;

import com.restaurant.orderservice.model.OrderDetails;

/**
 * Service interface for managing order details.
 * Defines methods for CRUD operations on order details.
 */
public interface OrderDetailsService {
    
    /**
     * Retrieves all order details from the database.
     * 
     * @return A list of all order details.
     */
    public List<OrderDetails> getAllOrderDetails();

    /**
     * Retrieves an order detail by its ID from the database.
     * 
     * @param orderDetailId The ID of the order detail to retrieve.
     * @return The order detail with the specified ID, or null if not found.
     */
    public OrderDetails getOrderDetailsById(int orderDetailId);

    /**
     * Creates a new order detail in the database.
     * 
     * @param orderId    The ID of the associated order.
     * @param menuItemId The ID of the menu item.
     * @param quantity   The quantity of the menu item ordered.
     * @return The created order detail.
     */
    OrderDetails createOrderDetails(int orderId, int menuItemId, int quantity);
    
    /**
     * Updates an existing order detail in the database.
     * 
     * @param orderDetailId The ID of the order detail to update.
     * @param orderId       The ID of the associated order.
     * @param menuItemId    The ID of the menu item.
     * @param quantity      The quantity of the menu item ordered.
     * @return The updated order detail.
     */
    public OrderDetails updateOrderDetails(int orderDetailId, int orderId, int menuItemId, int quantity);
    
    /**
     * Deletes an order detail from the database by its ID.
     * 
     * @param orderDetailId The ID of the order detail to delete.
     * @return True if the order detail was successfully deleted, false otherwise.
     */
    boolean deleteOrderDetails(int orderDetailId);
    
    /**
     * Retrieves all order details associated with a specific order from the database.
     * 
     * @param orderId The ID of the order.
     * @return A list of order details associated with the specified order.
     */
    public List<OrderDetails> getOrderDetailsByOrderId(int orderId);
}
