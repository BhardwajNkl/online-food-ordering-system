package dev.bhardwaj.food_order.service;

import java.util.List;

import dev.bhardwaj.food_order.dto.NewRatingDto;
import dev.bhardwaj.food_order.dto.RatingDetailsDto;
import dev.bhardwaj.food_order.dto.RatingDto;

public interface RatingService {
	RatingDto createRating(NewRatingDto ratingDto);	
	List<RatingDetailsDto> getRatingsGivenByCustomer(long customerId);
	List<RatingDetailsDto> getRatingsForDish(int dishId);
}
