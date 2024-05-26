package com.restaurant.orderservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.restaurant.menuitemservice.model.MenuItem;
import com.restaurant.orderservice.dao.OrderDetailsDao;
import com.restaurant.orderservice.model.Order;
import com.restaurant.orderservice.model.OrderDetails;
import com.restaurant.orderservice.model.OrderStatus;

/**
 * Service implementation for managing order details.
 */
@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private final OrderDetailsDao orderDetailsDao;

    @Autowired
    private final OrderService orderService;

    @Autowired
    private final RestTemplate restTemplate;

    private static final String MENU_ITEM_SERVICE_URL = "http://localhost:8082/api/menu-items";

    /**
     * Constructs a new OrderDetailsServiceImpl with the specified dependencies.
     * 
     * @param orderDetailsDao The data access object for order details.
     * @param orderService    The service for managing orders.
     * @param restTemplate    The REST template for making HTTP requests.
     */
    public OrderDetailsServiceImpl(OrderDetailsDao orderDetailsDao, OrderService orderService, RestTemplate restTemplate) {
        this.orderDetailsDao = orderDetailsDao;
        this.orderService = orderService;
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieves all order details from the database.
     * 
     * @return A list of all order details.
     */
    @Override
    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsDao.getAllOrderDetails();
    }

    /**
     * Retrieves an order detail by its ID from the database.
     * 
     * @param orderDetailId The ID of the order detail to retrieve.
     * @return The order detail with the specified ID, or null if not found.
     */
    @Override
    public OrderDetails getOrderDetailsById(int orderDetailId) {
        return orderDetailsDao.getOrderDetailsById(orderDetailId);
    }

    /**
     * Creates a new order detail in the database.
     * 
     * @param orderId    The ID of the associated order.
     * @param menuItemId The ID of the menu item.
     * @param quantity   The quantity of the menu item ordered.
     * @return The created order detail.
     */
    @Override
    public OrderDetails createOrderDetails(int orderId, int menuItemId, int quantity) {
        Order order = orderService.getOrderById(orderId);

        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }
        order.setStatus(OrderStatus.IN_PROGRESS);
        orderService.updateOrderStatus(orderId,order);
        String menuItemUrl = MENU_ITEM_SERVICE_URL + "/get-by-id/" + menuItemId;
        MenuItem menuItem = restTemplate.getForObject(menuItemUrl, MenuItem.class);

        if (menuItem == null) {
            throw new IllegalArgumentException("Menu item not found");
        }

        // Check if OrderDetails already exists for this order and menu item
        OrderDetails existingOrderDetails = orderDetailsDao.findByOrderAndMenuItem(order, menuItem);
        if (existingOrderDetails != null) {
            existingOrderDetails.setQuantity(existingOrderDetails.getQuantity() + quantity);
            orderDetailsDao.updateOrderDetails(existingOrderDetails);
            updateOrderTotalAmount(order);
            return orderDetailsDao.updateOrderDetails(existingOrderDetails);
        } else {
            // Create a new OrderDetails entry
            OrderDetails orderDetails = new OrderDetails(order, menuItem, quantity);
            orderDetailsDao.createOrderDetails(orderDetails);
            updateOrderTotalAmount(order);
            return orderDetailsDao.createOrderDetails(orderDetails);
        }
    }

    /**
     * Updates an existing order detail in the database.
     * 
     * @param orderDetailId The ID of the order detail to update.
     * @param orderId       The ID of the associated order.
     * @param menuItemId    The ID of the menu item.
     * @param quantity      The quantity of the menu item ordered.
     * @return The updated order detail.
     */
    @Override
    public OrderDetails updateOrderDetails(int orderDetailId, int orderId, int menuItemId, int quantity) {
        Order order = orderService.getOrderById(orderId);

        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        String menuItemUrl = MENU_ITEM_SERVICE_URL + "/get-by-id/" + menuItemId;
        MenuItem menuItem = restTemplate.getForObject(menuItemUrl, MenuItem.class);

        if (menuItem == null) {
            throw new IllegalArgumentException("Menu item not found");
        }

        OrderDetails orderDetails = orderDetailsDao.getOrderDetailsById(orderDetailId); 
        orderDetails.setOrder(order);
        orderDetails.setMenuItem(menuItem);
        orderDetails.setQuantity(quantity);
        orderDetailsDao.updateOrderDetails(orderDetails);
        updateOrderTotalAmount(order);   
        return orderDetailsDao.updateOrderDetails(orderDetails);
       
    }
    
    /**
     * Updates the total amount of an order based on its order details.
     * 
     * @param order The order to update.
     */
    private void updateOrderTotalAmount(Order order) {
        // Retrieve all order details associated with the order
        List<OrderDetails> orderDetailsList = getOrderDetailsByOrderId(order.getOrderId());

        // Calculate the total cost based on all order details
        double totalAmount = 0;
        for(OrderDetails orderdetail:orderDetailsList) {
        	   totalAmount = totalAmount+orderdetail.getTotalCost();
        }

        // Update the order's total amount
        order.setTotalAmount(totalAmount);
        orderService.updateOrderTotalAmount(order.getOrderId(), order);
    }

    /**
     * Deletes an order detail from the database.
     * 
     * @param orderDetailId The ID of the order detail to delete.
     * @return True if the order detail was deleted successfully, otherwise false.
     */
    @Override
    public boolean deleteOrderDetails(int orderDetailId) {
        OrderDetails orderDetails = orderDetailsDao.getOrderDetailsById(orderDetailId);
        if (orderDetails == null) {
            throw new IllegalArgumentException("Order detail not found");
        }
        Order order = orderDetails.getOrder();
        int updatedTotalAmount = (int) (        order.getTotalAmount() - orderDetails.getTotalCost());
        order.setTotalAmount(updatedTotalAmount);
        orderService.updateOrderTotalAmount(order.getOrderId(), order);

        return orderDetailsDao.deleteOrderDetails(orderDetailId);
    }

    /**
     * Retrieves all order details associated with a specific order.
     * 
     * @param orderId The ID of the order.
     * @return A list of order details associated with the specified order.
     */
    public List<OrderDetails> getOrderDetailsByOrderId(int orderId) {
        return orderDetailsDao.getOrderDetailsByOrderId(orderId);
    }
}

