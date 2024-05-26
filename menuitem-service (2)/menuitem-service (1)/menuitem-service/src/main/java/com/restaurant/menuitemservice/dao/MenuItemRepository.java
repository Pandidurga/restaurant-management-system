package com.restaurant.menuitemservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.restaurant.menuitemservice.model.MenuItem;

/**
 * MenuItemRepository is a Spring Data JPA repository interface for MenuItem entities.
 * It provides methods for CRUD operations and custom queries.
 */
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    
    /**
     * Custom query to find all available menu items.
     * This query selects menu items where availability is true.
     *
     * @return List of available menu items
     */
    @Query("SELECT m FROM MenuItem m WHERE m.availability = true")
    List<MenuItem> findAvailableMenuItems();
    
    // JpaRepository provides CRUD methods for MenuItem entity
    // with a primary key type of Integer
}
