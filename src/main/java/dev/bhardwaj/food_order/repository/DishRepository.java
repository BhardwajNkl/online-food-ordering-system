package dev.bhardwaj.food_order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.bhardwaj.food_order.entity.Dish;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {

}
