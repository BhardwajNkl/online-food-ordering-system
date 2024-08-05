package dev.bhardwaj.food_order.service;

import java.util.List;

import dev.bhardwaj.food_order.dto.NewReviewDto;
import dev.bhardwaj.food_order.dto.ReviewDetailsDto;
import dev.bhardwaj.food_order.dto.ReviewDto;

public interface ReviewService {
	ReviewDto createReview(NewReviewDto reviewDto);
//	void deleteReview(long reviewId);
	
	List<ReviewDetailsDto> getReviewsGivenByCustomer(long customerId);
	List<ReviewDetailsDto> getReviewsForDish(int dishId);
}
