package com.restaurant.orderservice.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.restaurant.orderservice.model.Order;

import java.util.List;

/**
 * DAO (Data Access Object) class for managing orders.
 * Provides methods for CRUD operations on orders.
 */
@Component
public class OrderDao {
    
    @Autowired
    private final OrderRepository orderRepository;

    /**
     * Constructs a new OrderDao with the specified OrderRepository.
     * 
     * @param orderRepository The OrderRepository to be used by the DAO.
     */
    public OrderDao(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Retrieves all orders from the database.
     * 
     * @return A list of all orders.
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Retrieves an order by its ID from the database.
     * 
     * @param orderId The ID of the order to retrieve.
     * @return The order with the specified ID, or null if not found.
     */
    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    /**
     * Creates a new order in the database.
     * 
     * @param order The order to create.
     * @return The created order.
     */
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Updates an existing order in the database.
     * 
     * @param orderId The ID of the order to update.
     * @param order   The updated order object.
     * @return The updated order, or null if the order does not exist.
     */
    public Order updateOrder(int orderId, Order order) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            order.setOrderId(orderId);
            return orderRepository.save(order);
        }
        return null;
    }

    /**
     * Deletes an order from the database by its ID.
     * 
     * @param orderId The ID of the order to delete.
     * @return True if the order was successfully deleted, false otherwise.
     */
    public boolean deleteOrder(int orderId) {
        Order existingOrder = getOrderById(orderId);
        if (existingOrder != null) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }
}
