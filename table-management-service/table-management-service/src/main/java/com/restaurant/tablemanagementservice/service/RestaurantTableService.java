package com.restaurant.tablemanagementservice.service;

import java.util.List;
import com.restaurant.tablemanagementservice.model.RestaurantTable;

public interface RestaurantTableService {

    /**
     * Retrieves all restaurant tables.
     *
     * @return List of all restaurant tables.
     */
    List<RestaurantTable> getAllTables();

    /**
     * Retrieves a restaurant table by its ID.
     *
     * @param tableId The ID of the restaurant table to retrieve.
     * @return The restaurant table with the specified ID.
     */
    RestaurantTable getTableById(int tableId);

    /**
     * Creates a new restaurant table.
     *
     * @param table The restaurant table to create.
     * @return The created restaurant table.
     */
    RestaurantTable createTable(RestaurantTable table);

    /**
     * Updates an existing restaurant table by its ID.
     *
     * @param tableId The ID of the restaurant table to update.
     * @param table   The updated information for the restaurant table.
     * @return The updated restaurant table.
     */
    RestaurantTable updateTable(int tableId, RestaurantTable table);

    /**
     * Deletes a restaurant table by its ID.
     *
     * @param tableId The ID of the restaurant table to delete.
     * @return True if the deletion was successful, false otherwise.
     */
    boolean deleteTable(int tableId);

    /**
     * Retrieves all vacant tables.
     *
     * @return List of all vacant restaurant tables.
     */
    List<RestaurantTable> getVacantTables();
}
