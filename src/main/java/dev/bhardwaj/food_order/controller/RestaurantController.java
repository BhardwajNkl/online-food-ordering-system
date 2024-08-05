package dev.bhardwaj.food_order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bhardwaj.food_order.dto.NewRestaurantDto;
import dev.bhardwaj.food_order.dto.RestaurantDetailsDto;
import dev.bhardwaj.food_order.dto.RestaurantDto;
import dev.bhardwaj.food_order.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
	
	private final RestaurantService restaurantService;
	
	@Autowired
	public RestaurantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}
	
	
	@PostMapping("/create")
	public RestaurantDto createRestaurant(@RequestBody NewRestaurantDto restaurantDto) {
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
	void closeRestaurant(@PathVariable int restaurantId) {
		restaurantService.closeRestaurant(restaurantId);
	}
	
	@GetMapping("/get-restaurant-details/{restaurantId}")
	RestaurantDetailsDto getRestaurantDetails(@PathVariable int restaurantId){
		return restaurantService.getRestaurantDetails(restaurantId);
	}
}
