package dev.bhardwaj.food_order.service;

import java.util.List;

import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Rating;
import dev.bhardwaj.food_order.entity.Review;

public interface DishService {
	// requirements
	List<Dish> getDishesByCuisine(int restaurantId);
	Dish getDishDetails(int dishId);
	List<Dish> getDishesBasedOnRating();
	
	// other
	void createDish();
	void updateDish();
	void deleteDish();
	List<Rating> getRatings();
	List<Review> getReviews();
	
	
}
