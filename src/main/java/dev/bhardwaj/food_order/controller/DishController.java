package dev.bhardwaj.food_order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bhardwaj.food_order.dto.DishDetailsDto;
import dev.bhardwaj.food_order.dto.DishDto;
import dev.bhardwaj.food_order.dto.NewDishDto;
import dev.bhardwaj.food_order.dto.UpdateDishDto;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.service.DishService;

@RestController
@RequestMapping("/api/dish")
public class DishController {
	
	private final DishService dishService;
	
	@Autowired
	public DishController(DishService dishService) {
		this.dishService = dishService;
	}
	
	
	@PostMapping("/add-dish")
	DishDto addDish(@RequestBody NewDishDto dishDto) {
		return dishService.createDish(dishDto);
	}
	
	@PutMapping("/update-dish")
	DishDto updteDish(@RequestBody UpdateDishDto dishDto) {
		return dishService.updateDish(dishDto);
	}
	
	@DeleteMapping("/delete-dish/{dishId}")
	void deleteDish(@PathVariable int dishId) {
		dishService.deleteDish(dishId);
	}
	
	@GetMapping("/get-dish-details/{dishId}")
	DishDetailsDto getDishDetails(@PathVariable int dishId) {
		return dishService.getDishDetails(dishId);
	}
	
	
	@GetMapping("/get-dishes-from-restaurant-by-cuisine/{restaurantId}/{cuisine}")
	List<DishDto> getDishesFromRestaurantByCuisine(@PathVariable int restaurantId, @PathVariable String cuisine){
		return dishService.getDishesFromRestaurantByCuisine(restaurantId, cuisine);
	}
	
	
	@GetMapping("/get-dishes-by-cuisine/{cuisine}")
	List<DishDto> getDishesByCuisine(@PathVariable String cuisine){
		return dishService.getDishesByCuisine(cuisine);
	}
	
	@GetMapping("/get-dishes-based-on-rating")
	List<DishDto> getDishesBasedOnRating(){
		return dishService.getDishesBasedOnRating();
	}
}
