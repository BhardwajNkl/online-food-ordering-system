package dev.bhardwaj.food_order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bhardwaj.food_order.dto.NewRatingDto;
import dev.bhardwaj.food_order.dto.RatingDetailsDto;
import dev.bhardwaj.food_order.dto.RatingDto;
import dev.bhardwaj.food_order.entity.Rating;
import dev.bhardwaj.food_order.service.RatingService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rating")
@Validated
public class RatingController {
	
	private final RatingService ratingService;
	
	@Autowired
	public RatingController(RatingService ratingService){
		this.ratingService = ratingService;
	}
	
	@PostMapping("/add-rating")
	RatingDto addRating(@Valid @RequestBody NewRatingDto ratingDto) {
		return ratingService.createRating(ratingDto);
	}
	
	
	@GetMapping("/get-ratings-given-by-customer/{customerId}")
	List<RatingDetailsDto> getRatingsGivenByCustomer(@PathVariable int customerId){
		return ratingService.getRatingsGivenByCustomer(customerId);
	}
	
	@GetMapping("/get-dish-ratings/{dishId}")
	List<RatingDetailsDto> getRatings(@PathVariable int dishId){
		return ratingService.getRatingsForDish(dishId);
	}
}
