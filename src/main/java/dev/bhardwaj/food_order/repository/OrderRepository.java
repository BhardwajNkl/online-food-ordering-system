package dev.bhardwaj.food_order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.bhardwaj.food_order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	 @Query(value = "select distinct o.* from `order` o " +
             "join order_dishes od on o.id = od.order_id " +
             "join dish d on od.dish_id = d.id " +
             "where d.restaurant_id = :restaurantId", nativeQuery = true)
	 List<Order> findOrdersByRestaurantId(@Param("restaurantId") int restaurantId);
}
