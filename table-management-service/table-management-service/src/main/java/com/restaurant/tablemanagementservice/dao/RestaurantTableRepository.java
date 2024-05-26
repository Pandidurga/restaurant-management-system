package com.restaurant.tablemanagementservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.restaurant.tablemanagementservice.model.RestaurantTable;
import com.restaurant.tablemanagementservice.model.TableStatus;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Integer> {
    // You can add custom query methods here if needed
	
	/**
     * Retrieves all restaurant tables with the specified status.
     *
     * @param status The status of the restaurant tables to retrieve.
     * @return List of restaurant tables with the specified status.
     */
	@Query("SELECT t FROM RestaurantTable t WHERE t.status = :status")
	List<RestaurantTable> findByStatus(TableStatus status);
}
