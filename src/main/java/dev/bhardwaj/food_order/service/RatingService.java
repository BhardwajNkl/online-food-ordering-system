package dev.bhardwaj.food_order.service;

import dev.bhardwaj.food_order.dto.CreateRatingDto;
import dev.bhardwaj.food_order.entity.Rating;

public interface RatingService {
	Rating createRating(CreateRatingDto ratingDto);
	void deleteRating(long ratingId);
}
