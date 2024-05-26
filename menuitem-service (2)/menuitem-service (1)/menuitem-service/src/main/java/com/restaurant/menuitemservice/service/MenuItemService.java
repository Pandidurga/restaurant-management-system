package com.restaurant.menuitemservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurant.menuitemservice.dao.MenuItemDao;
import com.restaurant.menuitemservice.model.MenuItem;

import java.util.List;

/**
 * MenuItemService is a service class that provides methods to interact with menu items.
 * It delegates the operations to MenuItemDao for data access.
 */
@Service
public class MenuItemService {

    @Autowired
    private MenuItemDao menuItemDao;

    /**
     * Retrieves all menu items.
     *
     * @return List of all menu items
     */
    public List<MenuItem> getAllMenuItems() {
        return menuItemDao.getAllMenuItems();
    }

    /**
     * Retrieves a menu item by its ID.
     *
     * @param menuItemId ID of the menu item to retrieve
     * @return The menu item with the specified ID
     */
    public MenuItem getMenuItemById(int menuItemId) {
        return menuItemDao.getMenuItemById(menuItemId);
    }

    /**
     * Creates a new menu item.
     *
     * @param menuItem The menu item to create
     * @return The created menu item
     */
    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemDao.createMenuItem(menuItem);
    }

    /**
     * Updates an existing menu item by its ID.
     *
     * @param menuItemId      ID of the menu item to update
     * @param updatedMenuItem The updated menu item
     * @return The updated menu item
     */
    public MenuItem updateMenuItem(int menuItemId, MenuItem updatedMenuItem) {
        return menuItemDao.updateMenuItem(menuItemId, updatedMenuItem);
    }

    /**
     * Deletes a menu item by its ID.
     *
     * @param menuItemId ID of the menu item to delete
     * @return true if the menu item was successfully deleted, false otherwise
     */
    public boolean deleteMenuItem(int menuItemId) {
        return menuItemDao.deleteMenuItem(menuItemId);
    }

    /**
     * Retrieves all available menu items.
     *
     * @return List of available menu items
     */
    public List<MenuItem> getAvailableMenuItems() {
        return menuItemDao.findAvailableMenuItems();
    }
}
