package dev.bhardwaj.food_order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Rating;
import dev.bhardwaj.food_order.entity.Review;
import dev.bhardwaj.food_order.service.DishService;

@RestController
@RequestMapping("/api/dish")
public class DishController {
	
	private final DishService dishService;
	
	public DishController(DishService dishService) {
		this.dishService = dishService;
	}
	
	@GetMapping("/get-dish-details/{dishId}")
	Dish getDishDetails(@PathVariable int dishId) {
		return dishService.getDishDetails(dishId);
	}
	
	@GetMapping("/get-dishes-by-cuisine/{cuisine}")
	List<Dish> getDishesByCuisine(@PathVariable String cuisine){
		return dishService.getDishesByCuisine(cuisine);
	}
	
	@GetMapping("/get-dishes-based-on-rating")
	List<Dish> getDishesBasedOnRating(){
		return null;
	}
	
	@GetMapping("/get-dish-ratings/{dishId}")
	List<Rating> getRatings(@PathVariable int dishId){
		return dishService.getRatings(dishId);
	}
	
	@GetMapping("/get-dish-reviews/{dishId}")
	List<Review> getReviews(@PathVariable int dishId){
		return dishService.getReviews(dishId);
	}
}
