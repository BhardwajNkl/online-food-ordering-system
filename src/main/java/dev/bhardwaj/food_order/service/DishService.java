package dev.bhardwaj.food_order.service;

import java.util.List;

import dev.bhardwaj.food_order.dto.DishDetailsDto;
import dev.bhardwaj.food_order.dto.DishDto;
import dev.bhardwaj.food_order.dto.NewDishDto;
import dev.bhardwaj.food_order.dto.UpdateDishDto;
import dev.bhardwaj.food_order.entity.Dish;

public interface DishService {
	DishDto createDish(NewDishDto dishDto);
	DishDto updateDish(UpdateDishDto dishDto);
	void deleteDish(int dishId);
	DishDetailsDto getDishDetails(int dishId);
//	Dish getDish(int dishId); // this method may be omitted.
	
	List<DishDto> getDishesByCuisine(String cuisine);
	List<DishDto> getDishesFromRestaurantByCuisine(int restaurantId, String cuisine);
	List<DishDto> getDishesBasedOnRating();
}
