package com.restaurant.tablemanagementservice.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.restaurant.tablemanagementservice.model.RestaurantTable;
import com.restaurant.tablemanagementservice.model.TableStatus;

import java.util.List;

@Repository
public class RestaurantTableDao {

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    /**
     * Retrieves all restaurant tables.
     *
     * @return List of all restaurant tables.
     */
    public List<RestaurantTable> getAllTables() {
        return restaurantTableRepository.findAll();
    }

    /**
     * Retrieves a restaurant table by its ID.
     *
     * @param tableId The ID of the restaurant table to retrieve.
     * @return The restaurant table with the specified ID.
     */
    public RestaurantTable getTableById(int tableId) {
        return restaurantTableRepository.findById(tableId).orElse(null);
    }

    /**
     * Creates a new restaurant table.
     *
     * @param table The restaurant table to create.
     * @return The created restaurant table.
     */
    public RestaurantTable createTable(RestaurantTable table) {
        return restaurantTableRepository.save(table);
    }

    /**
     * Updates an existing restaurant table by its ID.
     *
     * @param tableId The ID of the restaurant table to update.
     * @param table   The updated information for the restaurant table.
     * @return The updated restaurant table.
     */
    public RestaurantTable updateTable(int tableId, RestaurantTable table) {
        if (restaurantTableRepository.existsById(tableId)) {
            table.setTableId(tableId);
            return restaurantTableRepository.save(table);
        }
        return null;
    }

    /**
     * Deletes a restaurant table by its ID.
     *
     * @param tableId The ID of the restaurant table to delete.
     * @return True if the deletion was successful, false otherwise.
     */
    public boolean deleteTable(int tableId) {
        if (restaurantTableRepository.existsById(tableId)) {
            restaurantTableRepository.deleteById(tableId);
            return true;
        }
        return false;
    }
    
    /**
     * Retrieves all restaurant tables with the specified status.
     *
     * @param status The status of the restaurant tables to retrieve.
     * @return List of restaurant tables with the specified status.
     */
    public List<RestaurantTable> findByStatus(TableStatus status) {
        return restaurantTableRepository.findByStatus(status);
    }
}
