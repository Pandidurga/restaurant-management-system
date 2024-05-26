package com.restaurant.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.restaurant.orderservice.dao.OrderDao;
import com.restaurant.orderservice.model.Order;
import com.restaurant.orderservice.model.OrderStatus;
import com.restaurant.tablemanagementservice.model.RestaurantTable;
import com.restaurant.tablemanagementservice.model.TableStatus;

import java.sql.Timestamp;
import java.util.List;

/**
 * Service implementation class for managing orders.
 * Implements methods for retrieving, creating, updating, and deleting orders.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private final OrderDao orderDao;

    @Autowired
    private RestTemplate restTemplate;

    private final String tableServiceUrl = "http://localhost:8083/api/restaurant-tables"; // Base URL for table service

    /**
     * Constructs a new OrderServiceImpl with the specified OrderDao.
     * 
     * @param orderDao The OrderDao to be used by the service.
     */
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    @Override
    public Order getOrderById(int orderId) {
        return orderDao.getOrderById(orderId);
    }

    @Override
    public Order createOrder(int tableId) {
        Order order = new Order();

        // Fetch the table by ID and update its status
        RestaurantTable restaurantTable = fetchTableById(tableId);
        updateTableStatus(restaurantTable, TableStatus.OCCUPIED);

        // Create and save the order
        order.setRestaurantTable(restaurantTable);
        order.setStatus(OrderStatus.PENDING);
        order.setDateTime(new Timestamp(System.currentTimeMillis()));
        order.setTotalAmount(0);
        return orderDao.createOrder(order);
    }

    @Override
    public Order updateOrder(int orderId, int tableId) {
        Order order = orderDao.getOrderById(orderId);

        if (order == null) {
            throw new RuntimeException("Order with ID " + orderId + " not found.");
        }

        // Fetch the table by ID and update its status
        RestaurantTable restaurantTable = fetchTableById(tableId);
        updateTableStatus(restaurantTable, TableStatus.OCCUPIED);

        // Update the order details
        order.setRestaurantTable(restaurantTable);
        order.setStatus(order.getStatus());
        order.setDateTime(new Timestamp(System.currentTimeMillis()));

        return orderDao.updateOrder(orderId, order);
    }

    @Override
    public void updateOrderTotalAmount(int orderId, Order order) {
        orderDao.updateOrder(orderId, order);
    }

    @Override
    public boolean deleteOrder(int orderId) {
        return orderDao.deleteOrder(orderId);
    }

    @Override
    public Order cancelOrder(int orderId) {
        Order order = orderDao.getOrderById(orderId);

        if (order == null) {
            throw new RuntimeException("Order with ID " + orderId + " not found.");
        }

        order.setStatus(OrderStatus.CANCELLED);

        // Update the table status to "VACANT"
        RestaurantTable restaurantTable = order.getRestaurantTable();
        updateTableStatus(restaurantTable, TableStatus.VACANT);

        return orderDao.updateOrder(orderId, order);
    }
    
    /**
     * Fetches a table by its ID from the table service.
     * 
     * @param tableId The ID of the table to fetch.
     * @return The RestaurantTable object corresponding to the specified ID.
     */
    private RestaurantTable fetchTableById(int tableId) {
        String getTableByIdUrl = tableServiceUrl + "/get-by-id/" + tableId;
        return restTemplate.getForObject(getTableByIdUrl, RestaurantTable.class);
    }

    /**
     * Updates the status of a table in the table service.
     * 
     * @param restaurantTable The RestaurantTable object representing the table to update.
     * @param status          The new status of the table.
     */
    private void updateTableStatus(RestaurantTable restaurantTable, TableStatus status) {
        restaurantTable.setStatus(status);
        String updateTableUrl = tableServiceUrl + "/update/" + restaurantTable.getTableId();
        restTemplate.put(updateTableUrl, restaurantTable);
    }
    
    /**
     * Updates the status of an order by its ID.
     * 
     * @param orderId The ID of the order to update.
     * @param order   The updated order object containing the new status.
     */
    public void updateOrderStatus(int orderId,Order order) {
        orderDao.updateOrder(orderId, order);
    }
    
    @Override
    public Order generateBill(int orderId) {
        // Retrieve the order by its ID
        Order order = orderDao.getOrderById(orderId);

        // Check if the order exists
        if (order == null) {
            // Throw a RuntimeException if the order is not found
            throw new RuntimeException("Order with ID " + orderId + " not found.");
        }

        // Update the status of the order to COMPLETED
        order.setStatus(OrderStatus.COMPLETED);

        // Retrieve the associated restaurant table
        RestaurantTable restaurantTable = order.getRestaurantTable();

        // Update the status of the restaurant table to VACANT
        updateTableStatus(restaurantTable, TableStatus.VACANT);

        // Update the order in the database and return the updated order
        return orderDao.updateOrder(orderId, order);
    }

}
