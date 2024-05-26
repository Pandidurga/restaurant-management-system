package com.restaurant.menuitemservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.restaurant.menuitemservice.model.MenuItem;
import com.restaurant.menuitemservice.service.MenuItemService;

import java.util.List;

/**
 * MenuItemController is a REST controller that handles HTTP requests related to menu items.
 */
@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    /**
     * Retrieves all menu items.
     *
     * @return List of all menu items
     */
    @GetMapping("/get-all")
    public List<MenuItem> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    /**
     * Retrieves a menu item by its ID.
     *
     * @param menuItemId ID of the menu item to retrieve
     * @return ResponseEntity containing the menu item if found, or ResponseEntity.notFound() if not found
     */
    @GetMapping("/get-by-id/{menuItemId}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable int menuItemId) {
        MenuItem menuItem = menuItemService.getMenuItemById(menuItemId);
        if (menuItem != null) {
            return ResponseEntity.ok(menuItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new menu item.
     *
     * @param menuItem The menu item to create
     * @return ResponseEntity containing the created menu item and HTTP status 201 if successful
     */
    @PostMapping("/add")
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem menuItem) {
        MenuItem createdMenuItem = menuItemService.createMenuItem(menuItem);
        return ResponseEntity.status(201).body(createdMenuItem);
    }

    /**
     * Updates an existing menu item by its ID.
     *
     * @param menuItemId      ID of the menu item to update
     * @param menuItem        The updated menu item
     * @return ResponseEntity containing the updated menu item if successful, or ResponseEntity.notFound() if the menu item is not found
     */
    @PutMapping("update/{menuItemId}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable int menuItemId, @RequestBody MenuItem menuItem) {
        MenuItem updatedMenuItem = menuItemService.updateMenuItem(menuItemId, menuItem);
        if (updatedMenuItem != null) {
            return ResponseEntity.ok(updatedMenuItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a menu item by its ID.
     *
     * @param menuItemId ID of the menu item to delete
     * @return ResponseEntity containing HTTP status 204 if successful, or ResponseEntity.notFound() if the menu item is not found
     */
    @DeleteMapping("delete/{menuItemId}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable int menuItemId) {
        boolean deleted = menuItemService.deleteMenuItem(menuItemId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Retrieves all available menu items.
     *
     * @return List of available menu items
     */
    @GetMapping("/get-available")
    public List<MenuItem> getAvailableMenuItems() {
        return menuItemService.getAvailableMenuItems();
    }
}
