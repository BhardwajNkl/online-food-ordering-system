package dev.bhardwaj.food_order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bhardwaj.food_order.dto.NewRatingDto;
import dev.bhardwaj.food_order.dto.RatingDetailsDto;
import dev.bhardwaj.food_order.dto.RatingDto;
import dev.bhardwaj.food_order.exception.NotAllowedException;
import dev.bhardwaj.food_order.security.SecurityUser;
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
	ResponseEntity<RatingDto> addRating(@Valid @RequestBody NewRatingDto ratingDto) {
		RatingDto result = ratingService.createRating(ratingDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/get-ratings-given-by-customer/{customerId}")
	ResponseEntity<List<RatingDetailsDto>> getRatingsGivenByCustomer(@PathVariable int customerId,
			@AuthenticationPrincipal SecurityUser securityUser ){
		if(securityUser.getUser().getCustomer().getId()==customerId) {
			List<RatingDetailsDto> result = ratingService.getRatingsGivenByCustomer(customerId);
	        return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			throw new NotAllowedException("You can get only those ratings that you have given");
		}
	}
	
	@GetMapping("/get-dish-ratings/{dishId}")
	ResponseEntity<List<RatingDetailsDto>> getRatings(@PathVariable int dishId){
		List<RatingDetailsDto> result = ratingService.getRatingsForDish(dishId);
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
