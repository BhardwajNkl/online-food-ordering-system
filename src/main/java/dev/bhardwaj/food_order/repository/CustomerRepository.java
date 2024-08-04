package dev.bhardwaj.food_order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.bhardwaj.food_order.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
