package com.restaurant.orderservice.model;

import java.sql.Timestamp;

import com.restaurant.tablemanagementservice.model.RestaurantTable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

/**
 * Represents an order placed in the restaurant.
 * Each order corresponds to a particular table and contains details such as order status, total amount, and date/time of the order.
 */
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId; // Unique identifier for the order

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // Current status of the order

    @Column(name = "total_amount")
    private double totalAmount; // Total amount of the order

    @Column(name = "date_time")
    private Timestamp dateTime; // Date and time when the order was placed

    @ManyToOne
    @JoinColumn(name = "table_id")
    private RestaurantTable restaurantTable; // Table associated with the order

    // Constructors
    public Order() {
    }

    /**
     * Constructs an order with the given parameters.
     * 
     * @param status          The status of the order.
     * @param totalAmount     The total amount of the order.
     * @param dateTime        The date and time of the order.
     * @param restaurantTable The table associated with the order.
     */
    public Order(OrderStatus status, double totalAmount, Timestamp dateTime, RestaurantTable restaurantTable) {
        this.status = status;
        this.totalAmount = totalAmount;
        this.dateTime = dateTime;
        this.restaurantTable = restaurantTable;
    }

    // Getters and Setters

    /**
     * Retrieves the unique identifier of the order.
     * 
     * @return The order ID.
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets the unique identifier of the order.
     * 
     * @param orderId The order ID to set.
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Retrieves the status of the order.
     * 
     * @return The status of the order.
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the order.
     * 
     * @param status The status to set.
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Retrieves the total amount of the order.
     * 
     * @return The total amount of the order.
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total amount of the order.
     * 
     * @param totalAmount The total amount to set.
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Retrieves the date and time when the order was placed.
     * 
     * @return The date and time of the order.
     */
    public Timestamp getDateTime() {
        return dateTime;
    }

    /**
     * Sets the date and time when the order was placed.
     * 
     * @param dateTime The date and time to set.
     */
    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Retrieves the table associated with the order.
     * 
     * @return The restaurant table associated with the order.
     */
    public RestaurantTable getRestaurantTable() {
        return restaurantTable;
    }

    /**
     * Sets the table associated with the order.
     * 
     * @param restaurantTable The restaurant table to set.
     */
    public void setRestaurantTable(RestaurantTable restaurantTable) {
        this.restaurantTable = restaurantTable;
    }

    // toString method
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", status='" + status + '\'' +
                ", totalAmount=" + totalAmount +
                ", dateTime=" + dateTime +
                ", restaurantTable=" + restaurantTable +
                '}';
    }
}
