package com.restaurant.orderservice.dao;

import com.restaurant.menuitemservice.model.MenuItem;
import com.restaurant.orderservice.model.Order;
import com.restaurant.orderservice.model.OrderDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing OrderDetails entities.
 */
@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

    /**
     * Finds an order detail by its associated order and menu item.
     * 
     * @param order The order associated with the order detail.
     * @param menuItem The menu item associated with the order detail.
     * @return The order detail with the specified order and menu item, or null if not found.
     */
    @Query("SELECT od FROM OrderDetails od WHERE od.order = :order AND od.menuItem = :menuItem")
    OrderDetails findByOrderAndMenuItem(@Param("order") Order order, @Param("menuItem") MenuItem menuItem);

    /**
     * Retrieves all order details associated with a specific order.
     * 
     * @param orderId The ID of the order.
     * @return A list of order details associated with the specified order.
     */
    @Query("SELECT od FROM OrderDetails od WHERE od.order.orderId = :orderId")
    List<OrderDetails> findByOrderId(@Param("orderId") int orderId);
}
