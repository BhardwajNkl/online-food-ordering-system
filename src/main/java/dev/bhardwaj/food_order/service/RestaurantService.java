package dev.bhardwaj.food_order.service;

import dev.bhardwaj.food_order.dto.NewRestaurantDto;
import dev.bhardwaj.food_order.dto.RestaurantDetailsDto;
import dev.bhardwaj.food_order.dto.RestaurantDto;
import dev.bhardwaj.food_order.entity.Restaurant;

public interface RestaurantService{
	RestaurantDto createRestaurant(NewRestaurantDto restaurantDto);
	RestaurantDetailsDto getRestaurantDetails(int restaurantId);
	Restaurant getRestaurant(int restaurantId); // not required. may be being used by other services.
	void deleteRestaurant(int restaurnatId);
	
	void openRestaurant(int restaurantId);
	void closeRestaurant(int restaurantId);
}