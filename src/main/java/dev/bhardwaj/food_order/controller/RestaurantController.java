package dev.bhardwaj.food_order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/restaurant")
@Validated
public class RestaurantController {
	
	private final RestaurantService restaurantService;
	
	@Autowired
	public RestaurantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<RestaurantDto> createRestaurant(@Valid @RequestBody NewRestaurantDto restaurantDto) {
		RestaurantDto result = restaurantService.createRestaurant(restaurantDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{restaurantId}")
	ResponseEntity<?> deleteRestaurant(@PathVariable int restaurantId) {
		restaurantService.deleteRestaurant(restaurantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@PostMapping("/open/{restaurantId}")
	ResponseEntity<Boolean> openRestaurant(@PathVariable int restaurantId) {
		restaurantService.openRestaurant(restaurantId);
        return new ResponseEntity<>(true, HttpStatus.OK);
	}
	
	@PostMapping("/close/{restaurantId}")
	ResponseEntity<Boolean> closeRestaurant(@PathVariable int restaurantId) {
		restaurantService.closeRestaurant(restaurantId);
        return new ResponseEntity<>(true, HttpStatus.OK);
	}
	
	
	@GetMapping("/get-restaurant-details/{restaurantId}")
	ResponseEntity<RestaurantDetailsDto> getRestaurantDetails(@PathVariable int restaurantId){
		RestaurantDetailsDto result = restaurantService.getRestaurantDetails(restaurantId);
        return new ResponseEntity<>(result, HttpStatus.OK);

	}
}
