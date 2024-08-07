package dev.bhardwaj.food_order.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.bhardwaj.food_order.dto.CustomerDto;
import dev.bhardwaj.food_order.dto.DishDto;
import dev.bhardwaj.food_order.dto.NewReviewDto;
import dev.bhardwaj.food_order.dto.ReviewDetailsDto;
import dev.bhardwaj.food_order.dto.ReviewDto;
import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Review;
import dev.bhardwaj.food_order.exception.DoesNotExistException;
import dev.bhardwaj.food_order.exception.DtoEntityConversionException;
import dev.bhardwaj.food_order.repository.CustomerRepository;
import dev.bhardwaj.food_order.repository.DishRepository;

@Component
public class ReviewConverter {
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerConverter customerConverter;
	
	@Autowired
	private DishRepository dishRepository;
	
	@Autowired
	private DishConverter dishConverter;
	
	
	public Review toEntity(Object dto) {
		if (dto == null) {
			return null;
		}

		switch (dto.getClass().getSimpleName()) {
		case "NewReviewDto":
			Review review = new Review();
			NewReviewDto newDto = (NewReviewDto) dto;
			review.setDishId(newDto.getDishId());
			review.setCustomerId(newDto.getCustomerId());
			review.setReview(newDto.getReview());
			review.setTimestamp(newDto.getTimestamp());
			
			return review;
			
		default:
			throw new DtoEntityConversionException("DTO to Review Entity conversion failed");
		}
	}

	public <T> T toDto(Review review, Class<T> dtoClass) {
		if (review == null) {
			return null;
		}

		switch (dtoClass.getSimpleName()) {
		
		case "ReviewDto":
			ReviewDto reviewDto = new ReviewDto();
			reviewDto.setId(review.getId());
			reviewDto.setCustomerId(review.getCustomerId());
			reviewDto.setDishId(review.getDishId());
			reviewDto.setReview(review.getReview());
			reviewDto.setTimestamp(review.getTimestamp());
			
			return dtoClass.cast(reviewDto);
			
		case "ReviewDetailsDto":
			ReviewDetailsDto reviewDetailsDto = new ReviewDetailsDto();
			reviewDetailsDto.setId(review.getId());
			reviewDetailsDto.setReview(review.getReview());
			reviewDetailsDto.setTimeStamp(review.getTimestamp());
			
			Customer customer = customerRepository
					.findById(review.getCustomerId())
					.orElseThrow(()->new DoesNotExistException("Customer with given id does not exist!"));
			Dish dish = dishRepository
					.findById(review.getDishId())
					.orElseThrow(()->new DoesNotExistException("Dish with given id does not exist!"));
			
			CustomerDto customerDto = customerConverter.toDto(customer, CustomerDto.class);
			DishDto dishDto = dishConverter.toDto(dish, DishDto.class);
			
			reviewDetailsDto.setCustomerDto(customerDto);
			reviewDetailsDto.setDishDto(dishDto);
			
			return dtoClass.cast(reviewDetailsDto);
			
		default:
			throw new DtoEntityConversionException("Review Entity to DTO conversion failed");
		}
	}
}
