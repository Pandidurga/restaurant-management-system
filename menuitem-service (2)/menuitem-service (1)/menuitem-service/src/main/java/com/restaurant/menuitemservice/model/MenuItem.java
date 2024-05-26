package com.restaurant.menuitemservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * MenuItem is an entity class representing a menu item.
 * It is mapped to a database table using JPA annotations.
 */
@Entity  // Indicates that this class is an entity
@Table  // Specifies the name of the database table to which this entity is mapped
public class MenuItem {

    @Id  // Indicates the primary key field
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Specifies the strategy for generating primary key values
    private int menuItemId;

    @Column(name = "item_name", nullable = false, unique = true)  // Specifies the column mapping for itemName
    private String itemName;

    @Column(nullable = false)  // Specifies the column mapping for unitPrice
    private double unitPrice;

    @Column(nullable = false)  // Specifies the column mapping for availability
    private boolean availability;

    // Constructors
    public MenuItem() {
        // Default constructor
    }

    /**
     * Constructs a MenuItem with the specified name, unit price, and availability.
     *
     * @param itemName     The name of the menu item
     * @param unitPrice    The unit price of the menu item
     * @param availability The availability status of the menu item
     */
    public MenuItem(String itemName, double unitPrice, boolean availability) {
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.availability = availability;
    }

    // Getter and Setter methods

    /**
     * Gets the ID of the menu item.
     *
     * @return The ID of the menu item
     */
    public int getMenuItemId() {
        return menuItemId;
    }

    /**
     * Sets the ID of the menu item.
     *
     * @param menuItemId The ID of the menu item
     */
    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    /**
     * Gets the name of the menu item.
     *
     * @return The name of the menu item
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the name of the menu item.
     *
     * @param itemName The name of the menu item
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Gets the unit price of the menu item.
     *
     * @return The unit price of the menu item
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the unit price of the menu item.
     *
     * @param unitPrice The unit price of the menu item
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Checks if the menu item is available.
     *
     * @return true if the menu item is available, false otherwise
     */
    public boolean isAvailability() {
        return availability;
    }

    /**
     * Sets the availability status of the menu item.
     *
     * @param availability The availability status of the menu item
     */
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    // toString() method for debugging and logging
    @Override
    public String toString() {
        return "MenuItem{" +
                "menuItemId=" + menuItemId +
                ", itemName='" + itemName + '\'' +
                ", unitPrice=" + unitPrice +
                ", availability=" + availability +
                '}';
    }
}
