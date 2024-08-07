package dev.bhardwaj.food_order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import dev.bhardwaj.food_order.service.DishService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/dish")
@Validated
public class DishController {
	
	private final DishService dishService;
	
	@Autowired
	public DishController(DishService dishService) {
		this.dishService = dishService;
	}
	
	
	@PostMapping("/add-dish")
	ResponseEntity<DishDto> addDish(@Valid @RequestBody NewDishDto dishDto) {
		DishDto result = dishService.createDish(dishDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);

	}
	
	@PutMapping("/update-dish")
	ResponseEntity<DishDto> updteDish(@Valid @RequestBody UpdateDishDto dishDto) {
		DishDto result = dishService.updateDish(dishDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-dish/{dishId}")
	ResponseEntity<?> deleteDish(@PathVariable int dishId) {
		dishService.deleteDish(dishId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/get-dish-details/{dishId}")
	ResponseEntity<DishDetailsDto> getDishDetails(@PathVariable int dishId) {
		DishDetailsDto result = dishService.getDishDetails(dishId);
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	@GetMapping("/get-dishes-from-restaurant-by-cuisine/{restaurantId}/{cuisine}")
	ResponseEntity<List<DishDto>> getDishesFromRestaurantByCuisine(@PathVariable int restaurantId, @PathVariable String cuisine){
		List<DishDto> result = dishService.getDishesFromRestaurantByCuisine(restaurantId, cuisine);
        return new ResponseEntity<>(result, HttpStatus.OK);

	}
	
	@GetMapping("/get-dishes-by-cuisine/{cuisine}")
	ResponseEntity<List<DishDto>> getDishesByCuisine(@PathVariable String cuisine){
		List<DishDto> result = dishService.getDishesByCuisine(cuisine);
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/get-dishes-based-on-rating")
	ResponseEntity<List<DishDto> > getDishesBasedOnRating(){
		List<DishDto> result = dishService.getDishesBasedOnRating();
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
