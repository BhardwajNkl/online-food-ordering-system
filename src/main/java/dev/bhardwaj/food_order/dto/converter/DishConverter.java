package dev.bhardwaj.food_order.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import dev.bhardwaj.food_order.dto.DishDetailsDto;
import dev.bhardwaj.food_order.dto.DishDto;
import dev.bhardwaj.food_order.dto.NewDishDto;
import dev.bhardwaj.food_order.dto.RestaurantDto;
import dev.bhardwaj.food_order.dto.ReviewDto;
import dev.bhardwaj.food_order.dto.UpdateDishDto;
//import dev.bhardwaj.food_order.entity.Dish.Cuisine;
import dev.bhardwaj.food_order.entity.Cuisine;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Restaurant;
import dev.bhardwaj.food_order.exception.DoesNotExistException;
import dev.bhardwaj.food_order.exception.DtoEntityConversionException;
import dev.bhardwaj.food_order.repository.DishRepository;
import dev.bhardwaj.food_order.repository.RestaurantRepository;

@Component
public class DishConverter {
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private DishRepository dishRepository;
	
	@Autowired
	@Lazy
	private RestaurantConverter restaurantConverter;
	
	@Autowired
	@Lazy
	private CustomerConverter customerConverter;
	
	
	public Dish toEntity(Object dto) {
		if (dto == null) {
			return null;
		}

		switch (dto.getClass().getSimpleName()) {
		case "NewDishDto":
			Dish dish = new Dish();
			NewDishDto newDto = (NewDishDto) dto;
			Restaurant restaurant = restaurantRepository.findById(newDto.getRestaurantId())
					.orElseThrow(()->new DoesNotExistException("Restaurant with given id does not exist!"));
			dish.setName(newDto.getName());
			dish.setAvailable(newDto.isAvailable());
			dish.setAverageRating(0);
			dish.setCuisine(Cuisine.valueOf(newDto.getCuisine()));
			dish.setDescription(newDto.getDescription());
			dish.setPrice(newDto.getPrice());
			dish.setRestaurant(restaurant);			
			return dish;
			
		case "UpdateDishDto":
			UpdateDishDto updateDto = (UpdateDishDto) dto;
			Dish dishToUpdate = dishRepository.findById(updateDto.getId())
					.orElseThrow(()->new DoesNotExistException("Dish with given id does not exist!"));
			dishToUpdate.setName(updateDto.getName());
			dishToUpdate.setDescription(updateDto.getDescription());
			dishToUpdate.setPrice(updateDto.getPrice());
			dishToUpdate.setAvailable(updateDto.isAvailable());
			dishToUpdate.setCuisine(Cuisine.valueOf(updateDto.getCuisine()));
			
			return dishToUpdate;
			
		default:
			throw new DtoEntityConversionException("DTO to Dish Entity conversion failed");
		}
	}

	public <T> T toDto(Dish dish, Class<T> dtoClass) {
		if (dish == null) {
			return null;
		}

		switch (dtoClass.getSimpleName()) {
		
		case "DishDto":
			DishDto dishDto = new DishDto();
			dishDto.setId(dish.getId());
			dishDto.setAvailable(dish.isAvailable());
			dishDto.setAverageRating(dish.getAverageRating());
			dishDto.setCuisine(dish.getCuisine());
			dishDto.setDescription(dish.getDescription());
			dishDto.setName(dish.getName());
			dishDto.setPrice(dish.getPrice());
			
			return dtoClass.cast(dishDto);
			
		case "DishDetailsDto":
			DishDetailsDto dishDetailsDto = new DishDetailsDto();
			dishDetailsDto.setId(dish.getId());
			dishDetailsDto.setAvailable(dish.isAvailable());
			dishDetailsDto.setAverageRating(dish.getAverageRating());
			dishDetailsDto.setCuisine(dish.getCuisine());
			dishDetailsDto.setDescription(dish.getDescription());
			dishDetailsDto.setName(dish.getName());
			dishDetailsDto.setPrice(dish.getPrice());
			
			List<ReviewDto> reviews = dish.getReveiws()
					.stream()
					.map(
						review->{
							
							ReviewDto reviewDto = new ReviewDto();
							reviewDto.setId(review.getId());
							reviewDto.setCustomerId(review.getCustomerId());
							reviewDto.setDishId(review.getDishId());
							reviewDto.setReview(review.getReview());
							reviewDto.setTimestamp(review.getTimestamp());
							return reviewDto;
							
						}
						)
					.collect(Collectors.toList());
			
			dishDetailsDto.setReveiws(reviews);
			
			RestaurantDto restaurantDto = restaurantConverter.toDto(dish.getRestaurant(), RestaurantDto.class);
			dishDetailsDto.setRestaurant(restaurantDto);
			
			return dtoClass.cast(dishDetailsDto);
			
		default:
			throw new DtoEntityConversionException("Dish Entity to DTO conversion failed");
		}
	}
}
