package com.restaurant.orderservice.model;

/**
 * Enum representing the status of an order.
 * Possible values are PENDING, IN_PROGRESS, COMPLETED, and CANCELLED.
 */
public enum OrderStatus {
    PENDING,         // The order is pending and has not been processed yet.
    IN_PROGRESS,     // The order is currently being processed.
    COMPLETED,       // The order has been completed successfully.
    CANCELLED        // The order has been cancelled and will not be processed.
}
