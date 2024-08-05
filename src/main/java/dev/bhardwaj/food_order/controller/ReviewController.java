package dev.bhardwaj.food_order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bhardwaj.food_order.dto.NewReviewDto;
import dev.bhardwaj.food_order.dto.ReviewDetailsDto;
import dev.bhardwaj.food_order.dto.ReviewDto;
import dev.bhardwaj.food_order.entity.Review;
import dev.bhardwaj.food_order.service.ReviewService;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
private final ReviewService reviewService;
	
	public ReviewController(ReviewService reviewService){
		this.reviewService = reviewService;
	}
	
	@PostMapping("/add-review")
	ReviewDto addReview(@RequestBody NewReviewDto reviewDto) {
		return reviewService.createReview(reviewDto);
	}
	
//	@DeleteMapping("/delete-review/{reviewId}")
//	void deleteReview(@PathVariable long reviewId) {
//		reviewService.deleteReview(reviewId);
//	}
	
	@GetMapping("/get-reviews-given-by-customer/{customerId}")
	List<ReviewDetailsDto> getReviewsGivenByCustomer(@PathVariable int customerId){
		return reviewService.getReviewsGivenByCustomer(customerId);
	}
	
	@GetMapping("/get-dish-reviews/{dishId}")
	List<ReviewDetailsDto> getReviews(@PathVariable int dishId){
		return reviewService.getReviewsForDish(dishId);
	}
}
