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
			throw new IllegalArgumentException("Unsupported DTO: " + dto.getClass().getSimpleName());
		}
	}

	public <T> T toDto(Review review, Class<T> dtoClass) {
		if (review == null) {
			return null;
		}

		T dto;

		try {
			dto = dtoClass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Failed to create DTO instance", e);
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
			reviewDetailsDto.setCustomerId(review.getCustomerId());
			reviewDetailsDto.setDishId(review.getDishId());
			reviewDetailsDto.setReview(review.getReview());
			reviewDetailsDto.setTimeStamp(review.getTimestamp());
			
			// set customer and dish dtos also
			Customer customer = customerRepository
					.findById(review.getCustomerId())
					.orElseThrow(()->new RuntimeException("Customer does not exist!"));
			Dish dish = dishRepository
					.findById(review.getDishId())
					.orElseThrow(()->new RuntimeException("Dish does not exist!"));
			
			CustomerDto customerDto = customerConverter.toDto(customer, CustomerDto.class);
			DishDto dishDto = dishConverter.toDto(dish, DishDto.class);
			
			reviewDetailsDto.setCustomerDto(customerDto);
			reviewDetailsDto.setDishDto(dishDto);
			
			return dtoClass.cast(reviewDetailsDto);
			
		default:
			throw new IllegalArgumentException("Unsupported DTO: " + dtoClass.getSimpleName());
		}
	}
}
