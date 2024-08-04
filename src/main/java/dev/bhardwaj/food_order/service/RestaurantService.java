package dev.bhardwaj.food_order.service;

import java.util.List;

import dev.bhardwaj.food_order.dto.CreateDishDto;
import dev.bhardwaj.food_order.dto.CreateRestaurantDto;
import dev.bhardwaj.food_order.dto.UpdateDishDto;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Order;
import dev.bhardwaj.food_order.entity.Restaurant;

public interface RestaurantService{
	Restaurant createRestaurant(CreateRestaurantDto restaurantDto);
	void deleteRestaurant(int restaurnatId);
	
	void openRestaurant(int restaurantId);
	void closeRestaurant(int restaurantId);
	
	Dish addDish(CreateDishDto dishDto);
	void deleteDish(int dishId);
	Dish updateDish(UpdateDishDto dishDto);
	
	List<Dish> getDishesFromRestaurantByCuisine(int restaurantId, String cuisine);
	
	List<Order> getAllOrdersForRestaurant(int restaurantId);
}