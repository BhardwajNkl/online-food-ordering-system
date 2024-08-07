package dev.bhardwaj.food_order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.bhardwaj.food_order.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	@Query(value = "select exists ( " +
            " select 1 from `order` o " +
            " join order_dishes od on o.id = od.order_id " +
            " join dish d on od.dish_id = d.id " +
            " where o.customer_id = :customerId " +
            " and d.id = :dishId" +
            ") as ordered", nativeQuery = true)
	Long hasCustomerOrderedDish(@Param("customerId") Long customerId, @Param("dishId") Integer dishId);
}
