package com.restaurant.tablemanagementservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restaurant.tablemanagementservice.model.RestaurantTable;
import com.restaurant.tablemanagementservice.service.RestaurantTableService;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant-tables")
public class RestaurantTableController {

    @Autowired
    private RestaurantTableService restaurantTableService;

    /**
     * Retrieves all restaurant tables.
     *
     * @return List of all restaurant tables.
     */
    @GetMapping("/get-all")
    public List<RestaurantTable> getAllTables() {
        return restaurantTableService.getAllTables();
    }

    /**
     * Retrieves a restaurant table by its ID.
     *
     * @param tableId The ID of the restaurant table to retrieve.
     * @return ResponseEntity with the retrieved restaurant table if found, otherwise returns 404.
     */
    @GetMapping("/get-by-id/{tableId}")
    public ResponseEntity<RestaurantTable> getTableById(@PathVariable int tableId) {
        RestaurantTable table = restaurantTableService.getTableById(tableId);
        if (table != null) {
            return ResponseEntity.ok(table);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new restaurant table.
     *
     * @param table The restaurant table to create.
     * @return ResponseEntity with the created restaurant table and status 201.
     */
    @PostMapping("/add")
    public ResponseEntity<RestaurantTable> createTable(@RequestBody RestaurantTable table) {
        RestaurantTable createdTable = restaurantTableService.createTable(table);
        return ResponseEntity.status(201).body(createdTable);
    }

    /**
     * Updates an existing restaurant table by its ID.
     *
     * @param tableId The ID of the restaurant table to update.
     * @param table   The updated information for the restaurant table.
     * @return ResponseEntity with the updated restaurant table if found, otherwise returns 404.
     */
    @PutMapping("/update/{tableId}")
    public ResponseEntity<RestaurantTable> updateTable(@PathVariable int tableId, @RequestBody RestaurantTable table) {
        RestaurantTable updatedTable = restaurantTableService.updateTable(tableId, table);
        if (updatedTable != null) {
            return ResponseEntity.ok(updatedTable);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a restaurant table by its ID.
     *
     * @param tableId The ID of the restaurant table to delete.
     * @return ResponseEntity with no content if the table was deleted successfully, otherwise returns 404.
     */
    @DeleteMapping("/delete/{tableId}")
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public ResponseEntity<Void> deleteTable(@PathVariable int tableId) {
        boolean deleted = restaurantTableService.deleteTable(tableId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all vacant tables.
     *
     * @return List of all vacant restaurant tables.
     */
    @GetMapping("/get-vacant")
    public List<RestaurantTable> getVacantTables() {
        return restaurantTableService.getVacantTables();
    }
}
