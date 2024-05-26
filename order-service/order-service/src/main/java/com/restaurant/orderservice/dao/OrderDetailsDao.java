package com.restaurant.orderservice.dao;

import com.restaurant.menuitemservice.model.MenuItem;
import com.restaurant.orderservice.model.Order;
import com.restaurant.orderservice.model.OrderDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Data Access Object (DAO) class for managing OrderDetails entities.
 */
@Component
public class OrderDetailsDao {
    
    @Autowired
    private final OrderDetailsRepository orderDetailsRepository;

    /**
     * Constructs an instance of the OrderDetailsDao.
     * 
     * @param orderDetailsRepository The repository for OrderDetails entities.
     */
    public OrderDetailsDao(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }

    /**
     * Retrieves all order details from the database.
     * 
     * @return A list of all order details.
     */
    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    /**
     * Retrieves an order detail by its ID.
     * 
     * @param orderDetailId The ID of the order detail to retrieve.
     * @return The order detail with the specified ID, or null if not found.
     */
    public OrderDetails getOrderDetailsById(int orderDetailId) {
        return orderDetailsRepository.findById(orderDetailId).orElse(null);
    }

    /**
     * Creates a new order detail in the database.
     * 
     * @param orderDetails The order detail to create.
     * @return The created order detail.
     */
    public OrderDetails createOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    /**
     * Updates an existing order detail in the database.
     * 
     * @param orderDetails The order detail to update.
     * @return The updated order detail.
     */
    public OrderDetails updateOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }
    
    /**
     * Deletes an order detail from the database by its ID.
     * 
     * @param orderDetailId The ID of the order detail to delete.
     * @return true if the order detail was deleted successfully, false otherwise.
     */
    public boolean deleteOrderDetails(int orderDetailId) {
        if (orderDetailsRepository.existsById(orderDetailId)) {
            orderDetailsRepository.deleteById(orderDetailId);
            return true;
        }
        return false;
    }
    
    /**
     * Finds an order detail by its associated order and menu item.
     * 
     * @param order The order associated with the order detail.
     * @param menuItem The menu item associated with the order detail.
     * @return The order detail with the specified order and menu item, or null if not found.
     */
    public OrderDetails findByOrderAndMenuItem(Order order, MenuItem menuItem) {
        return orderDetailsRepository.findByOrderAndMenuItem(order, menuItem);
    }
    
    /**
     * Retrieves all order details associated with a specific order.
     * 
     * @param orderId The ID of the order.
     * @return A list of order details associated with the specified order.
     */
    public List<OrderDetails> getOrderDetailsByOrderId(int orderId) {
        return orderDetailsRepository.findByOrderId(orderId);
    }
}
