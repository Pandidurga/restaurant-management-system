package com.restaurant.tablemanagementservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.tablemanagementservice.dao.RestaurantTableDao;
import com.restaurant.tablemanagementservice.model.RestaurantTable;
import com.restaurant.tablemanagementservice.model.TableStatus;

import java.util.List;

@Service
public class RestaurantTableServiceImpl implements RestaurantTableService {

    @Autowired
    private RestaurantTableDao restaurantTableDao;

    /**
     * Retrieves all restaurant tables.
     *
     * @return List of all restaurant tables.
     */
    @Override
    public List<RestaurantTable> getAllTables() {
        return restaurantTableDao.getAllTables();
    }

    /**
     * Retrieves a restaurant table by its ID.
     *
     * @param tableId The ID of the restaurant table to retrieve.
     * @return The restaurant table with the specified ID.
     */
    @Override
    public RestaurantTable getTableById(int tableId) {
        return restaurantTableDao.getTableById(tableId);
    }

    /**
     * Creates a new restaurant table.
     *
     * @param table The restaurant table to create.
     * @return The created restaurant table.
     */
    @Override
    public RestaurantTable createTable(RestaurantTable table) {
        return restaurantTableDao.createTable(table);
    }

    /**
     * Updates an existing restaurant table by its ID.
     *
     * @param tableId The ID of the restaurant table to update.
     * @param table   The updated information for the restaurant table.
     * @return The updated restaurant table.
     */
    @Override
    public RestaurantTable updateTable(int tableId, RestaurantTable table) {
        return restaurantTableDao.updateTable(tableId, table);
    }

    /**
     * Deletes a restaurant table by its ID.
     *
     * @param tableId The ID of the restaurant table to delete.
     * @return True if the deletion was successful, false otherwise.
     */
    @Override
    public boolean deleteTable(int tableId) {
        return restaurantTableDao.deleteTable(tableId);
    }
    
    /**
     * Retrieves all vacant tables.
     *
     * @return List of all vacant restaurant tables.
     */
    @Override
    public List<RestaurantTable> getVacantTables() {
        return restaurantTableDao.findByStatus(TableStatus.VACANT);
    }
}
