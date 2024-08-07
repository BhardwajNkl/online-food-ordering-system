package dev.bhardwaj.food_order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.bhardwaj.food_order.entity.Cuisine;
import dev.bhardwaj.food_order.entity.Dish;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
	List<Dish> findByCuisine(Cuisine cuisine);
	
	@Query(value = "select * from dish d where d.restaurant_id =:restaurantId and d.cuisine =:cuisine", nativeQuery = true)
    List<Dish> findByRestaurantIdAndCuisine(@Param("restaurantId") int restaurantId, @Param("cuisine") int cuisine);
	
	List<Dish> findAllByOrderByAverageRatingDesc();
	
}
