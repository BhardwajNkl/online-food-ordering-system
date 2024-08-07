package dev.bhardwaj.food_order.service;

import dev.bhardwaj.food_order.dto.NewRestaurantDto;
import dev.bhardwaj.food_order.dto.RestaurantDetailsDto;
import dev.bhardwaj.food_order.dto.RestaurantDto;

public interface RestaurantService{
	RestaurantDto createRestaurant(NewRestaurantDto restaurantDto);
	RestaurantDetailsDto getRestaurantDetails(int restaurantId);
	void deleteRestaurant(int restaurnatId);
	void openRestaurant(int restaurantId);
	void closeRestaurant(int restaurantId);
}