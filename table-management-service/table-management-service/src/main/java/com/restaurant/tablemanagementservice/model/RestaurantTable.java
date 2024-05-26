package com.restaurant.tablemanagementservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurant_tables")
// This class represents a table in the restaurant management system.
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // The unique identifier for the table.
    private int tableId;

    @Column(nullable = false, unique = true)
    // The number assigned to the table, must be unique.
    private int tableNumber; // New field for table number

    @Column(nullable = false)
    // The number of seats available at the table.
    private int seats;

    @Enumerated(EnumType.STRING)
    // The status of the table, whether it's vacant, occupied, etc.
    private TableStatus status;

    // Default constructor
    public RestaurantTable() {
    }

    // Constructor with parameters
    public RestaurantTable(int tableNumber, int seats, TableStatus status) {
        this.tableNumber = tableNumber;
        this.seats = seats;
        this.status = status;
    }

    // Getter method for tableId
    public int getTableId() {
        return tableId;
    }

    // Setter method for tableId
    public void setTableId(int id) {
        this.tableId = id;
    }

    // Getter method for tableNumber
    public int getTableNumber() {
        return tableNumber;
    }

    // Setter method for tableNumber
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    // Getter method for seats
    public int getSeats() {
        return seats;
    }

    // Setter method for seats
    public void setSeats(int seats) {
        this.seats = seats;
    }

    // Getter method for status
    public TableStatus getStatus() {
        return status;
    }

    // Setter method for status
    public void setStatus(TableStatus status) {
        this.status = status;
    }
}
