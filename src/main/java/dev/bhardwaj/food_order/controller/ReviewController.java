package dev.bhardwaj.food_order.controller;

import java.util.List;

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

import dev.bhardwaj.food_order.dto.NewReviewDto;
import dev.bhardwaj.food_order.dto.ReviewDetailsDto;
import dev.bhardwaj.food_order.dto.ReviewDto;
import dev.bhardwaj.food_order.exception.NotAllowedException;
import dev.bhardwaj.food_order.security.SecurityUser;
import dev.bhardwaj.food_order.service.ReviewService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/review")
@Validated
public class ReviewController {
	
private final ReviewService reviewService;
	
	public ReviewController(ReviewService reviewService){
		this.reviewService = reviewService;
	}
	
	@PostMapping("/add-review")
	ResponseEntity<ReviewDto> addReview(@Valid @RequestBody NewReviewDto reviewDto) {
		ReviewDto result = reviewService.createReview(reviewDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	@GetMapping("/get-reviews-given-by-customer/{customerId}")
	ResponseEntity<List<ReviewDetailsDto>> getReviewsGivenByCustomer(@PathVariable int customerId,
			@AuthenticationPrincipal SecurityUser securityUser ){
		if(securityUser.getUser().getCustomer().getId()==customerId) {
			List<ReviewDetailsDto> result = reviewService.getReviewsGivenByCustomer(customerId);
	        return new ResponseEntity<>(result, HttpStatus.OK);

		} else {
			throw new NotAllowedException("You can get only those reviews that you have given");
		}
	}
	
	@GetMapping("/get-dish-reviews/{dishId}")
	ResponseEntity<List<ReviewDetailsDto>> getReviews(@PathVariable int dishId){
		List<ReviewDetailsDto> result = reviewService.getReviewsForDish(dishId);
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
