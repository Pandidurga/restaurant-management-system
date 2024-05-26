package com.restaurant.orderservice.service;

import java.util.List;

import com.restaurant.orderservice.model.Order;

/**
 * Service interface for managing orders.
 * Defines methods for retrieving, creating, updating, and deleting orders.
 */
public interface OrderService {
    /**
     * Retrieves all orders.
     * 
     * @return A list of all orders.
     */
    List<Order> getAllOrders();

    /**
     * Retrieves an order by its ID.
     * 
     * @param orderId The ID of the order to retrieve.
     * @return The order with the specified ID, or null if not found.
     */
    Order getOrderById(int orderId);

    /**
     * Creates a new order for the specified table.
     * 
     * @param tableId The ID of the table for which to create the order.
     * @return The newly created order.
     */
    Order createOrder(int tableId);

    /**
     * Updates an existing order with the specified ID and table ID.
     * 
     * @param orderId The ID of the order to update.
     * @param tableId The new table ID for the order.
     * @return The updated order, or null if the order does not exist.
     */
    Order updateOrder(int orderId, int tableId);

    /**
     * Updates the total amount of an order.
     * 
     * @param orderId The ID of the order to update.
     * @param order   The updated order object containing the new total amount.
     */
    void updateOrderTotalAmount(int orderId, Order order);

    /**
     * Updates the status of an order.
     * 
     * @param orderId The ID of the order to update.
     * @param order   The updated order object containing the new status.
     */
    void updateOrderStatus(int orderId, Order order);

    /**
     * Deletes an order by its ID.
     * 
     * @param orderId The ID of the order to delete.
     * @return True if the order was successfully deleted, false otherwise.
     */
    boolean deleteOrder(int orderId);
    
    /**
     * Cancels an order by its ID.
     * 
     * @param orderId The ID of the order to cancel.
     * @return The canceled order, or null if the order does not exist.
     */
    Order cancelOrder(int orderId);
    
    /**
     * Generates a bill for an order by its ID.
     * 
     * @param orderId The ID of the order for which to generate the bill.
     * @return The completed order with the bill generated, or null if the order does not exist.
     */
    Order generateBill(int orderId);
}
