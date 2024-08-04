package dev.bhardwaj.food_order.service;

import dev.bhardwaj.food_order.dto.CreateReviewDto;
import dev.bhardwaj.food_order.entity.Review;

public interface ReviewService {
	Review createReview(CreateReviewDto reviewDto);
	void deleteReview(long reviewId);
}
