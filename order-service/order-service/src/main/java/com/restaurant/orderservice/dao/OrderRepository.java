package com.restaurant.orderservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.orderservice.model.Order;

/**
 * Repository interface for accessing Order entities in the database.
 * Extends JpaRepository to inherit basic CRUD operations.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
  
	//add customised methods here

}
