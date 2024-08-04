package dev.bhardwaj.food_order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bhardwaj.food_order.dto.CreateDishDto;
import dev.bhardwaj.food_order.dto.CreateRestaurantDto;
import dev.bhardwaj.food_order.dto.UpdateDishDto;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Order;
import dev.bhardwaj.food_order.entity.Restaurant;
import dev.bhardwaj.food_order.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
	
	private final RestaurantService restaurantService;
	
	public RestaurantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}
	
	
	@PostMapping("/create")
	public Restaurant createRestaurant(@RequestBody CreateRestaurantDto restaurantDto) {
		return restaurantService.createRestaurant(restaurantDto);
	}
	
	@DeleteMapping("/delete/{restaurantId}")
	void deleteRestaurant(@PathVariable int restaurantId) {
		restaurantService.deleteRestaurant(restaurantId);
	}
	
	@PostMapping("/open/{restaurantId}")
	void openRestaurant(@PathVariable int restaurantId) {
		restaurantService.openRestaurant(restaurantId);
	}
	
	@PostMapping("/close/{restaurantId}")
	void closeRestaurant(int restaurantId) {
		restaurantService.closeRestaurant(restaurantId);
	}
	
	@PostMapping("/add-dish")
	Dish addDish(@RequestBody CreateDishDto dishDto) {
		return restaurantService.addDish(dishDto);
	}
	@DeleteMapping("/delete-dish/{dishId}")
	void deleteDish(@PathVariable int dishId) {
		restaurantService.deleteDish(dishId);
	}
	
	@PutMapping("/update-dish")
	Dish updteDish(@RequestBody UpdateDishDto dishDto) {
		return restaurantService.updateDish(dishDto);
	}
	
	
	@GetMapping("/get-dishes-from-restaurant-by-cuisine/{restaurantId}/{cuisine}")
	List<Dish> getDishesFromRestaurantByCuisine(@PathVariable int restaurantId, @PathVariable String cuisine){
		return restaurantService.getDishesFromRestaurantByCuisine(restaurantId, cuisine);
	}
	
	@GetMapping("/get-all-orders-for-restaurant/{restaurantId}")
	List<Order> getAllOrdersForRestaurant(@PathVariable int restaurantId){
		return restaurantService.getAllOrdersForRestaurant(restaurantId);
	}
}
