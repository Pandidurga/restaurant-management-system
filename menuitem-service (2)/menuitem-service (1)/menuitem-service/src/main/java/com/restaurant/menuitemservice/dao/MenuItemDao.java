package com.restaurant.menuitemservice.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.restaurant.menuitemservice.model.MenuItem;
import java.util.List;

/**
 * MenuItemDao is a data access object class that interacts with the database for menu items.
 */
@Repository
public class MenuItemDao {

    @Autowired
    private MenuItemRepository menuItemRepository;

    /**
     * Retrieves all menu items.
     *
     * @return List of all menu items
     */
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    /**
     * Retrieves a menu item by its ID.
     *
     * @param menuItemId ID of the menu item to retrieve
     * @return The menu item with the specified ID, or null if not found
     */
    public MenuItem getMenuItemById(int menuItemId) {
        return menuItemRepository.findById(menuItemId).orElse(null);
    }

    /**
     * Creates a new menu item.
     *
     * @param menuItem The menu item to create
     * @return The created menu item
     */
    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    /**
     * Updates an existing menu item by its ID.
     *
     * @param menuItemId      ID of the menu item to update
     * @param updatedMenuItem The updated menu item
     * @return The updated menu item if successful, or null if the menu item is not found
     */
    public MenuItem updateMenuItem(int menuItemId, MenuItem updatedMenuItem) {
        MenuItem existingMenuItem = menuItemRepository.findById(menuItemId).orElse(null);
        if (existingMenuItem != null) {
            existingMenuItem.setItemName(updatedMenuItem.getItemName());
            existingMenuItem.setUnitPrice(updatedMenuItem.getUnitPrice());
            existingMenuItem.setAvailability(updatedMenuItem.isAvailability());
            return menuItemRepository.save(existingMenuItem);
        }
        return null;
    }

    /**
     * Deletes a menu item by its ID.
     *
     * @param menuItemId ID of the menu item to delete
     * @return true if the menu item was successfully deleted, false otherwise
     */
    public boolean deleteMenuItem(int menuItemId) {
        if (menuItemRepository.existsById(menuItemId)) {
            menuItemRepository.deleteById(menuItemId);
            return true;
        }
        return false;
    }
    
    /**
     * Retrieves all available menu items.
     *
     * @return List of available menu items
     */
    public List<MenuItem> findAvailableMenuItems() {
        return menuItemRepository.findAvailableMenuItems();
    }
}
