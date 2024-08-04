package dev.bhardwaj.food_order.service;

import java.util.List;

import dev.bhardwaj.food_order.dto.CreateDishDto;
import dev.bhardwaj.food_order.dto.UpdateDishDto;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Rating;
import dev.bhardwaj.food_order.entity.Review;

public interface DishService {
	Dish createDish(CreateDishDto dishDto);
	Dish updateDish(UpdateDishDto dishDto);
	void deleteDish(int dishId);
	Dish getDishDetails(int dishId);
	
	List<Dish> getDishesByCuisine(String cuisine);
	List<Dish> getDishesBasedOnRating();
	
	List<Rating> getRatings(int dishId);
	List<Review> getReviews(int dishId);
}
