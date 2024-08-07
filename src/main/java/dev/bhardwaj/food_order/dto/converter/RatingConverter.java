package dev.bhardwaj.food_order.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.bhardwaj.food_order.dto.CustomerDto;
import dev.bhardwaj.food_order.dto.DishDto;
import dev.bhardwaj.food_order.dto.NewRatingDto;
import dev.bhardwaj.food_order.dto.RatingDetailsDto;
import dev.bhardwaj.food_order.dto.RatingDto;
import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Rating;
import dev.bhardwaj.food_order.exception.DoesNotExistException;
import dev.bhardwaj.food_order.exception.DtoEntityConversionException;
import dev.bhardwaj.food_order.repository.CustomerRepository;
import dev.bhardwaj.food_order.repository.DishRepository;

@Component
public class RatingConverter {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerConverter customerConverter;
	
	@Autowired
	private DishRepository dishRepository;
	
	@Autowired
	private DishConverter dishConverter;
	
	
	public Rating toEntity(Object dto) {
		if (dto == null) {
			return null;
		}

		switch (dto.getClass().getSimpleName()) {
		case "NewRatingDto":
			Rating rating = new Rating();
			NewRatingDto newDto = (NewRatingDto) dto;
			rating.setDishId(newDto.getDishId());
			rating.setCustomerId(newDto.getCustomerId());
			rating.setRating(newDto.getRating());
			rating.setTimeStamp(newDto.getTimeStamp());
			
			return rating;
			
		default:
			throw new DtoEntityConversionException("DTO to Rating Entity conversion failed");
		}
	}

	public <T> T toDto(Rating rating, Class<T> dtoClass) {
		if (rating == null) {
			return null;
		}

		switch (dtoClass.getSimpleName()) {
		
		case "RatingDto":
			RatingDto ratingDto = new RatingDto();
			ratingDto.setId(rating.getId());
			ratingDto.setCustomerId(rating.getCustomerId());
			ratingDto.setDishId(rating.getDishId());
			ratingDto.setRating(rating.getRating());
			ratingDto.setTimeStamp(rating.getTimeStamp());
			
			return dtoClass.cast(ratingDto);
			
		case "RatingDetailsDto":
			RatingDetailsDto ratingDetailsDto = new RatingDetailsDto();
			ratingDetailsDto.setId(rating.getId());
			ratingDetailsDto.setRating(rating.getRating());
			ratingDetailsDto.setTimeStamp(rating.getTimeStamp());
			
			Customer customer = customerRepository
					.findById(rating.getCustomerId())
					.orElseThrow(()->new DoesNotExistException("Customer with given id does not exist!"));
			Dish dish = dishRepository
					.findById(rating.getDishId())
					.orElseThrow(()->new DoesNotExistException("Dish with given id does not exist!"));
			
			CustomerDto customerDto = customerConverter.toDto(customer, CustomerDto.class);
			DishDto dishDto = dishConverter.toDto(dish, DishDto.class);
			
			ratingDetailsDto.setCustomerDto(customerDto);
			ratingDetailsDto.setDishDto(dishDto);
			
			return dtoClass.cast(ratingDetailsDto);
			
		default:
			throw new DtoEntityConversionException("Rating Entity to DTO conversion failed");
		}
	}
}
