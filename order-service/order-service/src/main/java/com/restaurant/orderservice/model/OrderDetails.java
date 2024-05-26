package com.restaurant.orderservice.model;

import com.restaurant.menuitemservice.model.MenuItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entity class representing the details of an order, including the menu item, quantity, and total cost.
 */
@Entity
@Table(name = "order_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private int orderDetailId; // Variable representing the unique ID of the order detail.

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order; // Variable representing the order to which the order detail belongs.

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem; // Variable representing the menu item associated with the order detail.

    @Column(name = "unit_price")
    private double unitPrice; // Variable representing the unit price of the menu item.

    private int quantity; // Variable representing the quantity of the menu item ordered.

    @Column(name = "total_cost")
    private double totalCost; // Variable representing the total cost of the order detail.

    // Constructors
    public OrderDetails() {
    }

    /**
     * Constructs a new OrderDetails object with the specified order, menu item, and quantity.
     * 
     * @param order    The order associated with the order detail.
     * @param menuItem The menu item associated with the order detail.
     * @param quantity The quantity of the menu item ordered.
     */
    public OrderDetails(Order order, MenuItem menuItem, int quantity) {
        this.order = order;
        this.menuItem = menuItem;
        this.unitPrice = menuItem.getUnitPrice();
        this.quantity = quantity;
        calculateTotalCost();
    }

    // Getters and setters
    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
        this.unitPrice = menuItem.getUnitPrice();
        calculateTotalCost();
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the menu item ordered and recalculates the total cost.
     * 
     * @param quantity The quantity of the menu item ordered.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotalCost();
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    // Calculate total cost based on unit price and quantity
    private void calculateTotalCost() {
        this.totalCost = this.unitPrice * this.quantity;
    }

    // toString method for debugging and logging
    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderDetailId=" + orderDetailId +
                ", order=" + order +
                ", menuItem=" + menuItem +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", totalCost=" + totalCost +
                '}';
    }
}
